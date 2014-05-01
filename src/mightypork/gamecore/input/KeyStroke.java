package mightypork.gamecore.input;


import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;


/**
 * Key stroke trigger
 * 
 * @author MightyPork
 */
public class KeyStroke {
	
	private final Set<Integer> keys = new LinkedHashSet<>();
	private final boolean fallingEdge;
	
	
	/**
	 * KeyStroke
	 * 
	 * @param fallingEdge true for falling edge, up for rising edge
	 * @param keys keys that must be pressed
	 */
	public KeyStroke(boolean fallingEdge, int... keys)
	{
		this.fallingEdge = fallingEdge;
		for (final int k : keys) {
			this.keys.add(k);
		}
	}
	
	
	/**
	 * Rising edge keystroke
	 * 
	 * @param keys
	 */
	public KeyStroke(int... keys)
	{
		fallingEdge = false;
		for (final int k : keys) {
			this.keys.add(k);
		}
	}
	
	
	/**
	 * @return true if the keystroke is currently satisfied (keys pressed)
	 */
	public boolean isActive()
	{
		boolean st = true;
		for (final int k : keys) {
			st &= Keyboard.isKeyDown(k);
		}
		
		return fallingEdge ? st : !st;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keys == null) ? 0 : keys.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof KeyStroke)) return false;
		final KeyStroke other = (KeyStroke) obj;
		
		if (keys == null) {
			if (other.keys != null) return false;
		} else if (!keys.equals(other.keys)) {
			return false;
		}
		
		if (fallingEdge != other.fallingEdge) return false;
		
		return true;
	}
	
	
	@Override
	public String toString()
	{
		String s = "(";
		
		int cnt = 0;
		final Iterator<Integer> i = keys.iterator();
		for (; i.hasNext(); cnt++) {
			if (cnt > 0) s += "+";
			s += Keyboard.getKeyName(i.next());
		}
		
		s += fallingEdge ? ",DOWN" : ",UP";
		
		s += ")";
		
		return s;
	}
	
	
	/**
	 * @return the key set
	 */
	public Set<Integer> getKeys()
	{
		return keys;
	}
}
