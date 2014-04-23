package mightypork.rogue.screens.gamescreen.world;


import mightypork.rogue.world.PlayerControl;
import mightypork.util.constraints.vect.Vect;


public interface MapInteractionPlugin {
	
	void onStepEnd(MapView wv, PlayerControl player);
	
	
	void onClick(MapView wv, PlayerControl player, Vect mouse);
	
	
	void onKey(MapView wv, PlayerControl player, int key);
	
}
