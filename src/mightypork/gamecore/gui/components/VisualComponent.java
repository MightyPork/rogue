package mightypork.gamecore.gui.components;


import mightypork.gamecore.gui.events.LayoutChangeEvent;
import mightypork.gamecore.gui.events.LayoutChangeListener;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.Renderable;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.rect.caching.AbstractRectCache;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBoundAdapter;


/**
 * {@link Renderable} with pluggable context. When caching is enabled, the
 * layout update can be triggered by firing the {@link LayoutChangeEvent}.
 * 
 * @author MightyPork
 */
public abstract class VisualComponent extends AbstractRectCache implements Component, LayoutChangeListener {
	
	private Rect source;
	private boolean visible = true;
	
	
	public VisualComponent()
	{
		super();
		enableCaching(false);
	}
	
	
	@Override
	public final Rect getRect()
	{
		return super.getRect();
	}
	
	
	@Override
	public final void setRect(RectBound rect)
	{
		this.source = new RectBoundAdapter(rect);
	}
	
	
	@Override
	public final boolean isVisible()
	{
		return visible;
	}
	
	
	@Override
	public final void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	
	@Override
	public final Rect getCacheSource()
	{
		return source;
	}
	
	
	@Override
	public final void render()
	{
		if (!visible) return;
		
		renderComponent();
	}
	
	
	@Override
	public final void onLayoutChanged()
	{
		if (source == null) throw new NullPointerException("Component is missing a bounding rect.");
		poll();
	}
	
	
	@Override
	public final void onConstraintChanged()
	{
		updateLayout();
	}
	
	
	protected boolean isMouseOver()
	{
		return InputSystem.getMousePos().isInside(this);
	}
	
	
	/**
	 * Draw the component (it's visible)
	 */
	protected abstract void renderComponent();
	
	
	@Override
	@DefaultImpl
	public void updateLayout()
	{
	}
}
