package mightypork.rogue.world;


import java.io.IOException;

import mightypork.util.ion.IonBundle;
import mightypork.util.ion.IonBundled;


public class PlayerInfo implements IonBundled {
	
	private int eid = -1; // marks not initialized
	private int level;
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		eid = bundle.get("attached_eid", 0);
		level = bundle.get("current_level", 0);
	}
	
	
	@Override
	public void save(IonBundle bundle) throws IOException
	{
		bundle.put("attached_eid", eid);
		bundle.put("current_level", level);
	}
	
	
	public void setEID(int eid)
	{
		if (isInitialized()) throw new RuntimeException("Cannot change player EID.");
		this.eid = eid;
	}
	
	
	public void setLevel(int level)
	{
		this.level = level;
	}
	
	
	public int getEID()
	{
		return eid;
	}
	
	
	public int getLevel()
	{
		return level;
	}
	
	
	public boolean isInitialized()
	{
		return eid != -1;
	}
	
}
