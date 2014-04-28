package mightypork.rogue.screens.gamescreen.world;


import java.util.HashSet;
import java.util.Set;

import mightypork.gamecore.control.events.input.KeyEvent;
import mightypork.gamecore.control.events.input.KeyListener;
import mightypork.gamecore.control.events.input.MouseButtonEvent;
import mightypork.gamecore.control.events.input.MouseButtonListener;
import mightypork.gamecore.gui.components.InputComponent;
import mightypork.gamecore.input.Keys;
import mightypork.rogue.world.Coord;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.WorldRenderer;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.modules.EntityMoveListener;
import mightypork.util.math.Easing;
import mightypork.util.math.constraints.num.Num;
import mightypork.util.math.constraints.num.mutable.NumAnimated;
import mightypork.util.math.constraints.vect.Vect;
import mightypork.util.timing.Updateable;


public class MapView extends InputComponent implements KeyListener, MouseButtonListener, EntityMoveListener, Updateable {
	
	protected final WorldRenderer worldRenderer;
	private final PlayerControl pc;
	
	private final Set<MapInteractionPlugin> plugins = new HashSet<>();
	private final NumAnimated zoom = new NumAnimated(0, Easing.SINE_BOTH);
	
	private final Num tileSize;
	
	
	public MapView()
	{
		this.tileSize = height().min(width()).div(8).max(32).mul(Num.make(1).sub(zoom.mul(0.66)));
		this.worldRenderer = new WorldRenderer(this, tileSize);
		pc = WorldProvider.get().getPlayerControl();
		pc.addMoveListener(this);
	}
	
	
	@Override
	protected void renderComponent()
	{
		worldRenderer.render();
	}
	
	
	/**
	 * Get tile coord at a screen position
	 * 
	 * @param pos position on screen (px)
	 * @return position on map (tiles)
	 */
	public Coord toWorldPos(Vect pos)
	{
		return worldRenderer.getClickedTile(pos);
	}
	
	
	@Override
	public void onStepFinished(Entity entity)
	{
		for (final MapInteractionPlugin p : plugins) {
			p.onStepEnd(this, pc);
		}
	}
	
	
	@Override
	public void onPathFinished(Entity entity)
	{
		for (final MapInteractionPlugin p : plugins) {
			p.onStepEnd(this, pc);
		}
	}
	
	
	@Override
	public void onPathInterrupted(Entity entity)
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
			p.onClick(this, pc, event.getPos(), event.getButton(), event.isDown());
		}
		
		event.consume(); // only our clicks.
	}
	
	
	@Override
	public void receive(KeyEvent event)
	{
		for (final MapInteractionPlugin p : plugins) {
			p.onKey(this, pc, event.getKey(), event.isDown());
		}
		
		if(event.getKey() == Keys.Z) {
			if(event.isDown()) {
				zoom.fadeIn(1);
			} else {
				zoom.fadeOut(1);
			}
			
			
		}
		
		// don't consume key events, can be useful for others.
	}
	
	
	public void addPlugin(MapInteractionPlugin plugin)
	{
		plugins.add(plugin);
	}


	@Override
	public void update(double delta)
	{
		zoom.update(delta);
	}
}
