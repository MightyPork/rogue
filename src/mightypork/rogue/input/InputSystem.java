package mightypork.rogue.input;


import mightypork.rogue.AppAccess;
import mightypork.rogue.bus.Subsystem;
import mightypork.rogue.bus.events.ActionRequest;
import mightypork.rogue.bus.events.ActionRequest.RequestType;
import mightypork.rogue.bus.events.KeyboardEvent;
import mightypork.rogue.bus.events.MouseButtonEvent;
import mightypork.rogue.bus.events.MouseMotionEvent;
import mightypork.utils.control.interf.Updateable;
import mightypork.utils.math.coord.Coord;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


public class InputSystem extends Subsystem implements Updateable, KeyBinder {
	
	// listeners
	private final KeyBindingPool keybindings;
	private boolean yAxisDown = true;
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
		} catch (LWJGLException e) {
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
		
		Coord moveSum = Coord.zero();
		Coord lastPos = Coord.zero();
		boolean wasMouse = false;
		
		while (Mouse.next()) {
			onMouseEvent(moveSum, lastPos);
			wasMouse = true;
		}
		
		if (wasMouse && !moveSum.isZero()) bus().queue(new MouseMotionEvent(lastPos, moveSum));
		
		while (Keyboard.next()) {
			onKeyEvent();
		}
		
		if (Display.isCloseRequested()) {
			bus().queue(new ActionRequest(RequestType.SHUTDOWN));
		}
	}
	
	
	private void onMouseEvent(Coord moveSum, Coord lastPos)
	{
		int button = Mouse.getEventButton();
		boolean down = Mouse.getEventButtonState();
		Coord pos = new Coord(Mouse.getEventX(), Mouse.getEventY());
		Coord move = new Coord(Mouse.getEventDX(), Mouse.getEventDY());
		int wheeld = Mouse.getEventDWheel();
		
		if (yAxisDown) {
			flipScrY(pos);
			move.mul_ip(1, -1, 1);
		}
		
		if (button != -1 || wheeld != 0) {
			bus().queue(new MouseButtonEvent(pos, button, down, wheeld));
		}
		
		moveSum.add_ip(move);
		lastPos.setTo(pos);
	}
	
	
	private void onKeyEvent()
	{
		int key = Keyboard.getEventKey();
		boolean down = Keyboard.getEventKeyState();
		char c = Keyboard.getEventCharacter();
		
		bus().queue(new KeyboardEvent(key, c, down));
	}
	
	
	private void flipScrY(Coord c)
	{
		if (disp() != null) {
			c.setY_ip(disp().getSize().y - c.y);
		}
	}
	
	
	/**
	 * Set whether Y axis should go top-down instead of LWJGL default bottom-up.<br>
	 * Default = true.
	 * 
	 * @param yAxisDown
	 */
	public void setYDown(boolean yAxisDown)
	{
		this.yAxisDown = yAxisDown;
	}
}
