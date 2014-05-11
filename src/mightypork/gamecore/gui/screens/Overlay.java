package mightypork.gamecore.gui.screens;


import java.util.Collection;
import java.util.LinkedHashSet;

import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.app.AppSubModule;
import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.gui.Enableable;
import mightypork.gamecore.gui.Hideable;
import mightypork.gamecore.gui.components.layout.ConstraintLayout;
import mightypork.gamecore.gui.events.LayoutChangeListener;
import mightypork.gamecore.input.KeyBinder;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.render.Renderable;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.constraints.vect.Vect;


/**
 * Abstract overlay.<br>
 * Overlay is connected to event bus and is renderable.
 * 
 * @author MightyPork
 */
public abstract class Overlay extends AppSubModule implements Comparable<Overlay>, Updateable, Renderable, KeyBinder, Hideable, Enableable, LayoutChangeListener {
	
	private boolean visible = true;
	private boolean enabled = true;
	
	private final KeyBindingPool keybindings = new KeyBindingPool();
	
	/** Root layout, rendered and attached to the event bus. */
	protected final ConstraintLayout root;
	
	/** Constraint: Mouse position. */
	protected final Vect mouse;
	
	/** Extra rendered items (outside root) */
	protected final Collection<Renderable> rendered = new LinkedHashSet<>();
	
	/** Extra updated items (outside root - those can just implement Updateable) */
	protected final Collection<Updateable> updated = new LinkedHashSet<>();
	
	
	public Overlay(AppAccess app)
	{
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
		root.setVisible(visible);
	}
	
	@Override
	public void enable(boolean yes)
	{
		this.enabled  = yes;
		root.enable(yes);
	}
	
	@Override
	public boolean isEnabled()
	{
		return enabled;
	}
	
	/**
	 * Get rendering layer
	 * 
	 * @return higher = on top.
	 */
	@DefaultImpl
	public abstract int getZIndex();
	
	
	/**
	 * Get event bus listening priority - useful to block incoming events.
	 * 
	 * @return higher = first.
	 */
	public int getEventPriority()
	{
		return getZIndex();
	}
	
	
	/**
	 * Render the overlay. The caller MUST check for visibility himself.
	 */
	@Override
	public void render()
	{
		if(!isVisible()) return;
		
		for (final Renderable r : rendered) {
			r.render();
		}
	}
	
	
	@Override
	public void update(double delta)
	{
		if(!isEnabled()) return;
		
		for (final Updateable u : updated) {
			u.update(delta);
		}
	}
	
	
	@Override
	public int compareTo(Overlay o)
	{
		return o.getEventPriority() - getEventPriority();
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
