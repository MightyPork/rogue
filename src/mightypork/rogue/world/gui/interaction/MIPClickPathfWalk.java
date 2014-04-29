package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.input.InputSystem;
import mightypork.rogue.world.Coord;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.gui.MapView;
import mightypork.util.math.constraints.vect.Vect;


public class MIPClickPathfWalk implements MapInteractionPlugin {
	
	private static final int BTN = 0; // left
	
	
	@Override
	public void onStepEnd(MapView view, PlayerControl player)
	{
		if (InputSystem.isMouseButtonDown(BTN)) {
			final Coord clicked = view.toWorldPos(InputSystem.getMousePos());
			player.navigateTo(clicked);
		}
	}
	
	
	@Override
	public void onClick(MapView view, PlayerControl player, Vect mouse, int button, boolean down)
	{
		if (!down || button != BTN) return;
		
		final Coord clicked = view.toWorldPos(mouse);
		
		player.navigateTo(clicked);
	}
	
	
	@Override
	public void onKey(MapView view, PlayerControl player, int key, boolean down)
	{
	}
	
}
