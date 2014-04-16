package mightypork.gamecore.input;


import mightypork.gamecore.control.events.KeyEvent;


/**
 * Key binding, trigger activated by a keystroke event
 * 
 * @author MightyPork
 */
public class KeyBinding implements KeyEvent.Listener {
	
	private final KeyStroke keystroke;
	private Runnable handler;
	private boolean wasActive = false;
	
	
	/**
	 * @param stroke trigger keystroke
	 * @param handler action
	 */
	public KeyBinding(KeyStroke stroke, Runnable handler) {
		this.keystroke = stroke;
		this.handler = handler;
		
		wasActive = keystroke.isActive();
	}
	
	
	/**
	 * Check for equality of keystroke
	 * 
	 * @param stroke other keystroke
	 * @return true if keystrokes are equal (cannot co-exist)
	 */
	public boolean matches(KeyStroke stroke)
	{
		return this.keystroke.equals(stroke);
	}
	
	
	/**
	 * @param handler event handler
	 */
	public void setHandler(Runnable handler)
	{
		this.handler = handler;
	}
	
	
	@Override
	public void receive(KeyEvent event)
	{
		// ignore unrelated events
		if (!keystroke.getKeys().contains(event.getKey())) return;
		
		// run handler when event was met
		if (keystroke.isActive() && !wasActive) {
			handler.run();
		}
		
		wasActive = keystroke.isActive();
	}
	
}
