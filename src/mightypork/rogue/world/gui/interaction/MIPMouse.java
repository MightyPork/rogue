package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.util.math.Calc.Deg;
import mightypork.gamecore.util.math.Polar;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.gui.MapView;
import mightypork.rogue.world.tile.Tile;


public class MIPMouse implements MapInteractionPlugin {
	
	private static final int BTN = 0; // left
	
	
	@Override
	public void update(MapView view, PlayerControl pc, double delta)
	{
		if (!InputSystem.isKeyDown(Keys.L_SHIFT)) return;
		
		final Vect pos = InputSystem.getMousePos();
		
		if (InputSystem.isMouseButtonDown(BTN)) {
			if (mouseWalk(view, pc, pos)) return;
			if (troToNav(view, pc, pos)) return;
		}
	}
	
	
	@Override
	public boolean onClick(MapView view, PlayerControl pc, Vect mouse, int button, boolean down)
	{
		if (button != BTN) return false;
		final Coord pos = view.toWorldPos(mouse);
		final Tile t = pc.getLevel().getTile(pos);
		
		if (t.onClick()) return true;
		
		if (!down && t.isWalkable()) {
			if (troToNav(view, pc, mouse)) return true;
			return mouseWalk(view, pc, mouse);
		}
		
		return false;
	}
	
	
	private boolean troToNav(MapView view, PlayerControl pc, Vect mouse)
	{
		final Coord plpos = pc.getCoord();
		final Coord clicked = view.toWorldPos(mouse);
		if (clicked.equals(plpos)) return false;
		
		final Tile t = pc.getLevel().getTile(clicked);
		if (!t.isWalkable() || !t.isExplored()) return false;
		
		pc.navigateTo(clicked);
		return true;
	}
	
	
	private boolean mouseWalk(MapView view, PlayerControl pc, Vect pos)
	{
		final Coord plpos = pc.getCoord();
		final Coord clicked = view.toWorldPos(pos);
		if (clicked.equals(plpos)) return false;
		
		final Polar p = Polar.fromCoord(clicked.x - plpos.x, clicked.y - plpos.y);
		
		final int dir = Deg.round90(p.getAngleDeg()) / 90;
		
		switch (dir) {
			case 0:
				return pc.tryGo(Sides.E);
				
			case 1:
				return pc.tryGo(Sides.S);
				
			case 2:
				return pc.tryGo(Sides.W);
				
			case 3:
				return pc.tryGo(Sides.N);
		}
		
		return false;
	}
	
	
	@Override
	public boolean onKey(MapView view, PlayerControl player, int key, boolean down)
	{
		return false;
	}
	
	
	@Override
	public boolean onStepEnd(MapView view, PlayerControl pc)
	{
		if (!InputSystem.isKeyDown(Keys.L_SHIFT)) return false;
		
		final Vect pos = InputSystem.getMousePos();
		
		if (InputSystem.isMouseButtonDown(BTN)) {
			if (mouseWalk(view, pc, pos)) return true;
			if (troToNav(view, pc, pos)) return true;
		}
		
		return false;
	}
	
}
