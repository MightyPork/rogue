package mightypork.rogue.screens.game;


import java.io.File;

import mightypork.gamecore.Config;
import mightypork.gamecore.core.AppAccess;
import mightypork.gamecore.core.events.UserQuitRequest;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.ActionGroup;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.KeyStroke.Edge;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.util.math.color.Color;
import mightypork.rogue.Const;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
import mightypork.rogue.screens.RogueScreen;
import mightypork.rogue.world.PlayerFacade;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.events.PlayerDeathHandler;
import mightypork.rogue.world.events.WorldPauseRequest;
import mightypork.rogue.world.events.WorldPauseRequest.PauseAction;


public class ScreenGame extends RogueScreen implements PlayerDeathHandler {
	
	public static final Color COLOR_BTN_GOOD = Color.fromHex(0x28CB2D);
	public static final Color COLOR_BTN_BAD = Color.fromHex(0xCB2828);
	public static final Color COLOR_BTN_CANCEL = Color.fromHex(0xFFFB55);
	
	/**
	 * Game gui state.
	 * 
	 * @author MightyPork
	 */
	public enum GScrState
	{
		WORLD, INV, DEATH, GOTO_MENU, GOTO_QUIT;
	}
	
	private InventoryLayer invLayer;
	private HudLayer hudLayer;
	private DeathLayer deathLayer;
	
	private WorldLayer worldLayer;
	
	private GScrState state = null;
	
	private final ActionGroup worldActions = new ActionGroup();
	
	public Action actionEat = new Action() {
		
		@Override
		public void execute()
		{
			final PlayerFacade pl = WorldProvider.get().getPlayer();
			if (pl.isDead() || pl.getWorld().isPaused()) return;
			pl.tryToEatSomeFood();
		}
	};
	
	public Action actionToggleInv = new Action() {
		
		
		@Override
		public void execute()
		{
			System.out.println("Toggle inv action");
			setState(getState() == GScrState.INV ? GScrState.WORLD : GScrState.INV);
		}
	};
	
	public Action actionToggleMinimap = new Action() {
		
		@Override
		public void execute()
		{
			hudLayer.miniMap.setVisible(!hudLayer.miniMap.isVisible());
		}
	};
	
	public Action actionTogglePause = new Action() {
		
		@Override
		public void execute()
		{
			getEventBus().send(new WorldPauseRequest(PauseAction.TOGGLE));
		}
	};
	
	public Action actionToggleZoom = new Action() {
		
		@Override
		public void execute()
		{
			worldLayer.map.toggleMag();
		}
	};
	
	public Action actionSave = new Action() {
		
		@Override
		public void execute()
		{
			try {
				WorldProvider.get().saveWorld();
				WorldProvider.get().getWorld().getConsole().msgWorldSaved();
			} catch (final Exception e) {
				Log.e("Could not save the world.", e);
				WorldProvider.get().getWorld().getConsole().msgWorldSaveError();
			}
		}
	};
	
	public Action actionLoad = new Action() {
		
		@Override
		public void execute()
		{
			try {
				final File f = WorldProvider.get().getWorld().getSaveFile();
				WorldProvider.get().loadWorld(f);
				WorldProvider.get().getWorld().getConsole().msgReloaded();
				
			} catch (final Exception e) {
				Log.e("Could not load the world.", e);
				WorldProvider.get().getWorld().getConsole().msgLoadFailed();
			}
		}
	};
	
	public Action actionMenu = new Action() {
		
		@Override
		public void execute()
		{
			setState(GScrState.GOTO_MENU);
		}
	};
	
	public Action actionQuit = new Action() {
		
		@Override
		public void execute()
		{
			setState(GScrState.GOTO_QUIT);
		}
	};
	
