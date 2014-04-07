package mightypork.rogue.gui.constraints;


import java.util.LinkedList;

import mightypork.rogue.AppAccess;
import mightypork.rogue.bus.ChildClient;
import mightypork.rogue.render.Renderable;
import mightypork.utils.control.bus.EventBus;
import mightypork.utils.math.constraints.ConstraintContext;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Rect;


/**
 * Bag for {@link PluggableRenderable} elements with constraints.<br>
 * Elements are exposed to {@link EventBus}.
 * 
 * @author MightyPork
 */
public class ElementHolder extends ChildClient implements ConstraintContext, PluggableRenderable {
	
	private final LinkedList<PluggableRenderable> elements = new LinkedList<PluggableRenderable>();
	private ConstraintContext context;
	
	
	public ElementHolder(AppAccess app) {
		super(app);
	}
	
	
	public ElementHolder(AppAccess app, ConstraintContext context) {
		super(app);
		this.context = context;
	}
	
	
	@Override
	public void setContext(ConstraintContext context)
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
	public Rect getRect()
	{
		return context.getRect();
	}
	
	
	/**
	 * Add element to the holder.
	 * 
	 * @param elem element; it's context will be set to the constraint.
	 * @param constraint Constraint to be used for the element. It's context
	 *            will be set to this {@link ElementHolder}
	 */
	public void add(PluggableRenderable elem, RectConstraint constraint)
	{
		if (elem == null) return;
		
		constraint.setContext(this);
		elem.setContext(constraint);
		
		elements.add(elem);
		addChildClient(elem);
	}
	
	
	/**
	 * Remove element from the holder
	 * 
	 * @param elem
	 */
	public void remove(PluggableRenderable elem)
	{
		if (elem == null) return;
		elements.remove(elem);
		removeChildClient(elem);
	}
	
}
