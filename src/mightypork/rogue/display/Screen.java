package mightypork.rogue.display;


import static org.lwjgl.opengl.GL11.*;
import mightypork.rogue.AppAccess;
import mightypork.rogue.bus.DelegatingBusClient;
import mightypork.rogue.bus.events.ScreenChangeEvent;
import mightypork.rogue.display.constraints.Bounding;
import mightypork.rogue.input.KeyBinder;
import mightypork.rogue.input.KeyBindingPool;
import mightypork.rogue.input.KeyStroke;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;


/**
 * Screen class.<br>
 * Screen animates 3D world, while contained panels render 2D overlays, process
 * inputs and run the game logic.
 * 
 * @author MightyPork
 */
public abstract class Screen extends DelegatingBusClient implements KeyBinder, Bounding, ScreenChangeEvent.Listener {
	
	private KeyBindingPool keybindings;
	
	private boolean active;
	
	
	public Screen(AppAccess app) {
		super(app, true);
		
		// disable events initially
		enableEvents(false);
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
			setupGraphics();
			setupViewport();
			onSizeChanged(getRect().getSize());
			onScreenEnter();
			
			// subscribe to event bus
			enableEvents(true);
			
		} else {
			onScreenLeave();
			
			active = false;
			
			// unsusbcribe from event bus
			enableEvents(false);
		}
	}
	
	
	private void setupGraphics()
	{
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glDisable(GL_LIGHTING);
		
		glClearDepth(1f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		
		glEnable(GL_NORMALIZE);
		
		glShadeModel(GL_SMOOTH);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glDisable(GL_TEXTURE_2D);
		
		setupViewport();
	}
	
	
	private void setupViewport()
	{
		// fix projection for changed size
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		Coord s = disp().getSize();
		glViewport(0, 0, s.xi(), s.yi());
		glOrtho(0, s.x, 0, s.y, -1000, 1000);
		
		// back to modelview
		glMatrixMode(GL_MODELVIEW);
	}
	
	
	@Override
	protected final void init()
	{
		keybindings = new KeyBindingPool();
		
		addChildSubscriber(keybindings);
		
		initScreen();
	}
	
	
	@Override
	protected final void deinit()
	{
		deinitScreen();
	}
	
	
	/**
	 * Initialize screen layout and key bindings.<br>
	 * Called during screen construction.
	 */
	protected abstract void initScreen();
	
	
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
	 * Update animations and timing
	 * 
	 * @param delta time elapsed
	 */
	protected abstract void updateScreen(double delta);
	
	
	/**
	 * Render screen
	 */
	private void renderBegin()
	{
		glPushAttrib(GL_ENABLE_BIT);
		glPushMatrix();
	}
	
	
	/**
	 * Render screen
	 */
	private void renderEnd()
	{
		glPopAttrib();
		glPopMatrix();
	}
	
	
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
		
		setupViewport();
		
		onSizeChanged(event.getScreenSize());
	}
	
	
	/**
	 * Update and render the screen
	 */
	@Override
	public final void update(double delta)
	{
		updateScreen(delta);
		
		renderBegin();
		renderScreen();
		renderEnd();
	}
	
	
	@Override
	public final Rect getRect()
	{
		return disp().getRect();
	}
	
}
