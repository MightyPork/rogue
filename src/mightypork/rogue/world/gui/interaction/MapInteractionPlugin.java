package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.events.PlayerStepEndListener;
import mightypork.rogue.world.gui.MapView;


public abstract class MapInteractionPlugin implements Updateable, PlayerStepEndListener {
	
	protected final MapView mapView;
	
	
	public MapInteractionPlugin(MapView mapView)
	{
		super();
		this.mapView = mapView;
	}
	
	
	public abstract boolean onClick(Vect mouse, int button, boolean down);
	
	
	public abstract boolean onKey(int key, boolean down);
	
	
	@Override
	public abstract void update(double delta);
	
}
