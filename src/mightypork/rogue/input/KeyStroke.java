package mightypork.rogue.input;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;


public class KeyStroke {
	
	private Set<Integer> keys = new LinkedHashSet<Integer>();
	private boolean down = true;
	
	
	/**
	 * KeyStroke
	 * 
	 * @param down true for falling edge, up for rising edge
	 * @param keys keys that must be pressed
	 */
	public KeyStroke(boolean down, int... keys) {
		this.down = down;
		for (int k : keys) {
			this.keys.add(k);
		}
	}
	
	
	/**
	 * Falling edge keystroke
	 * 
	 * @param keys
	 */
	public KeyStroke(int... keys) {
		for (int k : keys) {
			this.keys.add(k);
		}
	}
	
	
	public boolean isActive()
	{
		boolean st = true;
		for (int k : keys) {
			st &= Keyboard.isKeyDown(k);
		}
		
		return down ? st : !st;
	}
	
	
	public void setKeys(Set<Integer> keys)
	{
		this.keys = keys;
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
		KeyStroke other = (KeyStroke) obj;
		
		if (keys == null) {
			if (other.keys != null) return false;
		} else if (!keys.equals(other.keys)) {
			return false;
		}
		
		if (down != other.down) return false;
		
		return true;
	}
	
	
	@Override
	public String toString()
	{
		String s = "(";
		
		int cnt = 0;
		Iterator<Integer> i = keys.iterator();
		for (; i.hasNext(); cnt++) {
			if (cnt > 0) s += "+";
			s += Keyboard.getKeyName(i.next());
		}
		
		s += down ? ",DOWN" : "UP";
		
		s += ")";
		
		return s;
	}
	
	
	public Set<Integer> getKeys()
	{
		return keys;
	}
}
