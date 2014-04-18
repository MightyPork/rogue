package mightypork.gamecore.gui.screens;


import java.util.Collection;
import java.util.LinkedHashSet;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppSubModule;
import mightypork.gamecore.control.events.LayoutChangeEvent;
import mightypork.gamecore.gui.Hideable;
import mightypork.gamecore.gui.components.layout.ConstraintLayout;
import mightypork.gamecore.input.KeyBinder;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.render.Renderable;
import mightypork.util.annotations.DefaultImpl;
import mightypork.util.constraints.vect.Vect;
import mightypork.util.control.timing.Updateable;


/**
 * Abstract overlay.<br>
 * Overlay is connected to event bus and is renderable.
 * 
 * @author MightyPork
 */
public abstract class Overlay extends AppSubModule implements Updateable, Comparable<Overlay>, Renderable, KeyBinder, Hideable, LayoutChangeEvent.Listener {
	
	private boolean visible = true;
	private final KeyBindingPool keybindings = new KeyBindingPool();
	
	/** Root layout, rendered and attached to the event bus. */
	protected final ConstraintLayout root;
	
	/** Constraint: Mouse position. */
	protected final Vect mouse;
	
	/** Extra rendered items (outside root) */
	protected final Collection<Renderable> rendered = new LinkedHashSet<>();
	
	/** Extra updated items (outside root - those can just implement Updateable) */
	protected final Collection<Updateable> updated = new LinkedHashSet<>();
	
	
	public Overlay(AppAccess app) {
		super(app);
		
		this.mouse = getInput().getMousePos();
		
		this.root = new ConstraintLayout(app, getDisplay());
		addChildClient(root);
		addChildClient(keybindings);
		
		rendered.add(root);
	}
	
	
	@Override
	public final void bindKey(KeyStroke stroke, Runnable task)
	{
		keybindings.bindKey(stroke, task);
	}
	
	
	@Override
	public final void unbindKey(KeyStroke stroke)
	{
		keybindings.unbindKey(stroke);
	}
	
	
	@Override
	public final boolean isVisible()
	{
		return visible;
	}
	
	
	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	
	@Override
	public final int compareTo(Overlay o)
	{
		return getPriority() - o.getPriority();
	}
	
	
	/**
	 * Get rendering priority
	 * 
	 * @return higher = on top.
	 */
	@DefaultImpl
	public abstract int getPriority();
	
	
	/**
	 * Render the overlay. The caller MUST check for visibility himself.
	 */
	@Override
	public void render()
	{
		for (final Renderable r : rendered) {
			r.render();
		}
	}
	
	
	@Override
	public void update(double delta)
	{
		for (final Updateable u : updated) {
			u.update(delta);
		}
	}
	
	
	/**
	 * <p>
	 * Screen size changed.
	 * </p>
	 * <p>
	 * Layouts / components should listen for this event and update their cached
	 * constraints; components added to root or directly to this overlay as
	 * child clients will receive the event.
	 * </p>
	 */
	@Override
	@DefaultImpl
	public void onLayoutChanged()
	{
	}
	
}