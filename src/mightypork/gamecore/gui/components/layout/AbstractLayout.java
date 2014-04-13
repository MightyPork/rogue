package mightypork.gamecore.gui.components.layout;


import java.util.LinkedList;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.bus.EventBus;
import mightypork.gamecore.gui.components.AbstractComponent;
import mightypork.gamecore.gui.components.PluggableRenderable;
import mightypork.gamecore.gui.components.Renderable;
import mightypork.gamecore.gui.components.painters.AbstractPainter;
import mightypork.utils.math.constraints.RectBound;


/**
 * Bag for {@link AbstractPainter} elements with constraints.<br>
 * Elements are exposed to {@link EventBus}.
 * 
 * @author MightyPork
 */
public abstract class AbstractLayout extends AbstractComponent {
	
	final LinkedList<PluggableRenderable>	elements	= new LinkedList<>();
	
	
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
		setContext(context);
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
	public void render()
	{
		for (final Renderable element : elements) {
			element.render();
		}
	}
	
}
