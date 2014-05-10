package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.input.events.KeyEvent;
import mightypork.gamecore.input.events.KeyListener;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.events.PlayerStepEndListener;
import mightypork.rogue.world.gui.MapView;


public abstract class MapInteractionPlugin {
	
	protected final MapView mapView;
	
	
	public MapInteractionPlugin(MapView mapView)
	{
		super();
		this.mapView = mapView;
	}
	
	
	public abstract boolean onClick(Vect mouse, int button, boolean down);
}
