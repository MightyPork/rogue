package mightypork.gamecore.input;


import mightypork.gamecore.core.App;
import mightypork.utils.string.StringUtil;

import org.lwjgl.input.Keyboard;


/**
 * Key stroke description
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class KeyStroke {
	
	private int mod;
	private int key;
	
	
	/**
	 * Create a Key Stroke
	 * 
	 * @param key key code
	 * @param modmask modifiers
	 */
	public KeyStroke(int key, int modmask) {
		setTo(key, modmask);
	}
	
	
	/**
	 * Change to...<br>
	 * (KeyStroke is mutable, so that upon changing it in Config, all existing
	 * key bindings are updated automatically.)
	 * 
	 * @param key key code
	 * @param modmask modifiers
	 */
	public void setTo(int key, int modmask)
	{
		this.key = key;
		this.mod = modmask | Keys.keyToMod(key); // for mods alone
	}
	
	
	/**
	 * Create a new keystroke without modifiers
	 * 
	 * @param key key code
	 */
	public KeyStroke(int key) {
		this(key, Keys.MOD_NONE);
	}
	
	
	/**
	 * @return true if the keystroke is currently down & modifiers match.
	 */
	public boolean isDown()
	{
		boolean st = Keyboard.isKeyDown(key);
		st &= (App.input().getActiveModKeys() == mod);
		
		return st;
	}
	
	
	public String toDataString()
	{
		String s = "";
		
		if (mod != Keys.MOD_NONE) s = Keys.modToString(mod);
		
		s += Keys.keyToString(key);
		
		return s;
	}
	
	
	public static KeyStroke createFromDataString(String dataString)
	{
		final KeyStroke ks = new KeyStroke(Keys.NONE, Keys.MOD_NONE);
		ks.fromDataString(dataString);
		return ks;
	}
	
	
	public void fromDataString(String dataString)
	{
		final String dataString1 = dataString.toUpperCase().replace('-', '+').replaceAll("[^A-Z0-9_+]", "");
		
		if (dataString1.contains("+")) {
			
			final String keyStr = StringUtil.fromLastChar(dataString1, '+');
			final String modStr = StringUtil.toLastChar(dataString1, '+');
			
			setTo(Keys.keyFromString(keyStr), Keys.modFromString(modStr));
			
		} else {
			setTo(Keys.keyFromString(dataString1), Keys.MOD_NONE);
		}
	}
	
	
	public int getKey()
	{
		return key;
	}
	
	
	public int getMod()
	{
		return mod;
	}
	
	
	@Override
	public String toString()
	{
		return toDataString();
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + key;
		result = prime * result + mod;
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final KeyStroke other = (KeyStroke) obj;
		if (key != other.key) return false;
		if (mod != other.mod) return false;
		return true;
	}
}