	public Action actionDropLastPickedItem = new Action() {
		
		@Override
		public void execute()
		{
			final PlayerFacade pl = WorldProvider.get().getPlayer();
			if (pl.isDead() || pl.getWorld().isPaused()) return;
			pl.dropItem(pl.getInventory().getLastAddIndex());
		}
	};
	private AskSaveLayer askSaveLayer;
	
	
	/**
	 * Set gui state (overlay)
	 * 
	 * @param nstate new state
	 */
	public void setState(GScrState nstate)
	{
		if (this.state == nstate) return;
		
		if (nstate != GScrState.WORLD) { // leaving world.
			getEventBus().send(new WorldPauseRequest(PauseAction.PAUSE));
			
			worldActions.setEnabled(false); // disable world actions
		}
		
		if (nstate == GScrState.WORLD) {
			getEventBus().send(new WorldPauseRequest(PauseAction.RESUME));
			
			invLayer.hide();
			deathLayer.hide();
			askSaveLayer.hide();
			
			worldActions.setEnabled(true);
		}
		
		if (nstate == GScrState.INV) {
			invLayer.show();
		}
		
		if (nstate == GScrState.DEATH) {
			deathLayer.show();
		}
		
		if (nstate == GScrState.GOTO_MENU) {
			
			askSaveLayer.setTask(new Runnable() {
				
				@Override
				public void run()
				{
					getEventBus().send(new RogueStateRequest(RogueState.MAIN_MENU));
				}
			});
			
			askSaveLayer.show();
		}
		
		if (nstate == GScrState.GOTO_QUIT) {
			
			askSaveLayer.setTask(new Runnable() {
				
				@Override
				public void run()
				{
					getEventBus().send(new RogueStateRequest(RogueState.EXIT));
				}
			});
			
			askSaveLayer.show();
		}
		
		this.state = nstate;
	}
	
	
	public GScrState getState()
	{
		return state;
	}
	
	
	public ScreenGame(AppAccess app)
	{
		super(app);
		
		addLayer(invLayer = new InventoryLayer(this));
		addLayer(deathLayer = new DeathLayer(this));
		addLayer(hudLayer = new HudLayer(this));
		addLayer(worldLayer = new WorldLayer(this));
		addLayer(askSaveLayer = new AskSaveLayer(this));
		
		bindKey(Config.getKey("game.pause"), Edge.RISING, actionTogglePause);
		
		bindKey(Config.getKey("game.inventory"), Edge.RISING, actionToggleInv);
		bindKey(Config.getKey("game.drop"), Edge.RISING, actionDropLastPickedItem);
		bindKey(Config.getKey("game.eat"), Edge.RISING, actionEat);
		bindKey(Config.getKey("game.minimap"), Edge.RISING, actionToggleMinimap);
		bindKey(Config.getKey("game.zoom"), Edge.RISING, actionToggleZoom);
		
		bindKey(Config.getKey("game.load"), Edge.RISING, actionLoad);
		bindKey(Config.getKey("game.save"), Edge.RISING, actionSave);
		bindKey(Config.getKey("game.quit"), Edge.RISING, actionMenu);
		
		// add as actions - enableables.
		worldActions.add(worldLayer);
		worldActions.add(hudLayer);
		
		worldActions.add(actionEat);
		worldActions.add(actionToggleMinimap);
		worldActions.add(actionTogglePause);
		worldActions.add(actionToggleZoom);
		
		worldActions.add(actionSave);
		worldActions.add(actionLoad);
		worldActions.add(actionMenu);
//		worldActions.add(actionQuit);
		worldActions.add(actionDropLastPickedItem);
		
		worldActions.setEnabled(true);
		
		// CHEAT - X-ray
		bindKey(Config.getKey("game.cheat.xray"), Edge.RISING, new Runnable() {
			
			@Override
			public void run()
			{
				Const.RENDER_UFOG ^= true;
			}
		});
	}
	
	
	@Override
	protected void onScreenEnter()
	{
		super.onScreenEnter();
		WorldProvider.get().setListening(true);
		
		setState(GScrState.WORLD);
	}
	
	
	@Override
	protected void onScreenLeave()
	{
		super.onScreenLeave();
		WorldProvider.get().setListening(false);
	}
	
	
	@Override
	public void onPlayerKilled()
	{
		setState(GScrState.DEATH);
	}
	
	@Override
	public void onQuitRequest(UserQuitRequest event)
	{
		actionQuit.run();
		event.consume();
	}
}
