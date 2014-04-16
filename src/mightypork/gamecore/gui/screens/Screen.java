package mightypork.gamecore.gui.screens;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppSubModule;
import mightypork.gamecore.control.events.LayoutChangeEvent;
import mightypork.gamecore.control.events.ScreenChangeEvent;
import mightypork.gamecore.gui.components.Renderable;
import mightypork.gamecore.input.KeyBinder;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.render.Render;
import mightypork.util.annotations.DefaultImpl;
import mightypork.util.math.constraints.rect.Rect;
import mightypork.util.math.constraints.rect.proxy.RectBound;
import mightypork.util.math.constraints.vect.Vect;


/**
 * Screen class.
 * 
 * @author MightyPork
 */
public abstract class Screen extends AppSubModule implements Renderable, KeyBinder, RectBound, ScreenChangeEvent.Listener {
	
	private final KeyBindingPool keybindings = new KeyBindingPool();
	
	private volatile boolean active;
	private volatile boolean needSetupViewport = false;
	
	
	/**
	 * @param app app access
	 */
	public Screen(AppAccess app) {
		super(app);
		
		// disable events initially
		setListening(false);
		
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
	 * Prepare for being shown
	 * 
	 * @param shown true to show, false to hide
	 */
	public final void setActive(boolean shown)
	{
		if (shown) {
			active = true;
			needSetupViewport = true;
			
			onSizeChanged(getRect().size());
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
	public final boolean isActive()
	{
		return active;
	}
	
	
	@Override
	public final void receive(ScreenChangeEvent event)
	{
		if (!isActive()) return;
		
		onSizeChanged(event.getScreenSize());
		
		// fire event
		getEventBus().sendDirectToChildren(this, new LayoutChangeEvent());
		
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
	 * Update GUI for new screen size
	 * 
	 * @param size screen size
	 */
	@DefaultImpl
	protected void onSizeChanged(Vect size)
	{
	}
	
	
	/**
	 * Render screen contents (context is ready for 2D rendering)
	 */
	protected abstract void renderScreen();
	
	
	/**
	 * @return screen identifier to be used for requests.
	 */
	public abstract String getName();
	
}
