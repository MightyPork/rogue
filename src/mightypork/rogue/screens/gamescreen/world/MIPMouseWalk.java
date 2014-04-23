package mightypork.rogue.screens.gamescreen.world;


import mightypork.gamecore.input.InputSystem;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.WorldPos;
import mightypork.util.constraints.vect.Vect;
import mightypork.util.math.Polar;
import mightypork.util.math.Calc.Deg;


public class MIPMouseWalk implements MapInteractionPlugin {
	
	@Override
	public void onStepEnd(MapView wv, PlayerControl player)
	{
		if (InputSystem.isMouseButtonDown(0)) {
			// walk by holding btn
			onClick(wv, player, InputSystem.getMousePos());
		}
	}
	
	
	@Override
	public void onClick(MapView wv, PlayerControl player, Vect mouse)
	{
		WorldPos plpos = player.getPos();
		WorldPos clicked = wv.toWorldPos(mouse);
		
		Polar p = Polar.fromCoord(clicked.x - plpos.x, clicked.y - plpos.y);
		
		int dir = Deg.round90(p.getAngleDeg()) / 90;
		
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
	public void onKey(MapView wv, PlayerControl player, int key)
	{
	}
	
}
