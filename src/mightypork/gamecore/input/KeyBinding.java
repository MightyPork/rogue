package mightypork.gamecore.input;


import mightypork.gamecore.input.KeyStroke.Edge;
import mightypork.gamecore.input.events.InputReadyListener;
import mightypork.gamecore.input.events.KeyEvent;
import mightypork.gamecore.input.events.KeyEventHandler;


/**
 * Key binding, trigger activated by a keystroke event
 * 
 * @author MightyPork
 */
public class KeyBinding implements KeyEventHandler, InputReadyListener {
	
	private final KeyStroke keystroke;
	private Runnable handler;
	private final Edge edge;
	
	
	/**
	 * @param edge trigger edge
	 * @param stroke trigger keystroke
	 * @param handler action
	 */
	public KeyBinding(KeyStroke stroke, Edge edge, Runnable handler)
	{
		this.keystroke = stroke;
		this.handler = handler;
		this.edge = edge;
		
		if (InputSystem.isReady()) keystroke.poll();
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
		// run handler when event was met
		if (keystroke.tryTrigger(edge)) {
			handler.run();
		}
	}
	
	
	@Override
	public void onInputReady()
	{
		keystroke.poll();
	}
	
}
