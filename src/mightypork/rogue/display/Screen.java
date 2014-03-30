package mightypork.rogue.display;


import static org.lwjgl.opengl.GL11.*;
import mightypork.rogue.App;
import mightypork.rogue.display.events.ScreenChangeEvent;
import mightypork.rogue.input.KeyBinder;
import mightypork.rogue.input.KeyBindingPool;
import mightypork.rogue.input.KeyStroke;
import mightypork.rogue.input.events.KeyboardEvent;
import mightypork.rogue.input.events.MouseButtonEvent;
import mightypork.rogue.input.events.MouseMotionEvent;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.patterns.Initializable;
import mightypork.utils.time.Updateable;


/**
 * Screen class.<br>
 * Screen animates 3D world, while contained panels render 2D overlays, process
 * inputs and run the game logic.
 * 
 * @author MightyPork
 */
public abstract class Screen implements KeyBinder, Updateable, Initializable, KeyboardEvent.Listener, MouseMotionEvent.Listener, MouseButtonEvent.Listener, ScreenChangeEvent.Listener {

	private KeyBindingPool keybindings = new KeyBindingPool();

	private boolean active;


	public Screen() {
		initialize();
	}


	@Override
	public void bindKeyStroke(KeyStroke stroke, Runnable task)
	{
		keybindings.bindKeyStroke(stroke, task);
	}


	@Override
	public void unbindKeyStroke(KeyStroke stroke)
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
			onSizeChanged(App.disp().getSize());
			onEnter();
			App.msgbus().addSubscriber(this);
		} else {
			active = false;
			onLeave();
			App.msgbus().removeSubscriber(this);
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
		Coord s = App.disp().getSize();
		glViewport(0, 0, s.xi(), s.yi());
		glOrtho(0, s.x, 0, s.y, -1000, 1000);

		// back to modelview
		glMatrixMode(GL_MODELVIEW);
	}


	/**
	 * Initialize screen layout and key bindings.<br>
	 * Called when the screen is created, not when it comes to front. For that,
	 * use onEnter().
	 */
	@Override
	public abstract void initialize();


	/**
	 * Called when the screen becomes active
	 */
	protected abstract void onEnter();


	/**
	 * Called when the screen is no longer active
	 */
	protected abstract void onLeave();


	/**
	 * Update GUI for new screen size
	 * 
	 * @param size screen size
	 */
	protected abstract void onSizeChanged(Coord size);


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
	private final void renderBegin()
	{
		glPushAttrib(GL_ENABLE_BIT);
		glPushMatrix();
	}


	/**
	 * Render screen
	 */
	private final void renderEnd()
	{
		glPopAttrib();
		glPopMatrix();
	}


	/**
	 * Update and render the screen
	 */
	@Override
	public final void update(double delta)
	{
		if (!isActive()) return;

		updateScreen(delta);
		renderBegin();
		renderScreen();
		renderEnd();
	};


	/**
	 * @return true if screen is the curretn screen
	 */
	protected final boolean isActive()
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


	@Override
	public final void receive(KeyboardEvent event)
	{
		if (!isActive()) return;
		keybindings.receive(event);
	}

}
