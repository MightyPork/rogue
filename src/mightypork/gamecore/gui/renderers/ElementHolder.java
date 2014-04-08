package mightypork.gamecore.gui.renderers;


import java.util.LinkedList;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.ChildClient;
import mightypork.gamecore.control.bus.EventBus;
import mightypork.gamecore.render.Renderable;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Rect;


/**
 * Bag for {@link PluggableRenderer} elements with constraints.<br>
 * Elements are exposed to {@link EventBus}.
 * 
 * @author MightyPork
 */
public abstract class ElementHolder extends ChildClient implements PluggableRenderable {
	
	private final LinkedList<PluggableRenderable> elements = new LinkedList<PluggableRenderable>();
	private RectConstraint context;
	
	
	public ElementHolder(AppAccess app) {
		super(app);
	}
	
	
	public ElementHolder(AppAccess app, RectConstraint context) {
		super(app);
		setContext(context);
	}
	
	
	@Override
	public final void setContext(RectConstraint context)
	{
		this.context = context;
	}
	
	
	@Override
	public final void render()
	{
		for (final Renderable element : elements) {
			element.render();
		}
	}
	
	
	@Override
	public final Rect getRect()
	{
		return context.getRect();
	}
	
	
	/**
	 * Add element to the holder, setting it's context.<br>
	 * Element must then be attached using the <code>attach</code> method.
	 * 
	 * @param elem element
	 */
	public abstract void add(PluggableRenderable elem);
	
	
	/**
	 * Connect to bus and add to element list
	 * 
	 * @param elem element; it's context will be set to the constraint.
	 */
	public final void attach(PluggableRenderable elem)
	{
		if (elem == null) return;
		
		elements.add(elem);
		addChildClient(elem);
	}
	
	
	@Override
	protected void deinit()
	{
		// no impl
	}
	
}
