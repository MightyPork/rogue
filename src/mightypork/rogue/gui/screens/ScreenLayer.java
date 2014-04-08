package mightypork.rogue.gui.screens;


import mightypork.rogue.bus.ChildClient;
import mightypork.rogue.input.KeyBinder;
import mightypork.rogue.input.KeyBindingPool;
import mightypork.rogue.input.KeyStroke;
import mightypork.rogue.render.Renderable;
import mightypork.utils.math.constraints.RectEvaluable;
import mightypork.utils.math.coord.Rect;


/**
 * Screen display layer
 * 
 * @author MightyPork
 */
public abstract class ScreenLayer extends ChildClient implements Renderable, RectEvaluable, KeyBinder {
	
	private final Screen screen;
	
	private final KeyBindingPool keybindings = new KeyBindingPool();
	
	
	public ScreenLayer(Screen screen) {
		super(screen); // screen as AppAccess
		
		this.screen = screen;
		addChildClient(keybindings);
	}
	
	
	@Override
	public final void bindKeyStroke(KeyStroke stroke, Runnable task)
	{
		keybindings.bindKeyStroke(stroke, task);
	}
	
	
	@Override
	public final void unbindKeyStroke(KeyStroke stroke)
	{
		keybindings.unbindKeyStroke(stroke);
	}
	
	
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
