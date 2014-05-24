package mightypork.gamecore.input;


import mightypork.gamecore.gui.Action;
import mightypork.gamecore.input.KeyStroke.Edge;


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
	void bindKey(KeyStroke stroke, Edge edge, Runnable task);
	
	
	/**
	 * Remove handler from a keystroke (id any)
	 * 
	 * @param stroke stroke
	 */
	void unbindKey(KeyStroke stroke);
	
}
