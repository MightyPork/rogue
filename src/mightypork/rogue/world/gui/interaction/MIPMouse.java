package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.util.math.Calc.Deg;
import mightypork.gamecore.util.math.Polar;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.entity.entities.PlayerEntity;
import mightypork.rogue.world.gui.MapView;
import mightypork.rogue.world.tile.Tile;


public class MIPMouse extends MapInteractionPlugin {
	
	private static final int BTN = 0; // left
	
	
	public MIPMouse(MapView mapView)
	{
		super(mapView);
	}
	
	
	@Override
	public void update(double delta)
	{
		if (!InputSystem.isKeyDown(Keys.L_SHIFT)) return;
		
		final Vect pos = InputSystem.getMousePos();
		
		if (InputSystem.isMouseButtonDown(BTN)) {
			if (mouseWalk(pos)) return;
			if (troToNav(pos)) return;
		}
	}
	
	
	@Override
	public boolean onClick(Vect mouse, int button, boolean down)
	{
		if (button != BTN) return false;
		final Coord pos = mapView.toWorldPos(mouse);
		final Tile t = mapView.playerControl.getLevel().getTile(pos);
		
		if (t.onClick()) return true;
		
		if (!down && t.isWalkable()) {
			if (troToNav(mouse)) return true;
			return mouseWalk(mouse);
		}
		
		return false;
	}
	
	
	private boolean troToNav(Vect mouse)
	{
		final Coord plpos = mapView.playerControl.getCoord();
		final Coord clicked = mapView.toWorldPos(mouse);
		if (clicked.equals(plpos)) return false;
		
		final Tile t = mapView.playerControl.getLevel().getTile(clicked);
		if (!t.isWalkable() || !t.isExplored()) return false;
		
		mapView.playerControl.navigateTo(clicked);
		return true;
	}
	
	
	private boolean mouseWalk(Vect pos)
	{
		final Coord plpos = mapView.playerControl.getCoord();
		final Coord clicked = mapView.toWorldPos(pos);
		if (clicked.equals(plpos)) return false;
		
		final Polar p = Polar.fromCoord(clicked.x - plpos.x, clicked.y - plpos.y);
		
		final int dir = Deg.round90(p.getAngleDeg()) / 90;
		
		switch (dir) {
			case 0:
				return mapView.playerControl.tryGo(Sides.E);
				
			case 1:
				return mapView.playerControl.tryGo(Sides.S);
				
			case 2:
				return mapView.playerControl.tryGo(Sides.W);
				
			case 3:
				return mapView.playerControl.tryGo(Sides.N);
		}
		
		return false;
	}
	
	
	@Override
	public boolean onKey(int key, boolean down)
	{
		return false;
	}
	
	
	@Override
	public void onStepFinished(PlayerEntity player)
	{
		if (!InputSystem.isKeyDown(Keys.L_SHIFT)) return;
		
		final Vect pos = InputSystem.getMousePos();
		
		if (InputSystem.isMouseButtonDown(BTN)) {
			if (mouseWalk(pos)) return;
			if (troToNav(pos)) return;
		}
	}
	
}
