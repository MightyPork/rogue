package mightypork.rogue.world.gui;


import java.util.LinkedHashSet;
import java.util.Set;

import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.gui.components.InputComponent;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.input.events.KeyEvent;
import mightypork.gamecore.input.events.KeyListener;
import mightypork.gamecore.input.events.MouseButtonEvent;
import mightypork.gamecore.input.events.MouseButtonListener;
import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.WorldRenderer;
import mightypork.rogue.world.entity.modules.EntityMoveListener;
import mightypork.rogue.world.gui.interaction.MapInteractionPlugin;


/**
 * Level display component
 * 
 * @author MightyPork
 */
public class MapView extends InputComponent implements KeyListener, MouseButtonListener, EntityMoveListener, Updateable {
	
	protected final WorldRenderer worldRenderer;
	private final PlayerControl pc;
	
	private final Set<MapInteractionPlugin> plugins = new LinkedHashSet<>();
	private final NumAnimated zoom = new NumAnimated(0, Easing.SINE_BOTH);
	private boolean zoom_in = true;
	
	private final Num tileSize;
	
	
	public MapView()
	{
		this.tileSize = height().min(width()).div(10).max(32).mul(Num.make(1).sub(zoom.mul(0.5)));
		this.worldRenderer = new WorldRenderer(this, tileSize);
		pc = WorldProvider.get().getPlayerControl();
		pc.addMoveListener(this);
		
		zoom.setDefaultDuration(0.5);
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
	public void onStepFinished()
	{
		for (final MapInteractionPlugin p : plugins) {
			if (p.onStepEnd(this, pc)) break;
		}
	}
	
	
	@Override
	public void onPathFinished()
	{
		for (final MapInteractionPlugin p : plugins) {
			if (p.onStepEnd(this, pc)) break;
		}
	}
	
	
	@Override
	public void onPathInterrupted()
	{
		for (final MapInteractionPlugin p : plugins) {
			if (p.onStepEnd(this, pc)) break;
		}
	}
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		if (!event.isOver(this)) return;
		
		for (final MapInteractionPlugin p : plugins) {
			if (p.onClick(this, pc, event.getPos(), event.getButton(), event.isDown())) {
				event.consume();
				break;
			}
		}
		
		if (event.isWheelEvent()) {
			final int delta = event.getWheelDelta();
			if (!zoom.isFinished()) return;
			if (delta < 0) {
				zoom.fadeIn();
				zoom_in = false;
			} else {
				zoom.fadeOut();
				zoom_in = true;
			}
		}
	}
	
	
	@Override
	public void receive(KeyEvent event)
	{
		for (final MapInteractionPlugin p : plugins) {
			if (p.onKey(this, pc, event.getKey(), event.isDown())) break;
		}
		
		if (event.getKey() == Keys.Z && event.isDown()) {
			if (zoom_in) {
				zoom.fadeIn();
				zoom_in = false;
			} else {
				zoom.fadeOut();
				zoom_in = true;
			}
		}
		
		// don't consume key events, can be useful for others.
	}
	
	
	/**
	 * Add interaction plugin
	 * 
	 * @param plugin
	 */
	public void addPlugin(MapInteractionPlugin plugin)
	{
		plugins.add(plugin);
	}
	
	
	@Override
	public void update(double delta)
	{
		for (final MapInteractionPlugin p : plugins) {
			p.update(this, pc, delta);
		}
		zoom.update(delta);
	}
}
