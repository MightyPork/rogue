package mightypork.gamecore.gui.screens;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppSubModule;
import mightypork.gamecore.control.events.gui.LayoutChangeEvent;
import mightypork.gamecore.control.events.gui.LayoutChangeListener;
import mightypork.gamecore.input.KeyBinder;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.render.Render;
import mightypork.util.annotations.DefaultImpl;
import mightypork.util.constraints.rect.Rect;


/**
 * Screen class.
 * 
 * @author MightyPork
 */
public abstract class BaseScreen extends AppSubModule implements Screen, KeyBinder, LayoutChangeListener {
	
	private final KeyBindingPool keybindings = new KeyBindingPool();
	
	private volatile boolean active;
	private volatile boolean needSetupViewport = false;
	
	
	/**
	 * @param app app access
	 */
	public BaseScreen(AppAccess app)
	{
		super(app);
		
		// disable events initially
		setListening(false);
		
		addChildClient(keybindings);
	}
	
	
	private void fireLayoutChangeEvent()
	{
		getEventBus().sendDirectToChildren(this, new LayoutChangeEvent());
	}
	
	
	@Override
	public final void bindKey(KeyStroke stroke, Runnable task)
	{
		keybindings.bindKey(stroke, task);
	}
	
	
	@Override
	public final void unbindKey(KeyStroke stroke)
	{
		keybindings.unbindKey(stroke);
	}
	
	
	/**
	 * Prepare for being shown
	 * 
	 * @param shown true to show, false to hide
	 */
	@Override
	public final void setActive(boolean shown)
	{
		if (shown) {
			active = true;
			needSetupViewport = true;
			
			fireLayoutChangeEvent();
			onScreenEnter();
			
			// enable events
			setListening(true);
			
		} else {
			onScreenLeave();
			
			active = false;
			
			// disable events
			setListening(false);
		}
	}
	
	
	/**
	 * @return true if screen is the current screen
	 */
	@Override
	public final boolean isActive()
	{
		return active;
	}
	
	
	@Override
	public void onLayoutChanged()
	{
		if (!isActive()) return;
		
		needSetupViewport = true;
	}
	
	
	@Override
	public final Rect getRect()
	{
		return getDisplay().getRect();
	}
	
	
	@Override
	public void render()
	{
		if (!isActive()) return;
		
		if (needSetupViewport) {
			Render.setupOrtho(getDisplay().getSize());
		}
		
		Render.pushState();
		
		renderScreen();
		
		Render.popState();
	}
	
	
	/**
	 * Called when the screen becomes active
	 */
	@DefaultImpl
	protected void onScreenEnter()
	{
	}
	
	
	/**
	 * Called when the screen is no longer active
	 */
	@DefaultImpl
	protected void onScreenLeave()
	{
	}
	
	
	/**
	 * Render screen contents (context is ready for 2D rendering)
	 */
	protected abstract void renderScreen();
	
}
