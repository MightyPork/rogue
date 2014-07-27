package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.backends.lwjgl.LwjglInputModule;
import mightypork.rogue.world.entity.impl.EntityPlayer;
import mightypork.rogue.world.events.PlayerStepEndListener;
import mightypork.rogue.world.gui.MapView;
import mightypork.rogue.world.tile.Tile;
import mightypork.utils.interfaces.Updateable;
import mightypork.utils.math.Polar;
import mightypork.utils.math.algo.Coord;
import mightypork.utils.math.algo.Moves;
import mightypork.utils.math.angles.Deg;
import mightypork.utils.math.constraints.vect.Vect;


public class MIPMouse extends MapInteractionPlugin implements PlayerStepEndListener, Updateable {
	
	private static final int LEFT = 0; // left
	private static final int RIGHT = 1; // left
	
	
	public MIPMouse(MapView mapView) {
		super(mapView);
	}
	
	
	@Override
	public void update(double delta)
	{
		if (isImmobile()) return;
		
		final Vect pos = LwjglInputModule.getMousePos();
		if (!pos.isInside(mapView)) return;
		
		if (LwjglInputModule.isMouseButtonDown(LEFT)) {
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
		
		final int dir = Deg.roundTo90(p.getAngleDeg()) / 90;
		
		switch (dir) {
			case 0:
				return mapView.plc.tryGo(Moves.E);
				
			case 1:
				return mapView.plc.tryGo(Moves.S);
				
			case 2:
				return mapView.plc.tryGo(Moves.W);
				
			case 3:
				return mapView.plc.tryGo(Moves.N);
		}
		
		return false;
	}
	
	
	@Override
	public void onStepFinished(EntityPlayer player)
	{
		if (isImmobile()) return;
		
		final Vect pos = LwjglInputModule.getMousePos();
		if (!pos.isInside(mapView)) return;
		
		if (LwjglInputModule.isMouseButtonDown(LEFT)) {
			if (mouseWalk(pos)) return;
			if (mapView.plc.getPlayer().isMoving() && troToNav(pos)) return;
		}
	}
	
}
