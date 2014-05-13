package mightypork.rogue.world.entity.impl;


import java.io.IOException;
import java.util.List;

import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.gamecore.util.math.algo.Step;
import mightypork.gamecore.util.math.algo.pathfinding.PathFinder;
import mightypork.gamecore.util.math.algo.pathfinding.PathFinderProxy;
import mightypork.rogue.world.entity.AiTimer;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityModule;
import mightypork.rogue.world.entity.EntityType;
import mightypork.rogue.world.entity.modules.EntityMoveListener;
import mightypork.rogue.world.tile.Tile;


public class MonsterAi extends EntityModule implements EntityMoveListener {
	
	private boolean chasing = false;
	
	private final AiTimer timerFindPrey = new AiTimer(3) {
		
		@Override
		public void run()
		{
			if (!isIdle()) return;
			lookForTarget();
		}
	};
	
	private final AiTimer timerAttack = new AiTimer(1) {
		
		@Override
		public void run()
		{
			if (!isChasing()) return;
			
			final Entity prey = getPreyEntity();
			
			if (prey == null || prey.isDead()) return;
			
			attackPrey(prey);
		}
	};
	
	private final AiTimer timerRandomWalk = new AiTimer(0.2) {
		
		@Override
		public void run()
		{
			if (!isIdle()) return;
			
			if(entity.pos.isMoving()) return;
			
			if(Calc.rand.nextInt(10) == 0) {
				entity.pos.addStep(Sides.randomCardinal());
			}
		}
	};
	
	private PathFinder noDoorPf;
	
	/** Prey id */
	private int preyId = -1;
	
	
	public MonsterAi(final Entity entity) {
		super(entity);
		
		noDoorPf = new PathFinderProxy(entity.getPathFinder()) {
			
			@Override
			public boolean isAccessible(Coord pos)
			{
				final Tile t = entity.getLevel().getTile(pos);
				if (t.isDoor()) return false;
				
				return super.isAccessible(pos);
			}
			
		};
		
		noDoorPf.setIgnoreEnd(true);
		
		timerAttack.start();
		timerFindPrey.start();
	}
	
	
	@Override
	public void onStepFinished()
	{
		if (entity.isDead()) return;
		
		if (isChasing()) {
			final Entity prey = getPreyEntity();
			if (!isPreyValid(prey)) {
				stopChasing();
				return;
			}
			
			if (!isPreyInAttackRange(prey)) {
				stepTowardsPrey(prey);
			}
		}
	}
	
	
	@Override
	public void onPathFinished()
	{
	}
	
	
	@Override
	public void onPathInterrupted()
	{
	}
	
	
	@Override
	public void save(IonBundle bundle) throws IOException
	{
		bundle.putBundled("tscan", timerFindPrey);
		bundle.putBundled("tattack", timerAttack);
		
		bundle.put("chasing", chasing);
		
		bundle.put("prey", preyId);
	}
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		bundle.loadBundled("tscan", timerFindPrey);
		bundle.loadBundled("tattack", timerAttack);
		
		chasing = bundle.get("chasing", chasing);
		
		preyId = bundle.get("prey", preyId);
	}
	
	
	@Override
	public boolean isModuleSaved()
	{
		return true;
	}
	
	
	@Override
	public void update(double delta)
	{
		if (entity.isDead()) return;
		
		timerFindPrey.update(delta);
		timerAttack.update(delta);
		timerRandomWalk.update(delta);
		
		// go after the prey
		if (isChasing() && !entity.pos.isMoving()) {
			final Entity prey = getPreyEntity();
			if (prey == null) {
				// prey killed and cleaned from level
				stopChasing();
			}
			
			if (!isPreyInAttackRange(prey)) {
				stepTowardsPrey(prey);
			}
		}
		
	}
	
	
	public boolean isIdle()
	{
		return !chasing;
	}

	
	
	public boolean isChasing()
	{
		return chasing;
	}
	
	private void lookForTarget()
	{
		if (entity.isDead()) return;
			
		final Entity prey = entity.getLevel().getClosestEntity(entity.pos.getVisualPos(), EntityType.PLAYER, getScanRadius());
		if (prey != null) {
			
			// check if reachable without leaving room
			final List<Coord> noDoorPath = noDoorPf.findPath(entity.getCoord(), prey.getCoord());
			
			if (noDoorPath == null) return; // cant reach, give up
				
			startChasing(prey);
		}
	}
	
	
	private Entity getPreyEntity()
	{
		return entity.getLevel().getEntity(preyId);
	}
	
	
	private boolean isPreyInAttackRange(Entity prey)
	{
		return prey.getCoord().dist(entity.getCoord()) <= getAttackDistance();
	}
	
	
	private boolean isPreyValid(Entity prey)
	{
		return prey != null && !prey.isDead();
	}
	
	
	private void startChasing(Entity prey)
	{
		if (entity.isDead()) return;
		
		preyId = prey.getEntityId();
		chasing = true;
		
		entity.pos.setStepTime(getStepTime());
		
		// follow this one prey
		timerFindPrey.pause();
		
		onStepFinished(); // go towards prey
	}
	
	
	private void stopChasing()
	{
		chasing = false;
		
		entity.pos.setStepTime(getStepTime());
		
		preyId = -1;
		timerFindPrey.restart();
	}
	
	
	private List<Step> getPathToPrey(Entity prey)
	{
		if (!isPreyValid(prey)) return null;
		
		return entity.getPathFinder().findPathRelative(entity.getCoord(), prey.getCoord(), false, true);
	}
	
	
	private void stepTowardsPrey(Entity prey)
	{
		if (entity.isDead()) return;
		
		if (!isPreyValid(prey)) return;
		
		// if close enough
		if (isPreyInAttackRange(prey)) {
			// attack using the timed loop
			return;
		}
		
		final List<Step> preyPath = getPathToPrey(prey);
		
		if (preyPath == null || preyPath.size() > getPreyAbandonDistance()) {
			stopChasing();
			return;
		}
		
		entity.pos.cancelPath();
		entity.pos.addSteps(preyPath);
	}
	
	
	private void attackPrey(Entity prey)
	{
		if (entity.isDead()) return;
		
		if (!isPreyInAttackRange(prey)) return;
		
		prey.receiveAttack(entity, getAttackStrength());
	}
	
	
	protected void setAttackTime(double secs)
	{
		timerAttack.setDuration(secs);
	}
	
	
	protected void setScanTime(double secs)
	{
		timerFindPrey.setDuration(secs);
	}
	
	
	@DefaultImpl
	protected double getScanRadius()
	{
		return isIdle() ? Calc.randInt(1, 3) : Calc.randInt(4, 8); // For override
	}
	
	
	@DefaultImpl
	protected int getPreyAbandonDistance()
	{
		return Calc.randInt(5, 8); // For override
	}
	
	
	@DefaultImpl
	protected double getAttackDistance()
	{
		return 1;
	}
	
	
	@DefaultImpl
	protected int getAttackStrength()
	{
		return 1; // For override
	}
	
	@DefaultImpl
	protected double getStepTime()
	{
		return isIdle() ? 0.7 : 0.4;
	}
}
