package mightypork.gamecore.gui.screens;


import mightypork.gamecore.control.AppSubModule;
import mightypork.gamecore.gui.Hideable;
import mightypork.gamecore.gui.components.Renderable;
import mightypork.gamecore.input.KeyBinder;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.utils.annotations.DefaultImpl;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.RectBound;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Screen display layer
 * 
 * @author MightyPork
 */
public abstract class ScreenLayer extends AppSubModule implements Comparable<ScreenLayer>, Renderable, RectBound, KeyBinder, Hideable {
	
	private final Screen screen;
	
	private boolean visible = true;
	
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
	public boolean isVisible()
	{
		return visible;
	}
	
	
	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
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
	protected void onSizeChanged(Vect size)
	{
		//
	}
	
	
	/**
	 * @return higher = on top.
	 */
	public abstract int getPriority();
	
	
	protected final Rect bounds()
	{
		return screen.bounds();
	}
	
	
	protected final Vect mouse()
	{
		return screen.mouse();
	}
	
}
