package mightypork.gamecore.input;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mightypork.gamecore.core.App;
import mightypork.utils.logging.Log;


/**
 * Key constants & translation table.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Keys {
	
	//@formatter:off
	public static final Key NONE            = new Key("NONE", "NULL");

	public static final Key NUM_0           = new Key("0", "ZERO");
	public static final Key NUM_1           = new Key("1", "ONE");
	public static final Key NUM_2           = new Key("2", "TWO");
	public static final Key NUM_3           = new Key("3", "THREE");
	public static final Key NUM_4           = new Key("4", "FOUR");
	public static final Key NUM_5           = new Key("5", "FIVE");
	public static final Key NUM_6           = new Key("6", "SIX");
	public static final Key NUM_7           = new Key("7", "SEVEN");
	public static final Key NUM_8           = new Key("8", "EIGHT");
	public static final Key NUM_9           = new Key("9", "NINE");
	
	public static final Key Q               = new Key("Q");
	public static final Key W               = new Key("W");
	public static final Key E               = new Key("E");
	public static final Key R               = new Key("R");
	public static final Key T               = new Key("T");
	public static final Key Y               = new Key("Y");
	public static final Key U               = new Key("U");
	public static final Key I               = new Key("I");
	public static final Key O               = new Key("O");
	public static final Key P               = new Key("P");
	public static final Key A               = new Key("A");
	public static final Key S               = new Key("S");
	public static final Key D               = new Key("D");
	public static final Key F               = new Key("F");
	public static final Key G               = new Key("G");
	public static final Key H               = new Key("H");
	public static final Key J               = new Key("J");
	public static final Key K               = new Key("K");
	public static final Key L               = new Key("L");
	public static final Key Z               = new Key("Z");
	public static final Key X               = new Key("X");
	public static final Key C               = new Key("C");
	public static final Key V               = new Key("V");
	public static final Key B               = new Key("B");
	public static final Key N               = new Key("N");
	public static final Key M               = new Key("M");

	public static final Key MINUS           = new Key("MINUS", "DASH");
	public static final Key EQUALS          = new Key("EQUALS");
	public static final Key SLASH           = new Key("SLASH");
	public static final Key BACKSLASH       = new Key("BACKSLASH");
	public static final Key BRACKET_LEFT    = new Key("LBRACKET", "LEFT_BRACKET");
	public static final Key BRACKET_RIGHT   = new Key("RBRACKET", "RIGHT_BRACKET");
	public static final Key SEMICOLON       = new Key("SEMICOLON");
	public static final Key APOSTROPHE      = new Key("APOSTROPHE", "APOS");
	public static final Key GRAVE           = new Key("GRAVE", "ACCENT");
	public static final Key COMMA           = new Key("COMMA");
	public static final Key PERIOD          = new Key("PERIOD", "DOT", "POINT");
	
	public static final Key SPACE           = new Key("SPACE", "SPACEBAR");
	public static final Key BACKSPACE       = new Key("BACKSPACE", "BACK");
	public static final Key TAB             = new Key("TAB", "TABULATOR", "INDENT");
	public static final Key ESCAPE          = new Key("ESC", "ESCAPE");
	
	// those probably can't be used
	public static final Key APPS            = new Key("APPS");
	public static final Key POWER           = new Key("POWER");
	public static final Key SLEEP           = new Key("SLEEP");
	public static final Key MENU            = new Key("MENU");
	
	public static final Key F1              = new Key("F1");
	public static final Key F2              = new Key("F2");
	public static final Key F3              = new Key("F3");
	public static final Key F4              = new Key("F4");
	public static final Key F5              = new Key("F5");
	public static final Key F6              = new Key("F6");
	public static final Key F7              = new Key("F7");
	public static final Key F8              = new Key("F8");
	public static final Key F9              = new Key("F9");
	public static final Key F10             = new Key("F10");
	public static final Key F11             = new Key("F11");
	public static final Key F12             = new Key("F12");
	public static final Key F13             = new Key("F13");
	public static final Key F14             = new Key("F14");
	public static final Key F15             = new Key("F15");

	// probably not possible to bind to those.
	public static final Key CAPS_LOCK       = new Key("CAPSLOCK", "CAPS", "CAPITAL");
	public static final Key SCROLL_LOCK     = new Key("SCROLL", "SCROLL_LOCK");	
	public static final Key NUM_LOCK        = new Key("NUMLOCK");
	
	public static final Key NUMPAD_MINUS    = new Key("SUBTRACT", "NUMPAD_MINUS", "NUMPAD_SUBTRACT");
	public static final Key NUMPAD_PLUSS    = new Key("ADD", "NUMPAD_PLUS", "NUMPAD_ADD");
	public static final Key NUMPAD_0        = new Key("NUMPAD_0");
	public static final Key NUMPAD_1        = new Key("NUMPAD_1");
	public static final Key NUMPAD_2        = new Key("NUMPAD_2");
	public static final Key NUMPAD_3        = new Key("NUMPAD_3");
	public static final Key NUMPAD_4        = new Key("NUMPAD_4");
	public static final Key NUMPAD_5        = new Key("NUMPAD_5");
	public static final Key NUMPAD_6        = new Key("NUMPAD_6");
	public static final Key NUMPAD_7        = new Key("NUMPAD_7");
	public static final Key NUMPAD_8        = new Key("NUMPAD_8");
	public static final Key NUMPAD_9        = new Key("NUMPAD_9");
	public static final Key NUMPAD_DECIMAL  = new Key("DECIMAL", "NUMPAD_DECIMAL", "NUMPAD_PERIOD", "NUMPAD_POINT");
	public static final Key NUMPAD_ENTER    = new Key("NUMPAD_ENTER", "NUMPADRETURN", "NUMPAD_RETURN");
	public static final Key NUMPAD_DIVIDE   = new Key("DIVIDE", "NUMPAD_DIVIDE", "NUMPAD_SLASH");
	public static final Key NUMPAD_MULTIPLY = new Key("MULTIPLY", "NUMPAD_MULTIPLY", "NUMPAD_ASTERISK");
	
	public static final Key CONTROL_LEFT    = new Key("LCONTROL", "LEFT_CONTROL", "LCTRL", "LEFT_CTRL");
	public static final Key CONTROL_RIGHT   = new Key("RCONTROL", "RIGHT_CONTROL", "RCTRL", "RIGHT_CTRL");
	public static final Key ALT_LEFT        = new Key("LALT", "LMENU", "LEFT_MENU");
	public static final Key ALT_RIGHT       = new Key("RALT", "RMENU", "RIGHT_MENU");
	public static final Key SHIFT_LEFT      = new Key("LSHIFT", "LEFT_SHIFT");
	public static final Key SHIFT_RIGHT     = new Key("RSHIFT", "RIGHT_SHIFT");
	public static final Key META_LEFT       = new Key("LMETA", "LEFT_META", "LWIN", "LEFT_WIN");
	public static final Key META_RIGHT      = new Key("RMETA", "RIGHT_META", "RWIN", "RIGHT_WIN");
	
	public static final Key UP              = new Key("UP", "ARROW_UP");
	public static final Key DOWN            = new Key("DOWN", "ARROW_DOWN");
	public static final Key LEFT            = new Key("LEFT", "ARROW_LEFT");
	public static final Key RIGHT           = new Key("RIGHT", "ARROW_RIGHT");

	public static final Key HOME            = new Key("HOME");
	public static final Key END             = new Key("END");
	
	public static final Key PAGE_UP         = new Key("PAGE_UP", "PGUP", "PRIOR");
	public static final Key PAGE_DOWN       = new Key("PAGE_DOWN", "PGDN", "NEXT");

	public static final Key RETURN          = new Key("ENTER", "RETURN", "CR");
	public static final Key PAUSE           = new Key("PAUSE", "BREAK");
	public static final Key INSERT          = new Key("INSERT");
	public static final Key DELETE          = new Key("DELETE");
	public static final Key SYSRQ           = new Key("SYSRQ"); // wtf is this anyway?

	// here go modifier bits
	public static final byte MOD_NONE       = 0;
	public static final byte MOD_ALT        = 1;
	public static final byte MOD_CONTROL    = 2;
	public static final byte MOD_SHIFT      = 4;
	public static final byte MOD_META       = 8;
	//@formatter:on
	
	private static Map<Integer, Key> lookupByCode = new HashMap<>(100);
	private static List<Key> keyList = new ArrayList<>(100);
	
	static {
		// define none key
		NONE.setCode(0);
		
		// Use reflection to find keys
		Field[] fields = Keys.class.getFields();
		try {
			for (Field field : fields) {
				int modifiers = field.getModifiers();
				if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers) && Modifier.isFinal(modifiers) && field.getType().equals(Key.class)) {
					
					keyList.add((Key) field.get(null));
				}
			}
		} catch (Exception e) {}
	}
	
	
	/**
	 * Build lookup table by key codes
	 */
	private static void buildCodeLookupTable()
	{
		lookupByCode.clear();
		
		lookupByCode.put(NONE.getCode(), NONE);
		
		for (Key k : keyList) {
			if (!k.isDefined()) continue;
			if (!lookupByCode.containsKey(k.getCode())) {
				lookupByCode.put(k.getCode(), k);
			}
		}
		
		if (lookupByCode.size() == 1) {
			// NONE alone
			Log.w("Key codes are not ininitialized.");
		}
	}
	
	
	/**
	 * Convert a key name to a key code.
	 * 
	 * @param keyStr key name
	 * @return the key, or NONE if none matches
	 */
	public static Key stringToKey(String keyStr)
	{
		for (Key k : keyList) {
			if (k.matches(keyStr)) return k;
		}
		
		Log.w("No such key: " + keyStr);
		
		return NONE;
	}
	
	
	/**
	 * Convert a mod description to a mod mask. A mod description is a string
	 * containing CTRL,ALT,SHIFT,META, as in CTRL+ALT.<br>
	 * If none of the mod identifiers are found in the description, a MOD_NONE
	 * is returned.<br>
	 * This method is used for parsing keystroke, together with nameToKey().
	 * 
	 * @param modStr mod description (eg. CTRL+ALT)
	 * @return mod mask
	 */
	public static int stringToMod(String modStr)
	{
		int mod_mask = MOD_NONE;
		
		modStr = modStr.toUpperCase();
		
		if (modStr.contains("CTRL")) {
			mod_mask |= MOD_CONTROL;
		}
		
		if (modStr.contains("ALT")) {
			mod_mask |= MOD_ALT;
		}
		
		if (modStr.contains("SHIFT")) {
			mod_mask |= MOD_SHIFT;
		}
		
		if (modStr.contains("META") || modStr.contains("WIN")) {
			mod_mask |= MOD_META;
		}
		
		return mod_mask;
	}
	
	
	/**
	 * Convert a mod mask to a mod description, in a format recognized by
	 * stringToMod() - joining mods by +.
	 * 
	 * @param modMask mod mask
	 * @return mods as string (CTRL+ALT)
	 */
	public static String modToString(int modMask)
	{
		String s = "";
		
		if ((modMask & MOD_CONTROL) != 0) {
			s += "CTRL";
		}
		
		if ((modMask & MOD_ALT) != 0) {
			if (!s.isEmpty()) s += "+";
			s += "ALT";
		}
		
		if ((modMask & MOD_SHIFT) != 0) {
			if (!s.isEmpty()) s += "+";
			s += "SHIFT";
		}
		
		if ((modMask & MOD_META) != 0) {
			if (!s.isEmpty()) s += "+";
			s += "META";
		}
		
		return s;
	}
	
	
	/**
	 * Get a {@link Key} for key code.
	 * 
	 * @param keyCode code
	 * @return key instance, or NONE if no key matches.
	 */
	public static Key codeToKey(int keyCode)
	{
		if (lookupByCode.isEmpty()) buildCodeLookupTable();
		
		Key k = lookupByCode.get(keyCode);
		
		if (k == null) {
			Log.w("No key for code: " + keyCode);
			k = NONE;
		}
		
		return k;
	}
	
	
	/**
	 * Convert a key to mod mask, in case the key is one of the mod keys.
	 * 
	 * @param key the key
	 * @return mod mask corresponding to the key
	 */
	public static int keyToMod(Key key)
	{
		
		if (key == SHIFT_LEFT || key == SHIFT_RIGHT) return MOD_SHIFT;
		
		if (key == CONTROL_LEFT || key == CONTROL_RIGHT) return MOD_CONTROL;
		if (key == ALT_LEFT || key == ALT_RIGHT) return MOD_ALT;
		if (key == META_LEFT || key == META_RIGHT) return MOD_META;
		
		return MOD_NONE;
	}
	
	
	/**
	 * Get if the given key is down (call it's "isDown()" method).<br>
	 * This method is here just for completeness, since the getActiveMod() is
	 * also here.
	 * 
	 * @param key the key to check
	 * @return true if the key is down
	 */
	public static boolean isKeyDown(Key key)
	{
		return key.isDown();
	}
	
	
	/**
	 * Get currently active key modifiers
	 * 
	 * @return active mod mask (mod bits ored)
	 */
	public static int getActiveMod()
	{
		int mods = 0;
		
		InputModule inp = App.input();
		
		if (inp.isKeyDown(Keys.ALT_LEFT) || inp.isKeyDown(Keys.ALT_RIGHT)) {
			mods |= Keys.MOD_ALT;
		}
		
		if (inp.isKeyDown(Keys.SHIFT_LEFT) || inp.isKeyDown(Keys.SHIFT_RIGHT)) {
			mods |= Keys.MOD_SHIFT;
		}
		
		if (inp.isKeyDown(Keys.CONTROL_LEFT) || inp.isKeyDown(Keys.CONTROL_RIGHT)) {
			mods |= Keys.MOD_CONTROL;
		}
		
		if (inp.isKeyDown(Keys.META_LEFT) || inp.isKeyDown(Keys.META_RIGHT)) {
			mods |= Keys.MOD_META;
		}
		
		return mods;
	}
	
}
