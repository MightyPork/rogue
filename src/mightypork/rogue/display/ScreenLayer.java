package mightypork.rogue.display;


import mightypork.rogue.bus.ChildClient;
import mightypork.rogue.display.constraints.RenderContext;
import mightypork.rogue.display.constraints.Renderable;
import mightypork.utils.control.timing.Updateable;
import mightypork.utils.math.coord.Rect;


/**
 * Screen display layer
 * 
 * @author MightyPork
 */
public abstract class ScreenLayer extends ChildClient implements Renderable, Updateable, RenderContext {
	
	private Screen screen;
	
	
	public ScreenLayer(Screen screen) {
		super(screen); // screen as AppAccess
		
		this.screen = screen;
	}
	
	
	@Override
	public abstract void render();
	
	
	@Override
	public abstract void update(double delta);
	
	
	protected Screen screen()
	{
		return screen;
	}
	
	
	/**
	 * UNSUPPORTED
	 */
	@Override
	public final void setContext(RenderContext context)
	{
		throw new UnsupportedOperationException("ScreenLayer uses screen as it's context.");
	}
	
	
	@Override
	public Rect getRect()
	{
		return screen.getRect();
	}
	
}
