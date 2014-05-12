package mightypork.rogue.world.entity.impl;


import mightypork.rogue.world.entity.AiTimer;
import mightypork.rogue.world.entity.Entity;


public class BossRatAi extends GrayRatAi {
	
	private final AiTimer healTimer = new AiTimer(0.5) {
		
		@Override
		public void run()
		{
			entity.health.addHealth(1); // heal
		}
	};
	
	
	public BossRatAi(Entity entity)
	{
		super(entity);
		
		setAttackTime(0.6);
	}
	
	
	@Override
	protected int getAttackStrength()
	{
		return 5 + rand.nextInt(4);
	}
	
	
	@Override
	protected int getPreyAbandonDistance()
	{
		return 15 + rand.nextInt(4);
	}
	
	
	@Override
	public void update(double delta)
	{
		super.update(delta);
		healTimer.update(delta);
	}
}
