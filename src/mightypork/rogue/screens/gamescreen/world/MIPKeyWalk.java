package mightypork.rogue.screens.gamescreen.world;


import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.Keys;
import mightypork.rogue.world.PlayerControl;
import mightypork.util.math.constraints.vect.Vect;


public class MIPKeyWalk implements MapInteractionPlugin {
	
	@Override
	public void onStepEnd(MapView wv, PlayerControl player)
	{
		walkByKey(player);
	}
	
	
	@Override
	public void onClick(MapView wv, PlayerControl player, Vect mouse, boolean down)
	{
	}
	
	
	@Override
	public void onKey(MapView wv, PlayerControl player, int key, boolean down)
	{
		if(down) walkByKey(player);
	}
	
	
	private void walkByKey(PlayerControl player)
	{
		if (InputSystem.isKeyDown(Keys.LEFT)) {
			player.goWest();
		} else if (InputSystem.isKeyDown(Keys.RIGHT)) {
			player.goEast();
		} else if (InputSystem.isKeyDown(Keys.UP)) {
			player.goNorth();
		} else if (InputSystem.isKeyDown(Keys.DOWN)) {
			player.goSouth();
		}
	}
	
}
