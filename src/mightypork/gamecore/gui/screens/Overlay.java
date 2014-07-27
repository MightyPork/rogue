package mightypork.gamecore.gui.screens;


import java.util.ArrayList;
import java.util.Collection;

import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.Renderable;
import mightypork.gamecore.gui.components.layout.ConstraintLayout;
import mightypork.gamecore.gui.events.LayoutChangeListener;
import mightypork.gamecore.input.KeyBinder;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Trigger;
import mightypork.utils.annotations.Stub;
import mightypork.utils.eventbus.clients.BusNode;
import mightypork.utils.interfaces.Enableable;
import mightypork.utils.interfaces.Hideable;
import mightypork.utils.interfaces.Updateable;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Abstract overlay.<br>
 * Overlay is connected to event bus and is renderable.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class Overlay extends BusNode implements Comparable<Overlay>, Updateable, Renderable, KeyBinder, Hideable, Enableable, LayoutChangeListener {
	
	private boolean visible = true;
	private boolean enabled = true;
	
	private final KeyBindingPool keybindings = new KeyBindingPool();
	
	/** Root layout, rendered and attached to the event bus. */
	protected final ConstraintLayout root;
	
	/** Constraint: Mouse position. */
	protected final Vect mouse;
	
	/** Extra rendered items (outside root) */
	protected final Collection<Renderable> rendered = new ArrayList<>();
	
	/** Extra updated items (outside root - those can just implement Updateable) */
	protected final Collection<Updateable> updated = new ArrayList<>();
	private Num alphaMul = Num.ONE;
	
	
	public Overlay() {
		
		this.mouse = App.input().getMousePos();
		
		this.root = new ConstraintLayout(App.gfx().getRect());
		addChildClient(root);
		addChildClient(keybindings);
		
		rendered.add(root);
	}
	
	
	@Override
	public final void bindKey(KeyStroke stroke, Trigger edge, Runnable task)
	{
		keybindings.bindKey(stroke, edge, task);
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
		if (visible != this.visible) {
			this.visible = visible;
			root.setVisible(visible);
		}
	}
	
	
	@Override
	public void setEnabled(boolean yes)
	{
		if (enabled != yes) {
			this.enabled = yes;
			root.setEnabled(yes);
		}
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
	@Stub
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
		if (!isVisible()) return;
		
		Color.pushAlpha(alphaMul);
		for (final Renderable r : rendered) {
			r.render();
		}
		
		Color.popAlpha();
	}
	
	
	@Override
	public void update(double delta)
	{
		if (!isEnabled()) return;
		
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
	@Stub
	public void onLayoutChanged()
	{
	}
	
	
	public void setAlpha(Num alpha)
	{
		this.alphaMul = alpha;
	}
	
	
	public void setAlpha(double alpha)
	{
		this.alphaMul = Num.make(alpha);
	}
	
	
	public void show()
	{
		setVisible(true);
		setEnabled(true);
	}
	
	
	public void hide()
	{
		setVisible(false);
		setEnabled(false);
	}
	
	
	@Override
	public boolean isListening()
	{
		return (isVisible() || isEnabled());
	}
	
	
	@Override
	public boolean doesDelegate()
	{
		return isListening();
	}
}
