package mightypork.gamecore.input;


public interface KeyBinder {
	
	/**
	 * Bind handler to a keystroke, replace current handler if any
	 * 
	 * @param stroke trigger keystroke
	 * @param task handler
	 */
	void bindKeyStroke(KeyStroke stroke, Runnable task);
	
	
	/**
	 * Remove handler from a keystroke (id any)
	 * 
	 * @param stroke stroke
	 */
	void unbindKeyStroke(KeyStroke stroke);
	
}
