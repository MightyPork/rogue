package mightypork.gamecore.input;


import mightypork.gamecore.core.BackendModule;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Abstract input module.<br>
 * An input module takes care of dispatching mouse and keyboard events, provides
 * access to mouse position, key states etc.<br>
 * The input module also takes care of calling App.shutdown() when the user
 * requests exit (eg. clicks the titlebar close button)
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InputModule extends BackendModule implements KeyBinder {
	
	protected KeyBindingPool keybindings;
	
	
	@Override
	public final void init()
	{
		initKeyCodes();
		initDevices();
		
		keybindings = new KeyBindingPool();
		addChildClient(keybindings);
	}
	
	
	/**
	 * Initialize key codes for keys in {@link Keys}
	 */
	protected abstract void initKeyCodes();
	
	
	/**
	 * Initialize input devices (set up infrastructure for getting the input)
	 */
	protected abstract void initDevices();
	
	
	@Override
	public void bindKey(KeyStroke stroke, Trigger edge, Runnable task)
	{
		keybindings.bindKey(stroke, edge, task);
	}
	
	
	@Override
	public void unbindKey(KeyStroke stroke)
	{
		keybindings.unbindKey(stroke);
	}
	
	
	/**
	 * Get absolute mouse position. Should always return the same Vect instance
	 * (use a VectVar or similar).
	 * 
	 * @return mouse position
	 */
	public abstract Vect getMousePos();
	
	
	/**
	 * Check if mouse is inside window
	 * 
	 * @return true if mouse is inside window.
	 */
	public abstract boolean isMouseInside();
	
	
	/**
	 * Trap mouse cursor in the window / release it
	 * 
	 * @param grab true to grab, false to release
	 */
	public abstract void grabMouse(boolean grab);
	
	
	/**
	 * Check if key is down. The key comes from the Keys class, so the code is
	 * the one assigned in initKeyCodes()
	 * 
	 * @param key key to check
	 * @return is down
	 */
	public abstract boolean isKeyDown(Key key);
	
	
	/**
	 * Check mouse button state
	 * 
	 * @param button button to test (0 left, 1 right, 2 middle, 3,4,5... extra)
	 * @return true if the button exists and is down
	 */
	public abstract boolean isMouseButtonDown(int button);
}
