package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.gamecore.util.math.algo.Step;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.gui.MapView;


public class MIPKeyboard implements MapInteractionPlugin {
	
	private static final int[] keys = { Keys.LEFT, Keys.RIGHT, Keys.UP, Keys.DOWN };
	private static final Step[] sides = { Sides.W, Sides.E, Sides.N, Sides.S };
	
	
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
		if (down) return false; // not interested
		
		for (int i = 0; i < 4; i++) {
			if (key == keys[i]) {
				return player.clickTile(sides[i]);
			}
		}
		
		return false;
	}
	
	
	private boolean walkByKey(PlayerControl pc)
	{
		if (pc.getPlayerEntity().pos.isMoving()) return false;
		
		for (int i = 0; i < 4; i++) {
			if (InputSystem.isKeyDown(keys[i])) {
				
				final Step side = sides[i];
				if (pc.canGo(side)) {
					pc.go(side);
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	
	@Override
	public void update(MapView mapView, PlayerControl pc, double delta)
	{
		walkByKey(pc);
	}
}
