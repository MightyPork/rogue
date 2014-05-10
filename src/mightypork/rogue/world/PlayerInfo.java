package mightypork.rogue.world;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.ion.IonObjBundled;
import mightypork.rogue.world.item.Item;


public class PlayerInfo implements IonObjBundled {
	
	private int eid = -1; // marks not initialized
	private int level;
	
	private final List<Item> inventory = new ArrayList<>();
	private int selectedWeapon = -1;
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		eid = bundle.get("eid", eid);
		level = bundle.get("floor", level);
		selectedWeapon = bundle.get("weapon", selectedWeapon);
		bundle.loadSequence("inv", inventory);
	}
	
	
	@Override
	public void save(IonBundle bundle) throws IOException
	{
		bundle.put("eid", eid);
		bundle.put("floor", level);
		bundle.put("weapon", selectedWeapon);
		bundle.putSequence("inv", inventory);
	}
	
	
	public void setEID(int eid)
	{
		if (isInitialized()) throw new RuntimeException("Cannot change player EID.");
		this.eid = eid;
	}
	
	
	public void setLevelNumber(int level)
	{
		this.level = level;
	}
	
	
	public int getEID()
	{
		return eid;
	}
	
	
	public int getLevelNumber()
	{
		return level;
	}
	
	
	public boolean isInitialized()
	{
		return eid != -1;
	}
	
}
