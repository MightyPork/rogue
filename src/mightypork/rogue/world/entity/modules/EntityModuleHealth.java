package mightypork.rogue.world.entity.modules;


import java.io.IOException;

import mightypork.gamecore.util.error.IllegalValueException;
import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.math.Calc;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityModule;


public class EntityModuleHealth extends EntityModule {
	
	public EntityModuleHealth(Entity entity)
	{
		super(entity);
	}
	
	protected int health = 1;
	protected int maxHealth = 1;
	private double hitCooldownTime = 0.3;
	protected boolean dead = false;
	
	private double timeSinceLastDamage = Integer.MAX_VALUE;
	
	
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
	public boolean isModuleSaved()
	{
		return true;
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
	
	
	public void receiveDamage(int attackStrength)
	{
		if (timeSinceLastDamage < hitCooldownTime) return;
		
		setHealth(health - attackStrength);
		timeSinceLastDamage = 0;
	}
	
	
	public void fill()
	{
		setHealth(maxHealth);
	}
	
	
	@Override
	public void update(double delta)
	{
		if (timeSinceLastDamage < 3600) timeSinceLastDamage += delta;
	}
	
	
	/**
	 * @return seconds since last attack received (can be used for rendering)
	 */
	public double getTimeSinceLastDamage()
	{
		return timeSinceLastDamage;
	}
	
	
	/**
	 * Set how long after hit another hit can be received.
	 * 
	 * @param secs
	 */
	public void setHitCooldownTime(double secs)
	{
		this.hitCooldownTime = secs;
	}
}
