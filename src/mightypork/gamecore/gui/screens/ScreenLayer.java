package mightypork.gamecore.gui.screens;


import mightypork.gamecore.control.AppSubModule;
import mightypork.gamecore.control.interf.DefaultImpl;
import mightypork.gamecore.gui.components.Renderable;
import mightypork.gamecore.gui.constraints.RectConstraint;
import mightypork.gamecore.input.KeyBinder;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;


/**
 * Screen display layer
 * 
 * @author MightyPork
 */
public abstract class ScreenLayer extends AppSubModule implements Comparable<ScreenLayer>, Renderable, RectConstraint, KeyBinder {
	
	private final Screen screen;
	
	private final KeyBindingPool keybindings = new KeyBindingPool();
	
	
	/**
	 * @param screen parent screen
	 */
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
	
	
	/**
	 * @return parent screen instance
	 */
	protected final Screen getScreen()
	{
		return screen;
	}
	
	
	@Override
	public Rect getRect()
	{
		return screen.getRect();
	}
	
	
	@Override
	public final int compareTo(ScreenLayer o)
	{
		return getPriority() - o.getPriority();
	}
	
	
	/**
	 * Called when the screen becomes active
	 */
	@DefaultImpl
	protected void onScreenEnter()
	{
		//
	}
	
	
	/**
	 * Called when the screen is no longer active
	 */
	@DefaultImpl
	protected void onScreenLeave()
	{
		//
	}
	
	
	/**
	 * Update GUI for new screen size
	 * 
	 * @param size screen size
	 */
	@DefaultImpl
	protected void onSizeChanged(Coord size)
	{
		//
	}
	
	
	/**
	 * @return higher = on top.
	 */
	public abstract int getPriority();
	
}
