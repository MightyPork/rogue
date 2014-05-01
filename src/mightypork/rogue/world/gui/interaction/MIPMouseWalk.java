package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.util.math.Calc.Deg;
import mightypork.gamecore.util.math.Polar;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.gui.MapView;
import mightypork.rogue.world.tile.Tile;


public class MIPMouseWalk implements MapInteractionPlugin {
	
	@Override
	public void update(MapView world, PlayerControl player, double delta)
	{
		if (InputSystem.isMouseButtonDown(0)) {
			// walk by holding btn
			onClick(world, player, InputSystem.getMousePos(), 0, true);
		}
	}
	
	
	@Override
	public boolean onClick(MapView world, PlayerControl player, Vect mouse, int button, boolean down)
	{
		if (!down || button != 0) return false;
		
		final Coord plpos = player.getCoord();
		final Coord clicked = world.toWorldPos(mouse);
		
		final Tile t = player.getLevel().getTile(clicked);
		
		final Polar p = Polar.fromCoord(clicked.x - plpos.x, clicked.y - plpos.y);
		
		final int dir = Deg.round90(p.getAngleDeg()) / 90;
		
		switch (dir) {
			case 0:
				player.goEast();
				break;
			
			case 1:
				player.goSouth();
				break;
			
			case 2:
				player.goWest();
				break;
			
			case 3:
				player.goNorth();
				break;
		}
		return true;
	}
	
	
	@Override
	public boolean onKey(MapView world, PlayerControl player, int key, boolean down)
	{
		return false;
	}
	
	
	@Override
	public boolean onStepEnd(MapView mapView, PlayerControl player)
	{
		return false;
	}
	
}
