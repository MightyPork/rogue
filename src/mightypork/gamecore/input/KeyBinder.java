package mightypork.gamecore.input;


import mightypork.gamecore.gui.Action;


/**
 * Can bind events to keys.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface KeyBinder {
	
	/**
	 * Bind handler to a keystroke, replace current handler if any
	 * 
	 * @param edge trigger edge
	 * @param stroke trigger keystroke
	 * @param task handler; can be {@link Runnable} or {@link Action}
	 */
	void bindKey(KeyStroke stroke, Trigger edge, Runnable task);
	
	
	/**
	 * Remove handler from a keystroke (id any)
	 * 
	 * @param stroke stroke
	 */
	void unbindKey(KeyStroke stroke);
	
}
