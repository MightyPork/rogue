package mightypork.gamecore.input;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;


/**
 * Key stroke trigger
 * 
 * @author MightyPork
 */
public class KeyStroke {
	
	private final int mod;
	private final int key;
	private final boolean fallingEdge;
	
	
	/**
	 * KeyStroke
	 * 
	 * @param fallingEdge true for falling edge, up for rising edge
	 * @param mod_mask mods mask
	 * @param key key code
	 */
	public KeyStroke(boolean fallingEdge, int key, int mod_mask) {
		this.fallingEdge = fallingEdge;
		this.key = key;
		this.mod = mod_mask;
	}
	
	
	/**
	 * Rising edge keystroke
	 * @param mod_mask mods mask
	 * @param key key code
	 */
	public KeyStroke(int key, int mod_mask) {
		this(false, key, mod_mask);
	}
	
	
	/**
	 * Rising edge keystroke
	 * 
	 * @param key key code
	 */
	public KeyStroke(int key) {
		this(false, key, Keys.MOD_NONE);
	}
	
	
	/**
	 * @return true if the keystroke is currently satisfied (keys pressed)
	 */
	public boolean isActive()
	{
		boolean st = Keyboard.isKeyDown(key);
		st &= (InputSystem.getModifierKeys() == mod);
		
		return fallingEdge ? st : !st;
	}
	
	
	@Override
	public String toString()
	{
		String s = "(";
		
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
		
		s += Keyboard.getKeyName(key);
		
		s += fallingEdge ? ",DOWN" : ",UP";
		
		s += ")";
		
		return s;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (fallingEdge ? 1231 : 1237);
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
		KeyStroke other = (KeyStroke) obj;
		if (fallingEdge != other.fallingEdge) return false;
		if (key != other.key) return false;
		if (mod != other.mod) return false;
		return true;
	}
}
