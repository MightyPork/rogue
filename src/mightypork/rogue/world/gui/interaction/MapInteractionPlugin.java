package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.gui.MapView;


public interface MapInteractionPlugin {
	
	boolean onStepEnd(MapView mapView, PlayerControl player);
	
	
	boolean onClick(MapView mapView, PlayerControl player, Vect mouse, int button, boolean down);
	
	
	boolean onKey(MapView mapView, PlayerControl player, int key, boolean down);
	
	
	void update(MapView mapView, PlayerControl pc, double delta);
	
}
