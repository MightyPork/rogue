package mightypork.rogue.input;


import mightypork.rogue.AppAccess;
import mightypork.rogue.AppSubsystem;
import mightypork.rogue.input.events.KeyboardEvent;
import mightypork.rogue.input.events.MouseButtonEvent;
import mightypork.rogue.input.events.MouseMotionEvent;
import mightypork.utils.math.coord.Coord;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


public class InputSystem extends AppSubsystem implements KeyBinder {

	// listeners
	private KeyBindingPool keybindings;


	public InputSystem(AppAccess app) {
		super(app, true);
	}


	@Override
	protected void init()
	{
		initDevices();

		initChannels();

		// global keybindings
		keybindings = new KeyBindingPool();
		addChildSubscriber(keybindings);
	}


	@Override
	public void deinit()
	{
		Mouse.destroy();
		Keyboard.destroy();
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
		msgbus().createChannel(KeyboardEvent.class, KeyboardEvent.Listener.class);
		msgbus().createChannel(MouseMotionEvent.class, MouseMotionEvent.Listener.class);
		msgbus().createChannel(MouseButtonEvent.class, MouseButtonEvent.Listener.class);
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


	private void onMouseEvent()
	{
		int button = Mouse.getEventButton();
		boolean down = Mouse.getEventButtonState();
		Coord pos = new Coord(Mouse.getEventX(), Mouse.getEventY());
		Coord move = new Coord(Mouse.getEventDX(), Mouse.getEventDY());
		int wheeld = Mouse.getEventDWheel();

		if (button != -1 || wheeld != 0) msgbus().broadcast(new MouseButtonEvent(pos, button, down, wheeld));
		if (!move.isZero()) msgbus().broadcast(new MouseMotionEvent(pos, move));
	}


	private void onKeyEvent()
	{
		int key = Keyboard.getEventKey();
		boolean down = Keyboard.getEventKeyState();
		char c = Keyboard.getEventCharacter();
		msgbus().broadcast(new KeyboardEvent(key, c, down));
	}


	@Override
	public void update(double delta)
	{
		Display.processMessages(); // redundant if Display.update() is called in main loop

		while (Mouse.next()) {
			onMouseEvent();
		}

		while (Keyboard.next()) {
			onKeyEvent();
		}
	}
}
