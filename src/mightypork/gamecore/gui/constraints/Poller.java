package mightypork.gamecore.gui.constraints;


import java.util.LinkedHashSet;
import java.util.Set;

import mightypork.gamecore.control.interf.Pollable;


/**
 * Used to poll a number of {@link Pollable}s, such as {@link RectCache}
 * 
 * @author MightyPork
 */
public class Poller implements Pollable {
	
	private final Set<Pollable> pollables = new LinkedHashSet<>();
	
	
	/**
	 * Add a pollable
	 * 
	 * @param p pollable
	 */
	public void add(Pollable p)
	{
		pollables.add(p);
	}
	
	
	/**
	 * Remove a pollalbe
	 * 
	 * @param p pollable
	 */
	public void remove(Pollable p)
	{
		pollables.remove(p);
	}
	
	
	@Override
	public void poll()
	{
		for (final Pollable p : pollables) {
			p.poll();
		}
	}
}
