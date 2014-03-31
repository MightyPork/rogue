package mightypork.rogue.input;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import mightypork.rogue.bus.events.KeyboardEvent;
import mightypork.utils.logging.Log;


/**
 * Key binding pool
 * 
 * @author MightyPork
 */
public class KeyBindingPool implements KeyBinder, KeyboardEvent.Listener {

	private Set<KeyBinding> bindings = new HashSet<KeyBinding>();


	/**
	 * Bind handler to a keystroke, replace current handler if any
	 * 
	 * @param stroke trigger keystroke
	 * @param task handler
	 */
	@Override
	public void bindKeyStroke(KeyStroke stroke, Runnable task)
	{
		for (KeyBinding kb : bindings) {
			if (kb.matches(stroke)) {
				Log.w("Duplicate KeyBinding (" + stroke + "), using newest handler.");
				kb.setHandler(task);
				return;
			}
		}

		bindings.add(new KeyBinding(stroke, task));
	}


	/**
	 * Remove handler from keystroke (id any)
	 * 
	 * @param stroke stroke
	 */
	@Override
	public void unbindKeyStroke(KeyStroke stroke)
	{
		Iterator<KeyBinding> iter = bindings.iterator();

		while (iter.hasNext()) {
			KeyBinding kb = iter.next();
			if (kb.matches(stroke)) {
				iter.remove();
				return;
			}
		}
	}


	@Override
	public void receive(KeyboardEvent event)
	{
		for (KeyBinding kb : bindings) {
			kb.receive(event);
		}
	}
}
