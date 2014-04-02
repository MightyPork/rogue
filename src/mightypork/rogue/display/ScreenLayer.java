package mightypork.rogue.display;


import mightypork.rogue.bus.ChildClient;
import mightypork.rogue.display.constraints.Renderable;
import mightypork.utils.control.interf.Updateable;
import mightypork.utils.math.constraints.ConstraintContext;
import mightypork.utils.math.coord.Rect;


/**
 * Screen display layer
 * 
 * @author MightyPork
 */
public abstract class ScreenLayer extends ChildClient implements Renderable, Updateable, ConstraintContext {
	
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
	
	
	@Override
	public Rect getRect()
	{
		return screen.getRect();
	}
	
}
