package mightypork.gamecore.gui.screens;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppSubModule;
import mightypork.gamecore.control.bus.events.ScreenChangeEvent;
import mightypork.gamecore.control.interf.NoImpl;
import mightypork.gamecore.gui.renderers.Renderable;
import mightypork.gamecore.input.KeyBinder;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.render.Render;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;


/**
 * Screen class.
 * 
 * @author MightyPork
 */
public abstract class Screen extends AppSubModule implements Renderable, KeyBinder, RectConstraint, ScreenChangeEvent.Listener {
	
	private final KeyBindingPool keybindings = new KeyBindingPool();
	
	private boolean active;
	private boolean needSetupViewport = false;
	
	
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
			
			onSizeChanged(getRect().getSize());
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
	 * Called when the screen becomes active
	 */
	@NoImpl
	protected void onScreenEnter()
	{
		//
	}
	
	
	/**
	 * Called when the screen is no longer active
	 */
	@NoImpl
	protected void onScreenLeave()
	{
		//
	}
	
	
	/**
	 * Update GUI for new screen size
	 * 
	 * @param size screen size
	 */
	@NoImpl
	protected void onSizeChanged(Coord size)
	{
		//
	}
	
	
	/**
	 * Render screen contents (context is ready for 2D rendering)
	 */
	protected abstract void renderScreen();
	
	
	/**
	 * @return true if screen is the curretn screen
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
			Render.setupOrtho();
		}
		
		Render.pushState();
		renderScreen();
		Render.popState();
	}
	
	
	/**
	 * @return screen identifier to be used for requests.
	 */
	public abstract String getId();
}
