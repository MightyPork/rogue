package mightypork.gamecore.gui.components;


import mightypork.gamecore.control.events.LayoutChangeEvent;
import mightypork.gamecore.render.Renderable;
import mightypork.util.annotations.DefaultImpl;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.caching.AbstractRectCache;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.constraints.rect.proxy.RectBoundAdapter;


/**
 * {@link Renderable} with pluggable context. When caching is enabled, the
 * layout update can be triggered by firing the {@link LayoutChangeEvent}.
 * 
 * @author MightyPork
 */
public abstract class VisualComponent extends AbstractRectCache implements Component, LayoutChangeEvent.Listener {
	
	private Rect source;
	private boolean visible = true;
	
	
	public VisualComponent() {
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
		poll();
	}
	
	
	@Override
	public final void onConstraintChanged()
	{
		updateLayout();
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
