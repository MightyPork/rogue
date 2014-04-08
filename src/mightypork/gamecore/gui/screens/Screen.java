package mightypork.gamecore.gui.screens;


import static org.lwjgl.opengl.GL11.*;
import mightypork.gamecore.AppAccess;
import mightypork.gamecore.control.Subsystem;
import mightypork.gamecore.control.bus.events.ScreenChangeEvent;
import mightypork.gamecore.control.interf.Destroyable;
import mightypork.gamecore.input.KeyBinder;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.render.Renderable;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;


/**
 * Screen class.
 * 
 * @author MightyPork
 */
public abstract class Screen extends Subsystem implements Renderable, Destroyable, KeyBinder, RectConstraint, ScreenChangeEvent.Listener {
	
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
	
	
	@Override
	public final void deinit()
	{
		deinitScreen();
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
	 * Clean up before screen is destroyed.
	 */
	protected abstract void deinitScreen();
	
	
	/**
	 * Called when the screen becomes active
	 */
	protected abstract void onScreenEnter();
	
	
	/**
	 * Called when the screen is no longer active
	 */
	protected abstract void onScreenLeave();
	
	
	/**
	 * Update GUI for new screen size
	 * 
	 * @param size screen size
	 */
	protected void onSizeChanged(Coord size)
	{
		// no impl
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
		return disp().getRect();
	}
	
	
	@Override
	public final void render()
	{
		if (!isActive()) return;
		
		if (needSetupViewport) {
			setupViewport();
		}
		
		renderScreen();
	}
	
	
	protected void setupViewport()
	{
		// fix projection for changed size
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		final Coord s = disp().getSize();
		glViewport(0, 0, s.xi(), s.yi());
		glOrtho(0, s.x, s.y, 0, -1000, 1000);
		
		// back to modelview
		glMatrixMode(GL_MODELVIEW);
	}
	
	
	public abstract String getId();
}
