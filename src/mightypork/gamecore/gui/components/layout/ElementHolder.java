package mightypork.gamecore.gui.components.layout;


import java.util.LinkedList;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.bus.EventBus;
import mightypork.gamecore.control.bus.clients.BusNode;
import mightypork.gamecore.gui.components.PluggableRenderable;
import mightypork.gamecore.gui.components.PluggableRenderer;
import mightypork.gamecore.gui.components.Renderable;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.rect.RectValue;


/**
 * Bag for {@link PluggableRenderer} elements with constraints.<br>
 * Elements are exposed to {@link EventBus}.
 * 
 * @author MightyPork
 */
public abstract class ElementHolder extends BusNode implements PluggableRenderable {
	
	private final LinkedList<PluggableRenderable> elements = new LinkedList<>();
	private RectConstraint context;
	
	
	/**
	 * @param app app access
	 */
	public ElementHolder(AppAccess app) {
		super(app);
	}
	
	
	/**
	 * @param app app access
	 * @param context boudning context
	 */
	public ElementHolder(AppAccess app, RectConstraint context) {
		super(app);
		setContext(context);
	}
	
	
	@Override
	public void setContext(RectConstraint context)
	{
		this.context = context;
	}
	
	
	@Override
	public void render()
	{
		for (final Renderable element : elements) {
			element.render();
		}
	}
	
	
	@Override
	public RectValue getRect()
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
	public void attach(PluggableRenderable elem)
	{
		if (elem == null) return;
		
		elements.add(elem);
		addChildClient(elem);
	}
	
}
