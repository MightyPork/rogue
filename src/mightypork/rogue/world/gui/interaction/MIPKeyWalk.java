package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.gamecore.util.math.algo.Step;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.gui.MapView;


public class MIPKeyWalk implements MapInteractionPlugin {
	
	@Override
	public boolean onStepEnd(MapView view, PlayerControl player)
	{
		return walkByKey(player);
	}
	
	
	@Override
	public boolean onClick(MapView view, PlayerControl player, Vect mouse, int button, boolean down)
	{
		return false;
	}
	
	
	@Override
	public boolean onKey(MapView view, PlayerControl player, int key, boolean down)
	{
		if (down) return walkByKey(player);
		return false;
	}
	
	
	private boolean walkByKey(PlayerControl player)
	{
		final int[] keys = { Keys.LEFT, Keys.RIGHT, Keys.UP, Keys.DOWN };
		final Step[] sides = { Sides.W, Sides.E, Sides.N, Sides.S };
		
		for (int i = 0; i < 4; i++) {
			if (InputSystem.isKeyDown(keys[i])) {
				
				final Step side = sides[i];
				if (player.canGo(side)) {
					player.go(side);
					return true;
				} else {
					return player.clickTile(side);
				}
				
			}
		}
		return false;
	}
	
	
	@Override
	public void update(MapView mapView, PlayerControl pc, double delta)
	{
	}
}
