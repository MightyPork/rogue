package mightypork.gamecore.input;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.bus.clients.RootBusNode;
import mightypork.gamecore.control.bus.events.KeyEvent;
import mightypork.gamecore.control.bus.events.MouseButtonEvent;
import mightypork.gamecore.control.bus.events.MouseMotionEvent;
import mightypork.gamecore.control.interf.Updateable;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;
import mightypork.utils.math.constraints.NumberConstraint;
import mightypork.utils.math.coord.Coord;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


public class InputSystem extends RootBusNode implements Updateable, KeyBinder {
	
	// listeners
	private final KeyBindingPool keybindings;
	
	private static boolean inited = false;
	
	
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
	
	
	private static void initDevices()
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
	
	
	@Override
	public void update(double delta)
	{
		// was destroyed
		if (!Display.isCreated()) return;
		if (!Mouse.isCreated()) return;
		if (!Keyboard.isCreated()) return;
		
		Display.processMessages();
		
		final Coord moveSum = Coord.zero();
		final Coord lastPos = Coord.zero();
		boolean wasMouse = false;
		
		while (Mouse.next()) {
			onMouseEvent(moveSum, lastPos);
			wasMouse = true;
		}
		
		if (wasMouse && !moveSum.isZero()) {
			getEventBus().send(new MouseMotionEvent(lastPos, moveSum));
		}
		
		while (Keyboard.next()) {
			onKeyEvent();
		}
		
		if (Display.isCloseRequested()) {
			getEventBus().send(new ActionRequest(RequestType.SHUTDOWN));
		}
	}
	
	
	private void onMouseEvent(Coord moveSum, Coord lastPos)
	{
		final int button = Mouse.getEventButton();
		final boolean down = Mouse.getEventButtonState();
		final Coord pos = new Coord(Mouse.getEventX(), Mouse.getEventY());
		final Coord move = new Coord(Mouse.getEventDX(), Mouse.getEventDY());
		final int wheeld = Mouse.getEventDWheel();
		
		if (DisplaySystem.yAxisDown) {
			flipScrY(pos);
			move.mul_ip(1, -1, 1);
		}
		
		if (button != -1 || wheeld != 0) {
			getEventBus().send(new MouseButtonEvent(pos, button, down, wheeld));
		}
		
		moveSum.add_ip(move);
		lastPos.setTo(pos);
	}
	
	
	private void onKeyEvent()
	{
		final int key = Keyboard.getEventKey();
		final boolean down = Keyboard.getEventKeyState();
		final char c = Keyboard.getEventCharacter();
		
		getEventBus().send(new KeyEvent(key, c, down));
	}
	
	
	private static void flipScrY(Coord c)
	{
		if (DisplaySystem.yAxisDown) c.setY_ip(DisplaySystem.getSize().y - c.y);
	}
	
	
	/**
	 * Get absolute mouse position
	 * 
	 * @return mouse position
	 */
	public static Coord getMousePos()
	{
		final Coord pos = new Coord(Mouse.getX(), Mouse.getY());
		flipScrY(pos);
		return pos;
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
	
	/** Horizontal mouse position */
	public static final NumberConstraint mouseX = new NumberConstraint() {
		
		@Override
		public double getValue()
		{
			return getMousePos().x;
		}
	};
	
	/** Vertical mouse position */
	public static final NumberConstraint mouseY = new NumberConstraint() {
		
		@Override
		public double getValue()
		{
			return getMousePos().y;
		}
	};
}
