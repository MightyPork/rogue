package mightypork.gamecore.input;


import mightypork.gamecore.core.BackendModule;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Abstract input module
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InputModule extends BackendModule implements KeyBinder {
	
	protected KeyBindingPool keybindings;
	
	
	@Override
	public final void init()
	{
		keybindings = new KeyBindingPool();
		addChildClient(keybindings);
		initDevices();
	}
	
	
	protected abstract void initDevices();
	
	
	@Override
	public void bindKey(KeyStroke stroke, Edge edge, Runnable task)
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
	 * Check if key is down (constant from the {@link Keys} class)
	 * 
	 * @param key key to check
	 * @return is down
	 */
	public abstract boolean isKeyDown(int key);
	
	
	/**
	 * Check mouse button state
	 * 
	 * @param button button to test (0 left, 1 right, 2 middle)
	 * @return button is down
	 */
	public abstract boolean isMouseButtonDown(int button);
	
	
	/**
	 * @return bit mask of active mod keys
	 */
	public abstract int getActiveModKeys();
}
