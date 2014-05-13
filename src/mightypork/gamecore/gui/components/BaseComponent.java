package mightypork.gamecore.gui.components;


import mightypork.gamecore.gui.Enableable;
import mightypork.gamecore.gui.events.LayoutChangeEvent;
import mightypork.gamecore.gui.events.LayoutChangeListener;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.render.Renderable;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.constraints.num.Num;
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
public abstract class BaseComponent extends AbstractRectCache implements Component, LayoutChangeListener, Enableable {
	
	private Rect source;
	private boolean visible = true;
	
	private int disableLevel = 0;
	private Num alphaMul = Num.ONE;
	
	
	public BaseComponent()
	{
		enableCaching(false);
	}
	
	
	@Override
	public void setRect(RectBound rect)
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
			Log.e("Component is missing a bounding rect, at: " + Log.str(getClass()));
		}
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
	
	
	@Override
	public void setEnabled(boolean yes)
	{
		if (yes) {
			if (disableLevel > 0) disableLevel--;
		} else {
			disableLevel++;
		}
	}
	
	
	@Override
	public boolean isEnabled()
	{
		return disableLevel == 0;
	}
	
	
	public void setAlpha(Num alpha)
	{
		this.alphaMul = alpha;
	}
	
	
	public void setAlpha(double alpha)
	{
		this.alphaMul = Num.make(alpha);
	}
}
