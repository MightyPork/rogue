package mightypork.rogue.input;


import mightypork.rogue.bus.events.KeyboardEvent;


public class KeyBinding implements KeyboardEvent.Listener {
	
	private KeyStroke keystroke;
	private Runnable handler;
	private boolean wasActive = false;
	
	
	public KeyBinding(KeyStroke stroke, Runnable handler) {
		this.keystroke = stroke;
		this.handler = handler;
		
		wasActive = keystroke.isActive();
	}
	
	
	public boolean matches(KeyStroke stroke)
	{
		return this.keystroke.equals(stroke);
	}
	
	
	public void setHandler(Runnable handler)
	{
		this.handler = handler;
	}
	
	
	@Override
	public void receive(KeyboardEvent event)
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
