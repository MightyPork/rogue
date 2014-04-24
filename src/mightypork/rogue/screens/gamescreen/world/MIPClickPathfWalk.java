package mightypork.rogue.screens.gamescreen.world;


import mightypork.gamecore.input.InputSystem;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.WorldPos;
import mightypork.util.math.constraints.vect.Vect;


public class MIPClickPathfWalk implements MapInteractionPlugin {
	
	@Override
	public void onStepEnd(MapView wv, PlayerControl player)
	{
		if (InputSystem.isMouseButtonDown(0)) {
			final WorldPos clicked = wv.toWorldPos(InputSystem.getMousePos());
			player.navigateTo(clicked);
		}
	}
	
	
	@Override
	public void onClick(MapView wv, PlayerControl player, Vect mouse, int button, boolean down)
	{
		if (!down) return;
		
		final WorldPos clicked = wv.toWorldPos(mouse);
		
		player.navigateTo(clicked);
	}
	
	
	@Override
	public void onKey(MapView wv, PlayerControl player, int key, boolean down)
	{
	}
	
}
