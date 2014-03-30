package mightypork.rogue.input;


import mightypork.rogue.App;
import mightypork.rogue.input.events.KeyboardEvent;
import mightypork.rogue.input.events.MouseButtonEvent;
import mightypork.rogue.input.events.MouseMotionEvent;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.patterns.Destroyable;
import mightypork.utils.patterns.Initializable;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


public class InputSystem implements KeyBinder, Destroyable, Initializable {

	private boolean initialized;

	// listeners
	private KeyBindingPool keybindings;


	public InputSystem() {
		initialize();
	}


	@Override
	public void initialize()
	{
		if (initialized) return;

		initDevices();

		initChannels();

		keybindings = new KeyBindingPool();

		App.msgbus().addSubscriber(keybindings);
	}


	private void initDevices()
	{
		try {
			Mouse.create();
			Keyboard.create();
			Keyboard.enableRepeatEvents(false);
		} catch (LWJGLException e) {
			throw new RuntimeException("Failed to initialize input devices.", e);
		}
	}


	private void initChannels()
	{
		App.msgbus().registerMessageType(KeyboardEvent.class, KeyboardEvent.Listener.class);
		App.msgbus().registerMessageType(MouseMotionEvent.class, MouseMotionEvent.Listener.class);
		App.msgbus().registerMessageType(MouseButtonEvent.class, MouseButtonEvent.Listener.class);
	}


	@Override
	public void destroy()
	{
		Mouse.destroy();
		Keyboard.destroy();
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
	 * Update inputs
	 */
	public final void poll()
	{
		Display.processMessages(); // redundant if Display.update() is called in main loop
		Mouse.poll();
		Keyboard.poll();

		while (Mouse.next()) {
			onMouseEvent();
		}

		while (Keyboard.next()) {
			onKeyEvent();
		}
	}


	private void onMouseEvent()
	{
		int button = Mouse.getEventButton();
		boolean down = Mouse.getEventButtonState();
		Coord pos = new Coord(Mouse.getEventX(), Mouse.getEventY());
		Coord move = new Coord(Mouse.getEventDX(), Mouse.getEventDY());
		int wheeld = Mouse.getEventDWheel();

		if (button != -1 || wheeld != 0) App.broadcast(new MouseButtonEvent(pos, button, down, wheeld));
		if(!move.isZero()) App.broadcast(new MouseMotionEvent(pos, move));
	}


	private void onKeyEvent()
	{
		int key = Keyboard.getEventKey();
		boolean down = Keyboard.getEventKeyState();
		char c = Keyboard.getEventCharacter();
		App.broadcast(new KeyboardEvent(key, c, down));
	}
}
