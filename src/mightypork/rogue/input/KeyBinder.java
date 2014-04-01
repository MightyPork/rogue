package mightypork.rogue.input;


public interface KeyBinder {
	
	/**
	 * Bind handler to a keystroke, replace current handler if any
	 * 
	 * @param stroke trigger keystroke
	 * @param task handler
	 */
	abstract void bindKeyStroke(KeyStroke stroke, Runnable task);
	
	
	/**
	 * Remove handler from a keystroke (id any)
	 * 
	 * @param stroke stroke
	 */
	abstract void unbindKeyStroke(KeyStroke stroke);
	
}
