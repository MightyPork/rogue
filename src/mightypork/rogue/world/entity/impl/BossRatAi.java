package mightypork.rogue.world.entity.impl;


import mightypork.rogue.world.entity.AiTimer;
import mightypork.rogue.world.entity.Entity;
import mightypork.utils.math.Calc;


public class BossRatAi extends GrayRatAi {
	
	private final AiTimer healTimer = new AiTimer(0.5) {
		
		@Override
		public void run()
		{
			entity.health.addHealth(1); // heal
		}
	};
	
	
	public BossRatAi(Entity entity) {
		super(entity);
		
		setAttackTime(0.3);
	}
	
	
	@Override
	protected int getAttackStrength()
	{
		return Calc.randInt(2, 3);
	}
	
	
	@Override
	protected int getPreyAbandonDistance()
	{
		return Calc.randInt(12, 18);
	}
	
	
	@Override
	public void update(double delta)
	{
		super.update(delta);
		
		healTimer.update(delta);
	}
	
	
	@Override
	protected double getStepTime()
	{
		return isIdle() ? 0.6 : 0.4;
	}
}
