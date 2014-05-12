package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.util.math.Calc.Deg;
import mightypork.gamecore.util.math.Polar;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.entity.impl.PlayerEntity;
import mightypork.rogue.world.events.PlayerStepEndListener;
import mightypork.rogue.world.gui.MapView;
import mightypork.rogue.world.tile.Tile;


public class MIPMouse extends MapInteractionPlugin implements PlayerStepEndListener, Updateable {
	
	private static final int LEFT = 0; // left
	private static final int RIGHT = 1; // left
	
	
	public MIPMouse(MapView mapView)
	{
		super(mapView);
	}
	
	
	@Override
	public void update(double delta)
	{
		if (isImmobile()) return;
		
		final Vect pos = InputSystem.getMousePos();
		if (!pos.isInside(mapView)) return;
		
		if (InputSystem.isMouseButtonDown(LEFT)) {
			if (mouseWalk(pos)) return;
			if (mapView.plc.getPlayer().isMoving() && troToNav(pos)) return;
		}
	}
	
	
	@Override
	public boolean onClick(Vect mouse, int button, boolean down)
	{
		if (isImmobile()) {
			return false;
		}
		
		final Vect pos = mapView.toWorldPos(mouse);
		
		if (button == LEFT && !down) {
			// try to click tile
			
			if (mapView.plc.clickTile(pos)) return true;
		}
		
		final Tile t = mapView.plc.getLevel().getTile(Coord.fromVect(pos));
		if (button == RIGHT && !down && t.isWalkable()) {
			if (troToNav(mouse)) return true;
			return mouseWalk(mouse);
		}
		
		return false;
	}
	
	
	private boolean troToNav(Vect mouse)
	{
		if (isImmobile()) return false;
		
		final Coord plpos = mapView.plc.getPlayer().getCoord();
		
		final Coord clicked = Coord.fromVect(mapView.toWorldPos(mouse));
		if (clicked.equals(plpos)) return false;
		
		final Tile t = mapView.plc.getLevel().getTile(clicked);
		if (!t.isWalkable() || !t.isExplored()) return false;
		
		mapView.plc.navigateTo(clicked);
		return true;
	}
	
	
	private boolean mouseWalk(Vect pos)
	{
		if (isImmobile()) return false;
		
		final Coord plpos = mapView.plc.getPlayer().getCoord();
		final Coord clicked = Coord.fromVect(mapView.toWorldPos(pos));
		if (clicked.equals(plpos)) return false;
		
		final Polar p = Polar.fromCoord(clicked.x - plpos.x, clicked.y - plpos.y);
		
		final int dir = Deg.round90(p.getAngleDeg()) / 90;
		
		switch (dir) {
			case 0:
				return mapView.plc.tryGo(Sides.E);
				
			case 1:
				return mapView.plc.tryGo(Sides.S);
				
			case 2:
				return mapView.plc.tryGo(Sides.W);
				
			case 3:
				return mapView.plc.tryGo(Sides.N);
		}
		
		return false;
	}
	
	
	@Override
	public void onStepFinished(PlayerEntity player)
	{
		if (isImmobile()) return;
		
		final Vect pos = InputSystem.getMousePos();
		if (!pos.isInside(mapView)) return;
		
		if (InputSystem.isMouseButtonDown(LEFT)) {
			if (mouseWalk(pos)) return;
			if (mapView.plc.getPlayer().isMoving() && troToNav(pos)) return;
		}
	}
	
}
