package mightypork.rogue.world.gui.interaction;


import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.gui.MapView;
import mightypork.util.math.constraints.vect.Vect;


public interface MapInteractionPlugin {
	
	void onStepEnd(MapView wv, PlayerControl player);
	
	
	void onClick(MapView wv, PlayerControl player, Vect mouse, int button, boolean down);
	
	
	void onKey(MapView wv, PlayerControl player, int key, boolean down);
	
}
