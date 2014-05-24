package mightypork.gamecore.gui.components;


import mightypork.dynmath.num.Num;
import mightypork.dynmath.rect.Rect;
import mightypork.dynmath.rect.caching.AbstractRectCache;
import mightypork.dynmath.rect.proxy.RectBound;
import mightypork.dynmath.rect.proxy.RectBoundAdapter;
import mightypork.gamecore.gui.Enableable;
import mightypork.gamecore.gui.events.LayoutChangeEvent;
import mightypork.gamecore.gui.events.LayoutChangeListener;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.render.Renderable;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.color.Color;


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
	
	
	@Override
	public final boolean isMouseOver()
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
