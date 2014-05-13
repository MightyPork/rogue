package mightypork.rogue.world.entity.impl;


import mightypork.gamecore.util.math.Calc;
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
		return isSleeping() ? Calc.randInt(2, 4) : Calc.randInt(4, 6);
	}
	
	
	@Override
	protected double getAttackDistance()
	{
		return 1.1;
	}
	
	
	@Override
	protected int getAttackStrength()
	{
		return 1 + (Calc.rand.nextInt(5) == 0 ? 1 : 0);
	}
	
	
	@Override
	protected int getPreyAbandonDistance()
	{
		return Calc.randInt(8, 11);
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
