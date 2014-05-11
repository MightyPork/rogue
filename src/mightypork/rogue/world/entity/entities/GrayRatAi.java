package mightypork.rogue.world.entity.entities;


import mightypork.rogue.world.entity.Entity;


public class GrayRatAi extends MonsterAi {
	
	public GrayRatAi(Entity entity)
	{
		super(entity);
		
		setAttackTime(1.2);
		setScanTime(1.5);
		setSleepTime(10);
	}
	
	
	@Override
	protected double getScanRadius()
	{
		return isSleeping() ? 2 + rand.nextInt(3) : 4 + rand.nextInt(3);
	}
	
	
	@Override
	protected double getAttackDistance()
	{
		return 1.1;
	}
	
	
	@Override
	protected int getAttackStrength()
	{
		return 1 + (rand.nextInt(5) == 0 ? 1 : 0);
	}
	
	
	@Override
	protected int getPreyAbandonDistance()
	{
		return 8 + rand.nextInt(4);
	}
	
	
	@Override
	protected boolean shouldSkipScan()
	{
		return false;//rand.nextInt(3) == 0;
	}
	
	
	@Override
	protected boolean shouldRandomlyAbandonPrey()
	{
		return false;//rand.nextInt(8) == 0;
	}
}
