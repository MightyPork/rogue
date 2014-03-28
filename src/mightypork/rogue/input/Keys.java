package mightypork.rogue.input;


import org.lwjgl.input.Keyboard;


/**
 * Key state handler
 */
public class Keys {

	private static boolean[] prevKeys;
	private static boolean[] keys;


	/**
	 * initialize key state handler
	 */
	private static void init()
	{

		if (keys == null) {
			keys = new boolean[Keyboard.KEYBOARD_SIZE];
		}

		if (prevKeys == null) {
			prevKeys = new boolean[Keyboard.KEYBOARD_SIZE];
		}
	}


	/**
	 * method called when key event was detected in Screen class.
	 * 
	 * @param key
	 * @param down
	 */
	public static void onKey(int key, boolean down)
	{

		init();

		prevKeys[key] = keys[key];
		keys[key] = down;
	}


	/**
	 * Check if key is down
	 * 
	 * @param key key index
	 * @return is down
	 */
	public static boolean isDown(int key)
	{

		init();

		return keys[key];
	}


	/**
	 * Check if key is up
	 * 
	 * @param key key index
	 * @return is up
	 */
	public static boolean isUp(int key)
	{

		init();

		return !keys[key];
	}


	/**
	 * Check if key was just pressed (changed state since last event on this
	 * key)
	 * 
	 * @param key key index
	 * @return true if changed state to DOWN
	 */
	public static boolean justPressed(int key)
	{

		init();

		return !prevKeys[key] && keys[key];
	}


	/**
	 * Check if key was just released (changed state since last event on this
	 * key)
	 * 
	 * @param key key index
	 * @return true if changed state to UP
	 */
	public static boolean justReleased(int key)
	{

		init();

		return prevKeys[key] && !keys[key];
	}


	/**
	 * Destroy "just" flag for a key.
	 * 
	 * @param key key index
	 */
	public static void destroyChangeState(int key)
	{

		init();

		prevKeys[key] = keys[key];
	}
}
