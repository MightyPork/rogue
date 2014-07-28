package mightypork.rogue.world.entity.impl;


import mightypork.rogue.world.entity.Entity;
import mightypork.utils.math.Calc;


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
		return isIdle() ? Calc.randInt(2, 4) : Calc.randInt(5, 8);
	}


	@Override
	protected int getAttackStrength()
	{
		return Calc.randInt(2, 4);
	}


	@Override
	protected int getPreyAbandonDistance()
	{
		return Calc.randInt(7, 12);
	}


	@Override
	protected double getStepTime()
	{
		return isIdle() ? 0.5 : 0.38;
	}
}
