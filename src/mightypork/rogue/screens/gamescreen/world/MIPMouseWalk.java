package mightypork.rogue.screens.gamescreen.world;


import mightypork.gamecore.input.InputSystem;
import mightypork.rogue.world.Coord;
import mightypork.rogue.world.PlayerControl;
import mightypork.util.math.Calc.Deg;
import mightypork.util.math.Polar;
import mightypork.util.math.constraints.vect.Vect;


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
