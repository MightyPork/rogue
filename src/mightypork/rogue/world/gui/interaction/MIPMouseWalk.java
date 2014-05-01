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
	public void update(MapView view, PlayerControl pc, double delta)
	{
		if (InputSystem.isMouseButtonDown(0)) {
			// walk by holding btn
			tryWalk(view, pc, InputSystem.getMousePos());
		}
	}
	
	
	@Override
	public boolean onClick(MapView view, PlayerControl pc, Vect mouse, int button, boolean down)
	{
		if (!down || button != 0) return false;
		
		tryWalk(view, pc, mouse);
		return true;
	}
	
	
	private void tryWalk(MapView view, PlayerControl pc, Vect pos)
	{
		if (pc.getPlayerEntity().pos.isMoving()) return;
		
		final Coord plpos = pc.getCoord();
		final Coord clicked = view.toWorldPos(pos);
		
		final Polar p = Polar.fromCoord(clicked.x - plpos.x, clicked.y - plpos.y);
		
		final int dir = Deg.round90(p.getAngleDeg()) / 90;
		
		switch (dir) {
			case 0:
				pc.goEast();
				break;
			
			case 1:
				pc.goSouth();
				break;
			
			case 2:
				pc.goWest();
				break;
			
			case 3:
				pc.goNorth();
				break;
		}
	}
	
	
	@Override
	public boolean onKey(MapView world, PlayerControl player, int key, boolean down)
	{
		return false;
	}
	
	
	@Override
	public boolean onStepEnd(MapView view, PlayerControl pc)
	{
		if(!InputSystem.isMouseButtonDown(0)) return false;

		tryWalk(view, pc, InputSystem.getMousePos());
		return true;
	}
	
}
