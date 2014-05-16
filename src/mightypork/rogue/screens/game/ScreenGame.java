package mightypork.rogue.screens.game;


import java.io.File;

import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.ActionGroup;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.logging.Log;
import mightypork.rogue.Config;
import mightypork.rogue.GameStateManager.GameState;
import mightypork.rogue.events.GameStateRequest;
import mightypork.rogue.world.PlayerFacade;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.events.WorldPauseRequest;
import mightypork.rogue.world.events.WorldPauseRequest.PauseAction;


public class ScreenGame extends LayeredScreen {
	
	/**
	 * Game gui state.
	 * 
	 * @author MightyPork
	 */
	public enum GScrState
	{
		WORLD, INV;
	}
	
	private InventoryLayer invLayer;
	private HudLayer hudLayer;
	
	private WorldLayer worldLayer;
	
	private GScrState state = GScrState.WORLD;
	
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
			// TODO ask to save
			
			getEventBus().send(new GameStateRequest(GameState.MAIN_MENU));
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
			
			invLayer.setVisible(false); // hide all extra layers
			invLayer.setEnabled(false);
			
			worldActions.setEnabled(true);
		}
		
		if (nstate == GScrState.INV) {
			invLayer.setVisible(true);
			invLayer.setEnabled(true);
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
		invLayer.setEnabled(false);
		invLayer.setVisible(false);
		
		addLayer(hudLayer = new HudLayer(this));
		hudLayer.setEnabled(true);
		hudLayer.setVisible(true);
		
		addLayer(worldLayer = new WorldLayer(this));
		worldLayer.setEnabled(true);
		worldLayer.setVisible(true);
		
		//pause key
		bindKey(new KeyStroke(Keys.P), actionTogglePause);
		bindKey(new KeyStroke(Keys.PAUSE), actionTogglePause);
		bindKey(new KeyStroke(Keys.SPACE), actionTogglePause);
		
		bindKey(new KeyStroke(Keys.I), actionToggleInv);
		bindKey(new KeyStroke(Keys.D), actionDropLastPickedItem);
		bindKey(new KeyStroke(Keys.E), actionEat);
		bindKey(new KeyStroke(Keys.M), actionToggleMinimap);
		bindKey(new KeyStroke(Keys.Z), actionToggleZoom);
		
		bindKey(new KeyStroke(Keys.L, Keys.MOD_CONTROL), actionLoad);
		bindKey(new KeyStroke(Keys.S, Keys.MOD_CONTROL), actionSave);
		
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
		bindKey(new KeyStroke(Keys.F10, Keys.MOD_CONTROL), new Runnable() {
			
			@Override
			public void run()
			{
				Config.RENDER_UFOG ^= true;
			}
		});
	}
	
	
	@Override
	protected void onScreenEnter()
	{
		super.onScreenEnter();
		WorldProvider.get().setListening(true);
	}
	
	
	@Override
	protected void onScreenLeave()
	{
		super.onScreenLeave();
		WorldProvider.get().setListening(false);
	}
}
