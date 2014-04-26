package mightypork.gamecore.control.events.input;


import mightypork.util.control.eventbus.BusEvent;
import mightypork.util.control.eventbus.event_flags.UnloggedEvent;

import org.lwjgl.input.Keyboard;


/**
 * A keyboard event
 * 
 * @author MightyPork
 */
@UnloggedEvent
public class KeyEvent extends BusEvent<KeyListener> {
	
	private final int key;
	private final boolean down;
	private final char c;
	
	
	/**
	 * @param key key that triggered the event. Can be KEY_NONE.
	 * @param c typed char (can be zero char)
	 * @param down true = pressed, false = released.
	 */
	public KeyEvent(int key, char c, boolean down)
	{
		this.key = key;
		this.c = c;
		this.down = down;
	}
	
	
	/**
	 * @return key code (see {@link org.lwjgl.input.Keyboard})
	 */
	public int getKey()
	{
		return key;
	}
	
	
	/**
	 * @return true if key was just pressed
	 */
	public boolean isDown()
	{
		return down;
	}
	
	
	/**
	 * @return true if key was just released
	 */
	public boolean isUp()
	{
		return !down;
	}
	
	
	/**
	 * @return event character (if any)
	 */
	public char getChar()
	{
		return c;
	}
	
	
	@Override
	public void handleBy(KeyListener keh)
	{
		keh.receive(this);
	}
	
	
	@Override
	public String toString()
	{
		return Keyboard.getKeyName(key) + ":" + (down ? "DOWN" : "UP");
	}
	
}
