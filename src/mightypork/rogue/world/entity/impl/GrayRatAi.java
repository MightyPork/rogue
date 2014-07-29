package mightypork.rogue.world.entity.impl;


import mightypork.rogue.world.entity.Entity;
import mightypork.utils.math.Calc;


public class GrayRatAi extends MonsterAi {
	
	public GrayRatAi(Entity entity)
	{
		super(entity);
		
		setAttackTime(1.2);
		setScanTime(1.5);
	}
	
	
	@Override
	protected double getScanRadius()
	{
		return isIdle() ? Calc.randInt(2, 3) : Calc.randInt(4, 6);
	}
	
	
	@Override
	protected double getAttackDistance()
	{
		return 1.1;
	}
	
	
	@Override
	protected int getAttackStrength()
	{
		return Calc.randInt(1, 2);
	}
	
	
	@Override
	protected int getPreyAbandonDistance()
	{
		return Calc.randInt(7, 11);
	}
	
	
	@Override
	protected double getStepTime()
	{
		return isIdle() ? 0.7 : 0.34;
	}
}
