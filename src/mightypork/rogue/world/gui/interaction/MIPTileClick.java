package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.gui.MapView;


public class MIPTileClick implements MapInteractionPlugin {
	
	@Override
	public boolean onStepEnd(MapView wv, PlayerControl player)
	{
		return false;
	}
	
	
	@Override
	public boolean onClick(MapView wv, PlayerControl player, Vect mouse, int button, boolean down)
	{
		if (down && button == 1) { // right button
			final Coord pos = wv.toWorldPos(mouse);
			player.clickTile(pos);
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public boolean onKey(MapView wv, PlayerControl player, int key, boolean down)
	{
		return false;
	}
	
	
	@Override
	public void update(MapView mapView, PlayerControl pc, double delta)
	{
	}
}
