package mightypork.rogue.screens.gamescreen.world;


import java.util.HashSet;
import java.util.Set;

import mightypork.gamecore.control.events.input.KeyEvent;
import mightypork.gamecore.control.events.input.KeyListener;
import mightypork.gamecore.control.events.input.MouseButtonEvent;
import mightypork.gamecore.control.events.input.MouseButtonListener;
import mightypork.gamecore.gui.components.InputComponent;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldPos;
import mightypork.rogue.world.WorldRenderer;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.models.EntityMoveListener;
import mightypork.rogue.world.level.Level;
import mightypork.util.math.constraints.vect.Vect;


public class MapView extends InputComponent implements KeyListener, MouseButtonListener, EntityMoveListener {
	
	protected final WorldRenderer worldRenderer;
	protected final World world;
	private final PlayerControl pc;
	
	private final Set<MapInteractionPlugin> plugins = new HashSet<>();
	
	
	public MapView(World world)
	{
		this.world = world;
		this.worldRenderer = new WorldRenderer(world, this, 8, 8, 64);
		pc = world.getPlayerControl();
		pc.addMoveListener(this);
	}
	
	
	@Override
	protected void renderComponent()
	{
		worldRenderer.render();
	}
	
	
	@Override
	public void updateLayout()
	{
		worldRenderer.poll(); // update sizing
	}
	
	
	/**
	 * Get tile coord at a screen position
	 * 
	 * @param pos position on screen (px)
	 * @return position on map (tiles)
	 */
	public WorldPos toWorldPos(Vect pos)
	{
		return worldRenderer.getClickedTile(pos);
	}
	
	
	@Override
	public void onStepFinished(Entity entity, World world, Level level)
	{
		for (final MapInteractionPlugin p : plugins) {
			p.onStepEnd(this, pc);
		}
	}
	
	
	@Override
	public void onPathFinished(Entity entity, World world, Level level)
	{
		for (final MapInteractionPlugin p : plugins) {
			p.onStepEnd(this, pc);
		}
	}
	
	
	@Override
	public void onPathInterrupted(Entity entity, World world, Level level)
	{
		for (final MapInteractionPlugin p : plugins) {
			p.onStepEnd(this, pc);
		}
	}
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		if (!event.isOver(this)) return;
		
		for (final MapInteractionPlugin p : plugins) {
			p.onClick(this, pc, event.getPos(), event.isDown());
		}
		
		event.consume(); // only our clicks.
	}
	
	
	@Override
	public void receive(KeyEvent event)
	{
		for (final MapInteractionPlugin p : plugins) {
			p.onKey(this, pc, event.getKey(), event.isDown());
		}
		
		// don't consume key events, can be useful for others.
	}
	
	
	public void addPlugin(MapInteractionPlugin plugin)
	{
		plugins.add(plugin);
	}
}
