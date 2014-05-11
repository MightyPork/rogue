package mightypork.rogue.world.entity.entities;


import mightypork.rogue.world.entity.Entity;


public class BrownRatAi extends GrayRatAi {
	
	public BrownRatAi(Entity entity)
	{
		super(entity);
		
		setAttackTime(0.8);
		setScanTime(1);
	}
	
	
	@Override
	protected double getScanRadius()
	{
		return isSleeping() ? 3 + rand.nextInt(3) : 5 + rand.nextInt(3);
	}
	
	
	@Override
	protected int getAttackStrength()
	{
		return 3 + rand.nextInt(2);
	}
	
	
	@Override
	protected int getPreyAbandonDistance()
	{
		return 11 + rand.nextInt(4);
	}
}
