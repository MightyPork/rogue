package mightypork.gamecore.gui.components;


import mightypork.gamecore.control.bus.events.LayoutChangeEvent;
import mightypork.utils.annotations.DefaultImpl;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.caching.AbstractRectCache;
import mightypork.utils.math.constraints.rect.proxy.RectBound;
import mightypork.utils.math.constraints.rect.proxy.RectBoundAdapter;


/**
 * {@link Renderable} with pluggable context
 * 
 * @author MightyPork
 */
public abstract class AbstractVisualComponent extends AbstractRectCache implements Component, LayoutChangeEvent.Listener {
	
	private Rect source;
	private boolean visible = true;
	
	
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
	};
	
	
	@Override
	public final void onLayoutChanged()
	{
		poll();
	}
	
	
	@Override
	public final void onChange()
	{
		updateLayout();
	}
	
	
	/**
	 * Draw the component (it's visible)
	 */
	public abstract void renderComponent();
	
	
	@Override
	@DefaultImpl
	public void updateLayout()
	{
	}
}
