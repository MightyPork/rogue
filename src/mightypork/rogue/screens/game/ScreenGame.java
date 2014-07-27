package mightypork.rogue.screens.game;


import java.io.File;

import mightypork.gamecore.core.config.Config;
import mightypork.gamecore.core.events.UserQuitRequest;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.ActionGroup;
import mightypork.gamecore.input.Trigger;
import mightypork.gamecore.resources.Res;
import mightypork.rogue.Const;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
import mightypork.rogue.screens.RogueScreen;
import mightypork.rogue.world.PlayerFacade;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.events.GameWinHandler;
import mightypork.rogue.world.events.PlayerDeathHandler;
import mightypork.rogue.world.events.WorldPauseRequest;
import mightypork.rogue.world.events.WorldPauseRequest.PauseAction;
import mightypork.utils.logging.Log;
import mightypork.utils.math.color.Color;


public class ScreenGame extends RogueScreen implements PlayerDeathHandler, GameWinHandler {
	
	public static final Color COLOR_BTN_GOOD = Color.fromHex(0x28CB2D);
	public static final Color COLOR_BTN_BAD = Color.fromHex(0xCB2828);
	public static final Color COLOR_BTN_CANCEL = Color.fromHex(0xFFFB55);
	
	/**
	 * Game gui state.
	 * 
	 * @author Ondřej Hruška (MightyPork)
	 */
	public enum GScrState
	{
		WORLD, INV, DEATH, WIN, GOTO_MENU, GOTO_QUIT;
	}
	
	private LayerInv invLayer;
	private LayerGameUi hudLayer;
	private LayerDeath deathLayer;
	private LayerWin winLayer;
	
	private LayerMapView worldLayer;
	
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
			if (WorldProvider.get().getWorld().isGameOver()) return;
			
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
			if (WorldProvider.get().getWorld().isGameOver()) return;
			
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
	private LayerAskSave askSaveLayer;
	
	
	/**
	 * Set gui state (overlay)
	 * 
	 * @param nstate new state
	 */
	public void setState(GScrState nstate)
	{
		if (this.state == nstate) return;
		
		final boolean gameOver = WorldProvider.get().getWorld().isGameOver();
		
		if (nstate != GScrState.WORLD && state == GScrState.WORLD) { // leaving world.
			getEventBus().send(new WorldPauseRequest(PauseAction.PAUSE));
			
			worldActions.setEnabled(false); // disable world actions
		}
		
		if (nstate != GScrState.DEATH) deathLayer.hide();
		if (nstate != GScrState.WIN) winLayer.hide();
		if (nstate != GScrState.INV) invLayer.hide();
		if (nstate != GScrState.GOTO_MENU && nstate != GScrState.GOTO_QUIT) askSaveLayer.hide();
		
		Runnable task;
		switch (nstate) {
			case WORLD:
				getEventBus().send(new WorldPauseRequest(PauseAction.RESUME));
				worldActions.setEnabled(true);
				break;
			
			case INV:
				invLayer.show();
				break;
			
			case DEATH:
				deathLayer.show();
				break;
			
			case WIN:
				winLayer.show();
				break;
			
			case GOTO_MENU:
			case GOTO_QUIT:
				final RogueState goal = (nstate == GScrState.GOTO_MENU) ? RogueState.MAIN_MENU : RogueState.EXIT;
				
				task = new Runnable() {
					
					@Override
					public void run()
					{
						getEventBus().send(new RogueStateRequest(goal));
					}
				};
				
				if (gameOver) {
					task.run();
				} else {
					askSaveLayer.setTask(task);
					askSaveLayer.show();
				}
		}
		
		this.state = nstate;
	}
	
	
	public GScrState getState()
	{
		return state;
	}
	
	
	public ScreenGame(AppAccess app) {
		super(app);
		
		addLayer(invLayer = new LayerInv(this));
		addLayer(deathLayer = new LayerDeath(this));
		addLayer(winLayer = new LayerWin(this));
		addLayer(hudLayer = new LayerGameUi(this));
		addLayer(worldLayer = new LayerMapView(this));
		addLayer(askSaveLayer = new LayerAskSave(this));
		
		bindKey(Config.getKeyStroke("game.pause"), Trigger.RISING, actionTogglePause);
		
		bindKey(Config.getKeyStroke("game.inventory"), Trigger.RISING, actionToggleInv);
		bindKey(Config.getKeyStroke("game.drop"), Trigger.RISING, actionDropLastPickedItem);
		bindKey(Config.getKeyStroke("game.eat"), Trigger.RISING, actionEat);
		bindKey(Config.getKeyStroke("game.minimap"), Trigger.RISING, actionToggleMinimap);
		bindKey(Config.getKeyStroke("game.zoom"), Trigger.RISING, actionToggleZoom);
		
		bindKey(Config.getKeyStroke("game.load"), Trigger.RISING, actionLoad);
		bindKey(Config.getKeyStroke("game.save"), Trigger.RISING, actionSave);
		bindKey(Config.getKeyStroke("game.quit"), Trigger.RISING, actionMenu);
		
//		bindKey(new KeyStroke(Keys.W), Edge.RISING, new Runnable() {
//			
//			@Override
//			public void run()
//			{
//				setState(GScrState.WIN);
//			}
//		});
		
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
		worldActions.add(actionDropLastPickedItem);
		
		worldActions.setEnabled(true);
		
		// CHEAT - X-ray
		bindKey(Config.getKeyStroke("game.cheat.xray"), Trigger.RISING, new Runnable() {
			
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
		hideAllPopups();
		
		getSoundSystem().fadeOutAllLoops();
		Res.getSoundLoop("music.dungeon").fadeIn();
	}
	
	
	@Override
	protected void onScreenLeave()
	{
		super.onScreenLeave();
		WorldProvider.get().setListening(false);
		
		Res.getSoundLoop("music.dungeon").fadeOut();
	}
	
	
	private void hideAllPopups()
	{
		invLayer.hideImmediate();
		deathLayer.hideImmediate();
		winLayer.hideImmediate();
		askSaveLayer.hideImmediate();
	}
	
	
	@Override
	public void onPlayerKilled()
	{
		setState(GScrState.DEATH);
	}
	
	
	@Override
	public void onGameWon()
	{
		setState(GScrState.WIN);
	}
	
	
	@Override
	public void onQuitRequest(UserQuitRequest event)
	{
		// if player is dead, don't ask don't ask for save		
		final PlayerFacade pl = WorldProvider.get().getPlayer();
		if (pl.isDead()) return;
		
		actionQuit.run();
		event.consume();
	}
}
