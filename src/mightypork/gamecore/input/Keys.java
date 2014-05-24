package mightypork.gamecore.input;


import java.util.HashMap;

import mightypork.gamecore.logging.Log;

import org.lwjgl.input.Keyboard;


/**
 * Key constants, from LWJGL {@link Keyboard}
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Keys {
	
	//@formatter:off

	public static final int NONE            = Keyboard.KEY_NONE;

	public static final int ESCAPE          = Keyboard.KEY_ESCAPE;
	
	public static final int NUM_1           = Keyboard.KEY_1;
	public static final int NUM_2           = Keyboard.KEY_2;
	public static final int NUM_3           = Keyboard.KEY_3;
	public static final int NUM_4           = Keyboard.KEY_4;
	public static final int NUM_5           = Keyboard.KEY_5;
	public static final int NUM_6           = Keyboard.KEY_6;
	public static final int NUM_7           = Keyboard.KEY_7;
	public static final int NUM_8           = Keyboard.KEY_8;
	public static final int NUM_9           = Keyboard.KEY_9;
	public static final int NUM_0           = Keyboard.KEY_0;
	
	public static final int Q               = Keyboard.KEY_Q;
	public static final int W               = Keyboard.KEY_W;
	public static final int E               = Keyboard.KEY_E;
	public static final int R               = Keyboard.KEY_R;
	public static final int T               = Keyboard.KEY_T;
	public static final int Y               = Keyboard.KEY_Y;
	public static final int U               = Keyboard.KEY_U;
	public static final int I               = Keyboard.KEY_I;
	public static final int O               = Keyboard.KEY_O;
	public static final int P               = Keyboard.KEY_P;
	public static final int A               = Keyboard.KEY_A;
	public static final int S               = Keyboard.KEY_S;
	public static final int D               = Keyboard.KEY_D;
	public static final int F               = Keyboard.KEY_F;
	public static final int G               = Keyboard.KEY_G;
	public static final int H               = Keyboard.KEY_H;
	public static final int J               = Keyboard.KEY_J;
	public static final int K               = Keyboard.KEY_K;
	public static final int L               = Keyboard.KEY_L;
	public static final int Z               = Keyboard.KEY_Z;
	public static final int X               = Keyboard.KEY_X;
	public static final int C               = Keyboard.KEY_C;
	public static final int V               = Keyboard.KEY_V;
	public static final int B               = Keyboard.KEY_B;
	public static final int N               = Keyboard.KEY_N;
	public static final int M               = Keyboard.KEY_M;

	public static final int MINUS           = Keyboard.KEY_MINUS;
	public static final int EQUALS          = Keyboard.KEY_EQUALS;
	public static final int SLASH           = Keyboard.KEY_SLASH;
	public static final int BACKSLASH       = Keyboard.KEY_BACKSLASH;
	public static final int L_BRACKET       = Keyboard.KEY_LBRACKET;
	public static final int R_BRACKET       = Keyboard.KEY_RBRACKET;
	public static final int SEMICOLON       = Keyboard.KEY_SEMICOLON;
	public static final int APOSTROPHE      = Keyboard.KEY_APOSTROPHE;
	public static final int GRAVE           = Keyboard.KEY_GRAVE;
	public static final int COMMA           = Keyboard.KEY_COMMA;
	public static final int PERIOD          = Keyboard.KEY_PERIOD;
	
	public static final int SPACE           = Keyboard.KEY_SPACE;
	public static final int BACKSPACE       = Keyboard.KEY_BACK;
	public static final int TAB             = Keyboard.KEY_TAB;
	
	public static final int F1              = Keyboard.KEY_F1;
	public static final int F2              = Keyboard.KEY_F2;
	public static final int F3              = Keyboard.KEY_F3;
	public static final int F4              = Keyboard.KEY_F4;
	public static final int F5              = Keyboard.KEY_F5;
	public static final int F6              = Keyboard.KEY_F6;
	public static final int F7              = Keyboard.KEY_F7;
	public static final int F8              = Keyboard.KEY_F8;
	public static final int F9              = Keyboard.KEY_F9;
	public static final int F10             = Keyboard.KEY_F10;
	public static final int F11             = Keyboard.KEY_F11;
	public static final int F12             = Keyboard.KEY_F12;
	public static final int F13             = Keyboard.KEY_F13;
	public static final int F14             = Keyboard.KEY_F14;
	public static final int F15             = Keyboard.KEY_F15;

	public static final int CAPS_LOCK       = Keyboard.KEY_CAPITAL;
	public static final int SCROLL_LOCK     = Keyboard.KEY_SCROLL;	
	public static final int NUM_LOCK        = Keyboard.KEY_NUMLOCK;
	
	public static final int NUMPAD_MINUS    = Keyboard.KEY_SUBTRACT;
	public static final int NUMPAD_PLUSS    = Keyboard.KEY_ADD;
	public static final int NUMPAD_0        = Keyboard.KEY_NUMPAD0;
	public static final int NUMPAD_1        = Keyboard.KEY_NUMPAD1;
	public static final int NUMPAD_2        = Keyboard.KEY_NUMPAD2;
	public static final int NUMPAD_3        = Keyboard.KEY_NUMPAD3;
	public static final int NUMPAD_4        = Keyboard.KEY_NUMPAD4;
	public static final int NUMPAD_5        = Keyboard.KEY_NUMPAD5;
	public static final int NUMPAD_6        = Keyboard.KEY_NUMPAD6;
	public static final int NUMPAD_7        = Keyboard.KEY_NUMPAD7;
	public static final int NUMPAD_8        = Keyboard.KEY_NUMPAD8;
	public static final int NUMPAD_9        = Keyboard.KEY_NUMPAD9;
	public static final int NUMPAD_DECIMAL  = Keyboard.KEY_DECIMAL;
	public static final int NUMPAD_ENTER    = Keyboard.KEY_NUMPADENTER;
	public static final int NUMPAD_DIVIDE   = Keyboard.KEY_DIVIDE;
	public static final int NUMPAD_MULTIPLY = Keyboard.KEY_MULTIPLY;
	
	public static final int L_CONTROL       = Keyboard.KEY_LCONTROL;
	public static final int R_CONTROL       = Keyboard.KEY_RCONTROL;
	public static final int L_ALT           = Keyboard.KEY_LMENU;
	public static final int R_ALT           = Keyboard.KEY_RMENU;
	public static final int L_SHIFT         = Keyboard.KEY_LSHIFT;
	public static final int R_SHIFT         = Keyboard.KEY_RSHIFT;
	public static final int L_META          = Keyboard.KEY_LMETA;
	public static final int R_META          = Keyboard.KEY_RMETA;
	
	public static final int UP              = Keyboard.KEY_UP;
	public static final int DOWN            = Keyboard.KEY_DOWN;
	public static final int LEFT            = Keyboard.KEY_LEFT;
	public static final int RIGHT           = Keyboard.KEY_RIGHT;

	public static final int HOME            = Keyboard.KEY_HOME;
	public static final int END             = Keyboard.KEY_END;
	
	public static final int PAGE_UP         = Keyboard.KEY_PRIOR;
	public static final int PAGE_DOWN       = Keyboard.KEY_NEXT;

	public static final int RETURN          = Keyboard.KEY_RETURN;
	public static final int PAUSE           = Keyboard.KEY_PAUSE;
	public static final int INSERT          = Keyboard.KEY_INSERT;
	public static final int DELETE          = Keyboard.KEY_DELETE;

	public static final byte MOD_NONE       = 0;
	public static final byte MOD_ALT        = 1;
	public static final byte MOD_CONTROL    = 2;
	public static final byte MOD_SHIFT      = 4;
	public static final byte MOD_META       = 8;
	//@formatter:on
	
	private static HashMap<String, String> loadAliasMap = new HashMap<>();
	private static HashMap<String, String> saveAliasMap = new HashMap<>();
	
	static {
		// init maps		
		loadAliasMap.put("ENTER", "RETURN");
		loadAliasMap.put("PGDN", "NEXT");
		loadAliasMap.put("PGUP", "PRIOR");
		loadAliasMap.put("PAGE_DOWN", "NEXT");
		loadAliasMap.put("PAGE_UP", "PRIOR");
		loadAliasMap.put("SPACEBAR", "SPACE");
		loadAliasMap.put("ESC", "ESCAPE");
		loadAliasMap.put("NUMPAD_DIVIDE", "DIVIDE");
		loadAliasMap.put("NUMPAD_MULTIPLY", "MULTIPLY");
		loadAliasMap.put("NUMPAD_ADD", "ADD");
		loadAliasMap.put("NUMPAD_SUBTRACT", "SUBTRACT");
		loadAliasMap.put("CAPS_LOCK", "CAPITAL");
		loadAliasMap.put("SCROLL_LOCK", "SROLL");
		loadAliasMap.put("NUM_LOCK", "NUMLOCK");
		loadAliasMap.put("BACKSPACE", "BACK");
		
		saveAliasMap.put("RETURN", "ENTER");
		saveAliasMap.put("ESCAPE", "ESC");
		saveAliasMap.put("NEXT", "PGDN");
		saveAliasMap.put("PRIOR", "PGUP");
		saveAliasMap.put("DIVIDE", "NUMPAD_DIVIDE");
		saveAliasMap.put("MULTIPLY", "NUMPAD_MULTIPLY");
		saveAliasMap.put("ADD", "NUMPAD_ADD");
		saveAliasMap.put("SUBTRACT", "NUMPAD_SUBTRACT");
		saveAliasMap.put("CAPITAL", "CAPS_LOCK");
		saveAliasMap.put("SROLL", "SCROLL_LOCK");
		saveAliasMap.put("NUMLOCK", "NUM_LOCK");
		saveAliasMap.put("BACK", "BACKSPACE");
	}
	
	
	public static int keyFromString(String key)
	{
		String key1 = key;
		if (loadAliasMap.containsKey(key1)) key1 = loadAliasMap.get(key1);
		
		final int index = Keyboard.getKeyIndex(key1);
		if (index == Keys.NONE && !key1.equals("NONE")) {
			Log.w("Could not parse key: " + key + " (" + key1 + ")");
		}
		return index;
	}
	
	
	public static int modFromString(String mod)
	{
		int mod_mask = Keys.MOD_NONE;
		
		if (mod.contains("CTRL")) {
			mod_mask |= Keys.MOD_CONTROL;
		}
		
		if (mod.contains("ALT")) {
			mod_mask |= Keys.MOD_ALT;
		}
		
		if (mod.contains("SHIFT")) {
			mod_mask |= Keys.MOD_SHIFT;
		}
		
		if (mod.contains("META") || mod.contains("WIN")) {
			mod_mask |= Keys.MOD_META;
		}
		
		return mod_mask;
	}
	
	
	public static String modToString(int mod)
	{
		String s = "";
		
		if ((mod & Keys.MOD_CONTROL) != 0) {
			s += "CTRL+";
		}
		
		if ((mod & Keys.MOD_ALT) != 0) {
			s += "ALT+";
		}
		
		if ((mod & Keys.MOD_SHIFT) != 0) {
			s += "SHIFT+";
		}
		
		if ((mod & Keys.MOD_META) != 0) {
			s += "META+";
		}
		
		return s;
	}
	
	
	public static String keyToString(int key)
	{
		String s = Keyboard.getKeyName(key);
		if (saveAliasMap.containsKey(s)) s = saveAliasMap.get(s);
		if (s == null) {
			Log.w("Could not stringify key: " + key);
			s = "NONE";
		}
		return s.toUpperCase();
	}
	
	
	public static int keyToMod(int key)
	{
		if (key == L_SHIFT || key == R_SHIFT) return MOD_SHIFT;
		if (key == L_CONTROL || key == R_CONTROL) return MOD_CONTROL;
		if (key == L_ALT || key == R_ALT) return MOD_ALT;
		if (key == L_META || key == R_META) return MOD_META;
		return MOD_NONE;
	}
	
}
