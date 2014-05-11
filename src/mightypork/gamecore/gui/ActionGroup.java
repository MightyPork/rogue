package mightypork.gamecore.gui;


import java.util.HashSet;
import java.util.Set;


public class ActionGroup implements Enableable {
	
	private boolean enabled = true;
	
	private final Set<Enableable> groupMembers = new HashSet<>();
	
	
	@Override
	public void enable(boolean yes)
	{
		enabled = yes;
		for (final Enableable e : groupMembers)
			e.enable(yes);
	}
	
	
	@Override
	public boolean isEnabled()
	{
		return enabled;
	}
	
	
	public void add(Enableable action)
	{
		groupMembers.add(action);
	}
	
}
