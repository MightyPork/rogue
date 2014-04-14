package mightypork.gamecore.gui.components;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppSubModule;
import mightypork.utils.math.constraints.RectBound;
import mightypork.utils.math.constraints.rect.Rect;


public abstract class AbstractComponent extends AppSubModule implements PluggableRenderable {
	
	private RectBound context;
	
	
	public AbstractComponent(AppAccess app) {
		super(app);
	}
	
	
	@Override
	public void setRect(RectBound context)
	{
		this.context = context;
	}
	
	
	@Override
	public Rect getRect()
	{
		return context.getRect();
	}
	
}
