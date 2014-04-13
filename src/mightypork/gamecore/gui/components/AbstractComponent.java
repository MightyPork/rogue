package mightypork.gamecore.gui.components;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppSubModule;
import mightypork.gamecore.control.bus.BusAccess;
import mightypork.gamecore.control.bus.clients.BusNode;
import mightypork.utils.math.constraints.RectBound;
import mightypork.utils.math.rect.RectView;


public abstract class AbstractComponent extends AppSubModule implements PluggableRenderable {
	
	private RectBound	context;

	public AbstractComponent(AppAccess app) {
		super(app);
	}

	@Override
	public void setContext(RectBound context)
	{
		this.context = context;
	}

	@Override
	public RectView getRect()
	{
		return context.getRect();
	}
	
}
