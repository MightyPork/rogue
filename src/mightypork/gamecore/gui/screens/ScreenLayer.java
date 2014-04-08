package mightypork.gamecore.gui.screens;


import mightypork.gamecore.control.AppSubModule;
import mightypork.gamecore.gui.renderers.Renderable;
import mightypork.gamecore.input.KeyBinder;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Rect;


/**
 * Screen display layer
 * 
 * @author MightyPork
 */
public abstract class ScreenLayer extends AppSubModule implements Comparable<ScreenLayer>, Renderable, RectConstraint, KeyBinder {
	
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
	
	
	protected final Screen getScreen()
	{
		return screen;
	}
	
	
	@Override
	public final Rect getRect()
	{
		return screen.getRect();
	}
	
	/**
	 * @return higher = on top.
	 */
	public abstract int getPriority();
	
	@Override
	public final int compareTo(ScreenLayer o)
	{
		return Integer.compare(getPriority(), o.getPriority());
	}
	
}
