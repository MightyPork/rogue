package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.util.math.Calc.Deg;
import mightypork.gamecore.util.math.Polar;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.gui.MapView;


public class MIPMouseWalk implements MapInteractionPlugin {
	
	@Override
	public void onStepEnd(MapView world, PlayerControl player)
	{
		if (InputSystem.isMouseButtonDown(0)) {
			// walk by holding btn
			onClick(world, player, InputSystem.getMousePos(), 0, true);
		}
	}
	
	
	@Override
	public void onClick(MapView world, PlayerControl player, Vect mouse, int button, boolean down)
	{
		if (!down) return;
		
		final Coord plpos = player.getCoord();
		final Coord clicked = world.toWorldPos(mouse);
		
		final Polar p = Polar.fromCoord(clicked.x - plpos.x, clicked.y - plpos.y);
		
		final int dir = Deg.round90(p.getAngleDeg()) / 90;
		
		switch (dir) {
			case 0:
				player.goEast();
				return;
				
			case 1:
				player.goSouth();
				return;
				
			case 2:
				player.goWest();
				return;
				
			case 3:
				player.goNorth();
				return;
		}
	}
	
	
	@Override
	public void onKey(MapView world, PlayerControl player, int key, boolean down)
	{
	}
	
}
