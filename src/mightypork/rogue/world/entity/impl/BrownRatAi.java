package mightypork.rogue.world.entity.impl;


import mightypork.gamecore.util.math.Calc;
import mightypork.rogue.world.entity.Entity;


public class BrownRatAi extends GrayRatAi {
	
	public BrownRatAi(Entity entity)
	{
		super(entity);
		
		setAttackTime(1.2);
		setScanTime(1.3);
	}
	
	
	@Override
	protected double getScanRadius()
	{
		return isSleeping() ? Calc.randInt(3, 5) : Calc.randInt(5, 8);
	}
	
	
	@Override
	protected int getAttackStrength()
	{
		return Calc.randInt(2, 5);
	}
	
	
	@Override
	protected int getPreyAbandonDistance()
	{
		return Calc.randInt(11, 14);
	}
}
