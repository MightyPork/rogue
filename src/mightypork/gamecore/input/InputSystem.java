package mightypork.gamecore.input;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.eventbus.clients.RootBusNode;
import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.input.events.KeyEvent;
import mightypork.gamecore.input.events.MouseButtonEvent;
import mightypork.gamecore.input.events.MouseMotionEvent;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.gamecore.util.math.constraints.vect.mutable.VectVar;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


/**
 * Input system
 * 
 * @author MightyPork
 */
public class InputSystem extends RootBusNode implements Updateable, KeyBinder {
	
	// listeners
	private final KeyBindingPool keybindings;
	
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
	
	private static boolean inited = false;
	
	
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
	public final void bindKey(KeyStroke stroke, Runnable task)
	{
		keybindings.bindKey(stroke, task);
	}
	
	
	@Override
	public void unbindKey(KeyStroke stroke)
	{
		keybindings.unbindKey(stroke);
	}
	
	// counters as fields to save memory.
	private final VectVar mouseMove = Vect.makeVar();
	private final VectVar mouseLastPos = Vect.makeVar();
	
	
	@Override
	public synchronized void update(double delta)
	{
		// was destroyed
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
			getEventBus().send(new ActionRequest(RequestType.SHUTDOWN));
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
		move.mul(1, -1, 1);
		
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
	
	
	public static boolean isKeyDown(int key)
	{
		return Keyboard.isKeyDown(key);
	}
	
	
	public static boolean isMouseButtonDown(int button)
	{
		return Mouse.isButtonDown(button);
	}
}
