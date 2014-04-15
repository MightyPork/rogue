package mightypork.gamecore.gui.components.layout;


import java.util.LinkedList;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.bus.EventBus;
import mightypork.gamecore.gui.components.BusEnabledPainter;
import mightypork.gamecore.gui.components.PluggableRenderable;
import mightypork.gamecore.gui.components.Renderable;
import mightypork.gamecore.gui.components.SimplePainter;
import mightypork.utils.math.constraints.rect.RectBound;


/**
 * Bag for {@link SimplePainter} elements with constraints.<br>
 * Elements are exposed to {@link EventBus}.
 * 
 * @author MightyPork
 */
public abstract class AbstractLayout extends BusEnabledPainter {
	
	final LinkedList<PluggableRenderable> elements = new LinkedList<>();
	
	
	/**
	 * @param app app access
	 */
	public AbstractLayout(AppAccess app) {
		super(app);
	}
	
	
	/**
	 * @param app app access
	 * @param context boudning context
	 */
	public AbstractLayout(AppAccess app, RectBound context) {
		super(app);
		setRect(context);
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
	
	
	@Override
	public void paint()
	{
		for (final Renderable element : elements) {
			element.render();
		}
	}
	
}
