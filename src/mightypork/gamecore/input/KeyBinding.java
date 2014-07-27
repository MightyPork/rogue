package mightypork.gamecore.input;


import mightypork.gamecore.input.events.KeyEvent;
import mightypork.gamecore.input.events.KeyEventHandler;


/**
 * Key binding, trigger activated by a keystroke event
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class KeyBinding implements KeyEventHandler {
	
	private final KeyStroke keystroke;
	private Runnable handler;
	private final Trigger edge;
	private boolean wasDown = false;
	
	
	/**
	 * @param edge trigger edge
	 * @param stroke trigger keystroke
	 * @param handler action
	 */
	public KeyBinding(KeyStroke stroke, Trigger edge, Runnable handler) {
		this.keystroke = stroke;
		this.handler = handler;
		this.edge = edge;
		wasDown = stroke.isDown();
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
		final boolean nowDown = keystroke.isDown();
		
		boolean trigger = false;
		trigger |= (edge == Trigger.FALLING && (!wasDown && nowDown));
		trigger |= (edge == Trigger.RISING && (wasDown && !nowDown));
		wasDown = nowDown;
		
		// run handler when event was met
		if (trigger) {
			handler.run();
		}
	}
}
