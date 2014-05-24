package mightypork.gamecore.input;


import mightypork.gamecore.util.strings.StringUtils;

import org.lwjgl.input.Keyboard;


/**
 * Key stroke trigger
 * 
 * @author Ondřej Hruška
 */
public class KeyStroke { //implements Pollable

	public static enum Edge
	{
		FALLING, RISING;
	}
	
	private int mod;
	private int key;
	
	
	/**
	 * KeyStroke
	 * 
	 * @param key key code
	 * @param mod_mask mods mask
	 */
	public KeyStroke(int key, int mod_mask)
	{
		setTo(key, mod_mask);
	}
	
	
	/**
	 * Rising edge keystroke
	 * 
	 * @param key key code
	 */
	public KeyStroke(int key)
	{
		this(key, Keys.MOD_NONE);
	}
	
	
	/**
	 * @return true if the keystroke is currently down & modifiers match.
	 */
	public boolean isDown()
	{
		boolean st = Keyboard.isKeyDown(key);
		st &= (InputSystem.getActiveModKeys() == mod);
		
		return st;
	}
	
	
	public void setTo(int key, int mod_mask)
	{
		this.key = key;
		this.mod = mod_mask | Keys.keyToMod(key); // for mods alone
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
			
			final String keyStr = StringUtils.fromLastChar(dataString1, '+');
			final String modStr = StringUtils.toLastChar(dataString1, '+');
			
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
