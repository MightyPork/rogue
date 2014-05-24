package mightypork.rogue.world.gui.interaction;


import mightypork.dynmath.vect.Vect;
import mightypork.rogue.world.PlayerFacade;
import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.gui.MapView;


public abstract class MapInteractionPlugin {
	
	protected final MapView mapView;
	
	
	public MapInteractionPlugin(MapView mapView)
	{
		super();
		this.mapView = mapView;
	}
	
	
	protected PlayerFacade getPlayer()
	{
		return mapView.plc.getPlayer();
	}
	
	
	protected boolean isImmobile()
	{
		return getPlayer().isDead() || getWorld().isPaused();
	}
	
	
	protected World getWorld()
	{
		return WorldProvider.get().getWorld();
	}
	
	
	public abstract boolean onClick(Vect mouse, int button, boolean down);
}
