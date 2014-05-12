package mightypork.rogue.screens.game;


import java.util.Random;

import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.ActionGroup;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.rogue.Config;
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
	
	private final Random rand = new Random();
	private InvLayer invLayer;
	private HudLayer hudLayer;
	
	private WorldLayer worldLayer;
	
	private GScrState state = GScrState.WORLD;
	
	private final ActionGroup worldActions = new ActionGroup();
	
	public Action actionEat = new Action() {
		
		@Override
		public void execute()
		{
			final PlayerFacade pl = WorldProvider.get().getPlayer();
			if (pl.isDead()) return;
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
			hudLayer.mm.setVisible(!hudLayer.mm.isVisible());
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
			
			worldActions.enable(false); // disable world actions
		}
		
		if (nstate == GScrState.WORLD) {
			getEventBus().send(new WorldPauseRequest(PauseAction.RESUME));
			
			invLayer.setVisible(false); // hide all extra layers
			invLayer.enable(false);
			
			worldActions.enable(true);
		}
		
		if (nstate == GScrState.INV) {
			invLayer.setVisible(true);
			invLayer.enable(true);
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
		
		addLayer(invLayer = new InvLayer(this));
		invLayer.enable(false);
		invLayer.setVisible(false);
		
		addLayer(hudLayer = new HudLayer(this));
		hudLayer.enable(true);
		hudLayer.setVisible(true);
		
		addLayer(worldLayer = new WorldLayer(this));
		worldLayer.enable(true);
		worldLayer.setVisible(true);
		
		// TODO temporary here ↓
		bindKey(new KeyStroke(Keys.L_CONTROL, Keys.N), new Runnable() {
			
			@Override
			public void run()
			{
				WorldProvider.get().createWorld(rand.nextLong());
			}
		});
		
		//pause key
		bindKey(new KeyStroke(Keys.P), actionTogglePause);
		bindKey(new KeyStroke(Keys.PAUSE), actionTogglePause);
		bindKey(new KeyStroke(Keys.SPACE), actionTogglePause);
		
		bindKey(new KeyStroke(Keys.I), actionToggleInv);
		bindKey(new KeyStroke(Keys.E), actionEat);
		bindKey(new KeyStroke(Keys.M), actionToggleMinimap);
		bindKey(new KeyStroke(Keys.Z), actionToggleZoom);
		
		// add as actions - enableables.
		worldActions.add(worldLayer);
		worldActions.add(hudLayer);
		
		worldActions.add(actionEat);
		worldActions.add(actionToggleMinimap);
		worldActions.add(actionTogglePause);
		worldActions.add(actionToggleZoom);
		
		worldActions.enable(true);
		
		// TMP TODO remove
		bindKey(new KeyStroke(Keys.X), new Runnable() {
			
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
