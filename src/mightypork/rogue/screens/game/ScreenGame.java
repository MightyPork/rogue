package mightypork.rogue.screens.game;


import java.util.Random;

import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.ActionGroup;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
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
	
	private GScrState state = GScrState.WORLD;
	
	private final ActionGroup worldActions = new ActionGroup();
	
	public Action actionEat = new Action() {
		
		@Override
		public void execute()
		{
			WorldProvider.get().getPlayer().tryToEatSomeFood();
		}
	};
	
	public Action actionInv = new Action() {
		
		@Override
		public void execute()
		{
			setState(GScrState.INV);
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
	
	private WorldLayer worldLayer;
	
	
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
			
			worldActions.enable(true);
		}
		
		if (nstate == GScrState.INV) {
			invLayer.setVisible(true);
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
		addLayer(hudLayer = new HudLayer(this));
		addLayer(worldLayer = new WorldLayer(this));
		
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
		
		bindKey(new KeyStroke(Keys.I), actionInv);
		bindKey(new KeyStroke(Keys.E), actionEat);
		bindKey(new KeyStroke(Keys.M), actionToggleMinimap);
		bindKey(new KeyStroke(Keys.Z), actionToggleZoom);
		
		worldActions.add(actionEat);
		worldActions.add(actionInv);
		worldActions.add(actionToggleMinimap);
		worldActions.add(actionTogglePause);
		worldActions.add(actionToggleZoom);
		
		worldActions.enable(true);
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
