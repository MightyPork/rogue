package mightypork.rogue.world.entity.modules;


import java.io.IOException;

import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityModule;
import mightypork.util.error.IllegalValueException;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.math.Calc;


public class EntityModuleHealth implements EntityModule {
	
	public EntityModuleHealth(Entity entity)
	{
		this.entity = entity;
	}
	
	private final Entity entity;
	
	protected int health = 1;
	protected int maxHealth = 1;
	protected boolean dead = false;
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		health = bundle.get("health", health);
		maxHealth = bundle.get("max_health", maxHealth);
		dead = bundle.get("dead", dead);
	}
	
	
	@Override
	public void save(IonBundle bundle) throws IOException
	{
		bundle.put("health", health);
		bundle.put("max_health", maxHealth);
		bundle.put("dead", dead);
	}
	
	
	@Override
	public void update(double delta)
	{
	}
	
	
	public int getHealth()
	{
		return health;
	}
	
	
	public void setHealth(int health)
	{
		
		this.health = Calc.clamp(health, 0, maxHealth);
		
		if (health <= 0) {
			setDead(true);
			entity.onKilled();
		}
	}
	
	
	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	
	public void setMaxHealth(int maxHealth)
	{
		if (maxHealth <= 0) throw new IllegalValueException("Max health out of allowed range: " + maxHealth);
		this.maxHealth = maxHealth;
	}
	
	
	public boolean isDead()
	{
		return dead;
	}
	
	
	public void setDead(boolean dead)
	{
		this.dead = dead;
	}
	
}
