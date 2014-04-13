package mightypork.gamecore.input;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.bus.clients.RootBusNode;
import mightypork.gamecore.control.bus.events.KeyEvent;
import mightypork.gamecore.control.bus.events.MouseButtonEvent;
import mightypork.gamecore.control.bus.events.MouseMotionEvent;
import mightypork.gamecore.control.timing.Updateable;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;
import mightypork.utils.math.vect.VectMutable;
import mightypork.utils.math.vect.VectView;

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
	private final VectView mousePos = new VectView() {
		
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
	public InputSystem(AppAccess app) {
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
	public final void bindKeyStroke(KeyStroke stroke, Runnable task)
	{
		keybindings.bindKeyStroke(stroke, task);
	}
	
	
	@Override
	public void unbindKeyStroke(KeyStroke stroke)
	{
		keybindings.unbindKeyStroke(stroke);
	}
	
	// counters as fields to save memory.
	private final VectMutable mouseMove = VectMutable.zero();
	private final VectMutable mouseLastPos = VectMutable.zero();
	
	
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
	
	
	private void onMouseEvent(VectMutable moveSum, VectMutable lastPos)
	{
		final int button = Mouse.getEventButton();
		final boolean down = Mouse.getEventButtonState();
		
		final VectMutable pos = VectMutable.make(Mouse.getEventX(), Mouse.getEventY());
		final VectMutable move = VectMutable.make(Mouse.getEventDX(), Mouse.getEventDY());
		
		final int wheeld = Mouse.getEventDWheel();
		
		// flip Y axis
		pos.setY(Display.getHeight() - pos.y());
		move.mul(1, -1, 1);
		
		if (button != -1 || wheeld != 0) {
			getEventBus().send(new MouseButtonEvent(pos.copy(), button, down, wheeld));
		}
		
		moveSum.add(move);
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
	 * Get absolute mouse position
	 * 
	 * @return mouse position
	 */
	public VectView getMousePos()
	{
		return mousePos;
	}
	
	
	/**
	 * @return true if mouse is inside window.
	 */
	public boolean isMouseInside()
	{
		return Mouse.isInsideWindow();
	}
	
	
	/**
	 * Trap mouse cursor in the window
	 * 
	 * @param grab true to grab
	 */
	public void grabMouse(boolean grab)
	{
		Mouse.setGrabbed(grab);
	}
}
