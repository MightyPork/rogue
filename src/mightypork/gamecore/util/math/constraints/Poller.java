package mightypork.gamecore.util.math.constraints;


import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Used to poll a number of {@link Pollable}s
 * 
 * @author Ondřej Hruška (MightyPork)
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
