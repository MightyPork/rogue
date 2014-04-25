package mightypork.rogue.world.entity;


import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import mightypork.rogue.world.EntityPos;
import mightypork.rogue.world.PathStep;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.files.ion.IonBundled;


public class EntityData implements IonBundled {
	
	public int health = 1;
	public int maxHealth = 1;
	public boolean dead = false;
	
	public final IonBundle extra = new IonBundle();
	public final Queue<PathStep> path = new LinkedList<>();
	public final EntityPos position = new EntityPos();
	
	
	@Override
	public void save(IonBundle bundle) throws IOException
	{
		bundle.put("health", health);
		bundle.put("max_health", maxHealth);
		bundle.put("dead", dead);
		bundle.putSequence("steps", path);
		bundle.putBundled("pos", position);
		
		bundle.put("extra", extra);
	}
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		health = bundle.get("health", health);
		maxHealth = bundle.get("max_health", maxHealth);
		dead = bundle.get("dead", dead);
		bundle.loadSequence("path", path);
		bundle.loadBundled("pos", position);
		
		bundle.loadBundle("extra", extra);
	}
	
}
