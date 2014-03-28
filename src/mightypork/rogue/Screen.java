package mightypork.rogue;


import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import mightypork.rogue.animations.GUIRenderer;
import mightypork.rogue.input.InputHandler;
import mightypork.rogue.input.Keys;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Vec;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


/**
 * Screen class.<br>
 * Screen animates 3D world, while contained panels render 2D overlays, process
 * inputs and run the game logic.
 * 
 * @author MightyPork
 */
public abstract class Screen implements GUIRenderer, InputHandler {

	/** RNG */
	protected static Random rand = new Random();


	/**
	 * handle fullscreen change
	 */
	@Override
	public final void onFullscreenChange()
	{
		onWindowResize();
		onViewportChanged();
	}


	protected abstract void onViewportChanged();


	/**
	 * handle window resize.
	 */
	public final void onWindowResize()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		Coord s = App.inst.getSize();

		glViewport(0, 0, s.xi(), s.yi());

		glOrtho(0, s.x, 0, s.y, -1000, 1000);

		glMatrixMode(GL_MODELVIEW);

		glLoadIdentity();

		glEnable(GL_BLEND);
		//glDisable(GL_DEPTH_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_TEXTURE_2D);
	}


	/**
	 * Initialize screen
	 */
	public void init()
	{
		onWindowResize();

		initScreen();

		// SETUP LIGHTS
		glDisable(GL_LIGHTING);

		// OTHER SETTINGS
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glClearDepth(1f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);

		glEnable(GL_NORMALIZE);

		glShadeModel(GL_SMOOTH);
		glDisable(GL_TEXTURE_2D);
	}


	/**
	 * Here you can initialize the screen.
	 */
	public abstract void initScreen();


	/**
	 * Update tick
	 */
	@Override
	public final void updateGui()
	{
		Mouse.poll();
		Keyboard.poll();
		checkInputEvents();

		onGuiUpdate();
	}


	protected abstract void onGuiUpdate();


	/**
	 * Render screen
	 * 
	 * @param delta delta time (position between two update ticks, to allow
	 *            super-smooth animations)
	 */
	@Override
	public final void render(double delta)
	{
		glPushAttrib(GL_ENABLE_BIT);

		// draw the directly rendered 3D stuff
		render3D();

		glPopAttrib();
	}


	protected abstract void render3D();


	/**
	 * Check input events and process them.
	 */
	private final void checkInputEvents()
	{
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			boolean down = Keyboard.getEventKeyState();
			char c = Keyboard.getEventCharacter();
			Keys.onKey(key, down);
			onKey(key, c, down);
		}
		while (Mouse.next()) {
			int button = Mouse.getEventButton();
			boolean down = Mouse.getEventButtonState();
			Coord delta = new Coord(Mouse.getEventDX(), Mouse.getEventDY());
			Coord pos = new Coord(Mouse.getEventX(), Mouse.getEventY());
			int wheeld = Mouse.getEventDWheel();

			onMouseButton(button, down, wheeld, pos, delta);
		}

		int xc = Mouse.getX();
		int yc = Mouse.getY();
		int xd = Mouse.getDX();
		int yd = Mouse.getDY();
		int wd = Mouse.getDWheel();

		if (Math.abs(xd) > 0 || Math.abs(yd) > 0 || Math.abs(wd) > 0) {
			onMouseMove(new Coord(xc, yc), new Vec(xd, yd), wd);
		}

		handleKeyStates();
	}


	@Override
	public abstract void onKey(int key, char c, boolean down);


	@Override
	public abstract void onMouseButton(int button, boolean down, int wheeld, Coord pos, Coord delta);


	@Override
	public abstract void handleKeyStates();


	@Override
	public abstract void onMouseMove(Coord coord, Vec vec, int wd);


	/**
	 * Render background 2D (all is ready for rendering)
	 * 
	 * @param delta delta time
	 */
	protected abstract void render2D(double delta);

}
