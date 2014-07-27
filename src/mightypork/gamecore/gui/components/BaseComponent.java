package mightypork.gamecore.gui.components;


import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.Renderable;
import mightypork.gamecore.gui.events.LayoutChangeEvent;
import mightypork.gamecore.gui.events.LayoutChangeListener;
import mightypork.utils.Support;
import mightypork.utils.annotations.Stub;
import mightypork.utils.interfaces.Enableable;
import mightypork.utils.logging.Log;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.RectBound;
import mightypork.utils.math.constraints.rect.caching.AbstractRectCache;
import mightypork.utils.math.constraints.rect.proxy.RectProxy;


/**
 * {@link Renderable} with pluggable context. When caching is enabled, the
 * layout update can be triggered by firing the {@link LayoutChangeEvent}.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class BaseComponent extends AbstractRectCache implements Component, LayoutChangeListener, Enableable {
	
	private Rect source;
	private boolean visible = true;
	private boolean enabled = true;
	private int indirectDisableLevel = 0;
	
	private Num alphaMul = Num.ONE;
	
	
	public BaseComponent() {
		enableCaching(false);
	}
	
	
	@Override
	public void setRect(RectBound rect)
	{
		this.source = new RectProxy(rect);
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
		return source.round(); // round to avoid visual artifacts in fonts and such
	}
	
	
	@Override
	public final void render()
	{
		if (!isVisible()) return;
		
		Color.pushAlpha(alphaMul);
		renderComponent();
		Color.popAlpha();
	}
	
	
	@Override
	public final void onLayoutChanged()
	{
		try {
			poll();
		} catch (final NullPointerException e) {
			Log.e("Component is missing a bounding rect, at: " + Support.str(getClass()));
		}
	}
	
	
	@Override
	public final void onConstraintChanged()
	{
		updateLayout();
	}
	
	
	@Override
	public final boolean isMouseOver()
	{
		return App.input().getMousePos().isInside(this);
	}
	
	
	/**
	 * Draw the component (it's visible)
	 */
	protected abstract void renderComponent();
	
	
	@Override
	@Stub
	public void updateLayout()
	{
	}
	
	
	@Override
	public void setEnabled(boolean yes)
	{
		enabled = yes;
	}
	
	
	@Override
	public boolean isEnabled()
	{
		return enabled && isIndirectlyEnabled();
	}
	
	
	@Override
	public final void setAlpha(Num alpha)
	{
		this.alphaMul = alpha;
	}
	
	
	@Override
	public final void setAlpha(double alpha)
	{
		this.alphaMul = Num.make(alpha);
	}
	
	
	@Override
	public void setIndirectlyEnabled(boolean yes)
	{
		if (!yes) {
			indirectDisableLevel++;
		} else {
			if (indirectDisableLevel > 0) indirectDisableLevel--;
		}
	}
	
	
	@Override
	public boolean isIndirectlyEnabled()
	{
		return indirectDisableLevel == 0;
	}
	
	
	@Override
	public boolean isDirectlyEnabled()
	{
		return enabled;
	}
}
