package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.gui.MapView;
import mightypork.rogue.world.tile.Tile;


public class MIPClickPathfWalk implements MapInteractionPlugin {
	
	private static final int BTN = 0; // left
	
	
	@Override
	public void update(MapView view, PlayerControl pc, double delta)
	{
		if(pc.getPlayerEntity().pos.isMoving()) return;
		if (InputSystem.isMouseButtonDown(BTN)) {
			
			troToNav(view, pc, InputSystem.getMousePos());
		}
	}
	
	
	@Override
	public boolean onClick(MapView view, PlayerControl player, Vect mouse, int button, boolean down)
	{
		if (down || button != BTN) return false;
		
		return troToNav(view, player, mouse);
	}


	private boolean troToNav(MapView view, PlayerControl player, Vect mouse)
	{
		final Coord clicked = view.toWorldPos(mouse);
		
		Tile t = player.getLevel().getTile(clicked);
		if (!t.isWalkable() || !t.isExplored()) return false;
		
		player.navigateTo(clicked);
		return true;
	}
	
	
	@Override
	public boolean onKey(MapView view, PlayerControl player, int key, boolean down)
	{
		return false;
	}


	@Override
	public boolean onStepEnd(MapView mapView, PlayerControl player)
	{
		return false;
	}
	
}
