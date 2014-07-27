package mightypork.gamecore.input;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import mightypork.gamecore.input.events.KeyEvent;
import mightypork.gamecore.input.events.KeyEventHandler;
import mightypork.utils.logging.Log;


/**
 * Key binding pool
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class KeyBindingPool implements KeyBinder, KeyEventHandler {
	
	private final Set<KeyBinding> bindings = new HashSet<>();
	
	
	/**
	 * Bind handler to a keystroke, replace current handler if any
	 * 
	 * @param stroke trigger keystroke
	 * @param task handler
	 */
	@Override
	public void bindKey(KeyStroke stroke, Trigger edge, Runnable task)
	{
		for (final KeyBinding kb : bindings) {
			if (kb.matches(stroke)) {
				Log.w("Duplicate KeyBinding (" + stroke + "), replacing handler.");
				kb.setHandler(task);
				return;
			}
		}
		
		bindings.add(new KeyBinding(stroke, edge, task));
	}
	
	
	/**
	 * Remove handler from keystroke (id any)
	 * 
	 * @param stroke stroke
	 */
	@Override
	public void unbindKey(KeyStroke stroke)
	{
		final Iterator<KeyBinding> iter = bindings.iterator();
		
		while (iter.hasNext()) {
			final KeyBinding kb = iter.next();
			if (kb.matches(stroke)) {
				iter.remove();
				return;
			}
		}
	}
	
	
	@Override
	public void receive(KeyEvent event)
	{
		for (final KeyBinding kb : bindings) {
			kb.receive(event);
		}
	}
}
