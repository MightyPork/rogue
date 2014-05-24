package mightypork.gamecore.input;


import mightypork.dynmath.vect.Vect;
import mightypork.dynmath.vect.mutable.VectVar;
import mightypork.gamecore.core.events.UserQuitRequest;
import mightypork.gamecore.core.modules.AppAccess;
import mightypork.gamecore.eventbus.clients.RootBusNode;
import mightypork.gamecore.input.KeyStroke.Edge;
import mightypork.gamecore.input.events.InputReadyEvent;
import mightypork.gamecore.input.events.KeyEvent;
import mightypork.gamecore.input.events.MouseButtonEvent;
import mightypork.gamecore.input.events.MouseMotionEvent;
import mightypork.gamecore.util.math.timing.Updateable;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


/**
 * Input system
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class InputSystem extends RootBusNode implements Updateable, KeyBinder {
	
	private static boolean inited = false;
	
	/** Current mouse position */
	private static final Vect mousePos = new Vect() {
		
		@Override
		public double x()
		{
			if (!Mouse.isInsideWindow()) return Integer.MIN_VALUE;
			
			return Mouse.getX();
		}
		
		
		@Override
		public double y()
		{
			if (!Mouse.isInsideWindow()) return Integer.MIN_VALUE;
			// flip Y axis
			return Display.getHeight() - Mouse.getY();
		}
	};
	
	private final KeyBindingPool keybindings;
	
	private final VectVar mouseMove = Vect.makeVar();
	private final VectVar mouseLastPos = Vect.makeVar();
	
	
	/**
	 * @param app app access
	 */
	public InputSystem(AppAccess app)
	{
		super(app);
		
		initDevices();
		
		// global keybindings
		keybindings = new KeyBindingPool();
		addChildClient(keybindings);
		
		getEventBus().send(new InputReadyEvent());
	}
	
	
	@Override
	public final void deinit()
	{
		Mouse.destroy();
		Keyboard.destroy();
	}
	
	
	private void initDevices()
	{
		if (inited) return;
		inited = true;
		
		try {
			Mouse.create();
			Keyboard.create();
			Keyboard.enableRepeatEvents(false);
		} catch (final LWJGLException e) {
			throw new RuntimeException("Failed to initialize input devices.", e);
		}
	}
	
	
	@Override
	public final void bindKey(KeyStroke stroke, Edge edge, Runnable task)
	{
		keybindings.bindKey(stroke, edge, task);
	}
	
	
	@Override
	public void unbindKey(KeyStroke stroke)
	{
		keybindings.unbindKey(stroke);
	}
	
	
	@Override
	public synchronized void update(double delta)
	{
		// was destroyed or not initialized
		if (!Display.isCreated()) return;
		if (!Mouse.isCreated()) return;
		if (!Keyboard.isCreated()) return;
		
		Display.processMessages();
		
		// sum the moves
		mouseMove.reset();
		mouseLastPos.reset();
		boolean wasMouse = false;
		while (Mouse.next()) {
			onMouseEvent(mouseMove, mouseLastPos);
			wasMouse = true;
		}
		
		if (wasMouse && !mouseMove.isZero()) {
			getEventBus().send(new MouseMotionEvent(mouseLastPos, mouseMove));
		}
		
		while (Keyboard.next()) {
			onKeyEvent();
		}
		
		if (Display.isCloseRequested()) {
			getEventBus().send(new UserQuitRequest());
		}
	}
	
	
	private void onMouseEvent(VectVar moveSum, VectVar lastPos)
	{
		final int button = Mouse.getEventButton();
		final boolean down = Mouse.getEventButtonState();
		
		final VectVar pos = Vect.makeVar(Mouse.getEventX(), Mouse.getEventY());
		final VectVar move = Vect.makeVar(Mouse.getEventDX(), Mouse.getEventDY());
		
		final int wheeld = Mouse.getEventDWheel();
		
		// flip Y axis
		pos.setY(Display.getHeight() - pos.y());
		
		if (button != -1 || wheeld != 0) {
			getEventBus().send(new MouseButtonEvent(pos.freeze(), button, down, wheeld));
		}
		
		moveSum.setTo(moveSum.add(move));
		lastPos.setTo(pos);
	}
	
	
	private void onKeyEvent()
	{
		final int key = Keyboard.getEventKey();
		final boolean down = Keyboard.getEventKeyState();
		final char c = Keyboard.getEventCharacter();
		
		getEventBus().send(new KeyEvent(key, c, down));
	}
	
	
	/**
	 * Get absolute mouse position. This vect is final and views at it can
	 * safely be made.
	 * 
	 * @return mouse position
	 */
	public static Vect getMousePos()
	{
		return mousePos;
	}
	
	
	/**
	 * @return true if mouse is inside window.
	 */
	public static boolean isMouseInside()
	{
		return Mouse.isInsideWindow();
	}
	
	
	/**
	 * Trap mouse cursor in the window
	 * 
	 * @param grab true to grab
	 */
	public static void grabMouse(boolean grab)
	{
		Mouse.setGrabbed(grab);
	}
	
	
	/**
	 * Check if key is down
	 * 
	 * @param key key to check (constant from the {@link Keys} class)
	 * @return is down
	 */
	public static boolean isKeyDown(int key)
	{
		return Keyboard.isKeyDown(key);
	}
	
	
	/**
	 * Check mouse button state
	 * 
	 * @param button button to test (0 left, 1 right, 2 middle)
	 * @return button is down
	 */
	public static boolean isMouseButtonDown(int button)
	{
		return Mouse.isButtonDown(button);
	}
	
	
	/**
	 * @return bit mask of active mod keys
	 */
	public static int getActiveModKeys()
	{
		int mods = 0;
		
		if (Keyboard.isKeyDown(Keys.L_ALT) || Keyboard.isKeyDown(Keys.R_ALT)) {
			mods |= Keys.MOD_ALT;
		}
		
		if (Keyboard.isKeyDown(Keys.L_SHIFT) || Keyboard.isKeyDown(Keys.R_SHIFT)) {
			mods |= Keys.MOD_SHIFT;
		}
		
		if (Keyboard.isKeyDown(Keys.L_CONTROL) || Keyboard.isKeyDown(Keys.R_CONTROL)) {
			mods |= Keys.MOD_CONTROL;
		}
		
		if (Keyboard.isKeyDown(Keys.L_META) || Keyboard.isKeyDown(Keys.R_META)) {
			mods |= Keys.MOD_META;
		}
		
		return mods;
	}
	
	
	/**
	 * @return true if the system is initialized
	 */
	public static boolean isReady()
	{
		return inited;
	}
}
