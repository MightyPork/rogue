package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.gui.MapView;


public class MIPKeyWalk implements MapInteractionPlugin {
	
	@Override
	public void onStepEnd(MapView view, PlayerControl player)
	{
		walkByKey(player);
	}
	
	
	@Override
	public void onClick(MapView view, PlayerControl player, Vect mouse, int button, boolean down)
	{
	}
	
	
	@Override
	public void onKey(MapView view, PlayerControl player, int key, boolean down)
	{
		if (down) walkByKey(player);
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
