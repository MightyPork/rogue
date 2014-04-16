package mightypork.gamecore.input;


import mightypork.util.control.Action;


/**
 * Can bind events to keys.
 * 
 * @author MightyPork
 */
public interface KeyBinder {
	
	/**
	 * Bind handler to a keystroke, replace current handler if any
	 * 
	 * @param stroke trigger keystroke
	 * @param task handler; can be {@link Runnable} or {@link Action}
	 */
	void bindKeyStroke(KeyStroke stroke, Runnable task);
	
	
	/**
	 * Remove handler from a keystroke (id any)
	 * 
	 * @param stroke stroke
	 */
	void unbindKeyStroke(KeyStroke stroke);
	
}
