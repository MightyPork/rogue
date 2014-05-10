package mightypork.rogue.world.entity.entities;


import java.io.IOException;
import java.util.List;

import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.math.algo.Coord;
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
	
	private boolean sleeping = true;
	private boolean chasing = false;
	
	private final AiTimer timerFindPrey = new AiTimer(3) {
		
		@Override
		public void run()
		{
			if (chasing) return;
			//System.out.println("Mob looks around.");
			lookForTarget();
		}
	};
	
	private final AiTimer timerSleepStart = new AiTimer(10) {
		
		@Override
		public void run()
		{
			if (chasing) return;
			//System.out.println("Mob going to sleep");
			sleeping = true;
		}
	};
	
	private final AiTimer timerAttack = new AiTimer(3) {
		
		@Override
		public void run()
		{
			if (!chasing) return;
			
			final Entity prey = getPreyEntity();
			
			if (prey == null || prey.isDead()) {
				//System.out.println("prey dead?");
				return;
			}
			
			//System.out.println("Timed prey attack");
			attackPrey(prey);
		}
	};
	
	private PathFinder noDoorPf;
	
	/** Prey id */
	private int preyId = -1;
	
	
	public MonsterAi(final Entity entity)
	{
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
		timerSleepStart.start();
	}
	
	
	@Override
	public void onStepFinished()
	{
		//System.out.println("monster ai step finished.");
		if (chasing) {
			//System.out.println("chasing..");
			final Entity prey = getPreyEntity();
			if (!isPreyValid(prey)) {
				//System.out.println("prey dead or null, stop chasing: " + prey + ", prey.isdead " + prey.isDead());
				stopChasing();
				return;
			}
			
			if (shouldRandomlyAbandonPrey()) {
				stopChasing();
				return;
			}
			
			if (isPreyInAttackRange(prey)) {
				//System.out.println("prey in attack range");
				return; // attacking
			} else {
				stepTowardsPrey(prey);
			}
		} else {
			//System.out.println("not chasing.");
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
		bundle.putBundled("tsleep", timerSleepStart);
		bundle.putBundled("tattack", timerAttack);
		
		bundle.put("chasing", chasing);
		bundle.put("sleeping", sleeping);
		
		bundle.put("prey", preyId);
	}
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		bundle.loadBundled("tscan", timerFindPrey);
		bundle.loadBundled("tsleep", timerSleepStart);
		bundle.loadBundled("tattack", timerAttack);
		
		chasing = bundle.get("chasing", chasing);
		sleeping = bundle.get("sleeping", sleeping);
		
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
		timerFindPrey.update(delta);
		timerSleepStart.update(delta);
		timerAttack.update(delta);
		
		if (chasing && !entity.pos.isMoving()) {
			final Entity prey = getPreyEntity();
			if (prey == null) {
				// prey killed and cleaned from level
				stopChasing();
				return;
			}
			
			if (!isPreyInAttackRange(prey)) {
				//System.out.println("-upd STEP--");
				stepTowardsPrey(prey);
			}
		}
	}
	
	
	public boolean isSleeping()
	{
		return sleeping;
	}
	
	
	private void lookForTarget()
	{
		if (shouldSkipScan()) return; // not hungry right now
		
		//System.out.println("- Lookin for prey, r=" + getScanRadius());
		
		final Entity prey = entity.getLevel().getClosestEntity(entity, EntityType.PLAYER, getScanRadius());
		if (prey != null) {
			//System.out.println("-- Prey in sight: " + prey);
			//System.out.println("-- path would be: " + entity.getCoord() + "->" + prey.getCoord());
			
			// check if reachable without leaving room
			final List<Coord> noDoorPath = noDoorPf.findPath(entity.getCoord(), prey.getCoord());
			
			if (noDoorPath == null) {
				//System.out.println("-- Could not navigate to prey, aborting.");
				return; // cant reach, give up
			}
			
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
		//System.out.println("start chasing");
		
		preyId = prey.getEntityId();
		chasing = true;
		sleeping = false;
		
		// not good to take a nap while chasing
		timerSleepStart.pause();
		
		// follow this one prey
		timerFindPrey.pause();
		
		onStepFinished(); // go towards prey
	}
	
	
	private void stopChasing()
	{
		//System.out.println("stop chasing.");
		chasing = false;
		preyId = -1;
		timerSleepStart.restart();
		timerFindPrey.restart();
	}
	
	
	private List<Step> getPathToPrey(Entity prey)
	{
		if (!isPreyValid(prey)) return null;
		
		return entity.getPathFinder().findPathRelative(entity.getCoord(), prey.getCoord(), false, true);
	}
	
	
	private void stepTowardsPrey(Entity prey)
	{
		//System.out.println("stepTowardsPrey");
		if (!isPreyValid(prey)) {
			//System.out.println("prey dead?");
			return;
		}
		
		// if close enough
		if (isPreyInAttackRange(prey)) {
			//System.out.println("attack");
			attackPrey(prey);
			return;
		}
		
		final List<Step> preyPath = getPathToPrey(prey);
		
		if (preyPath == null || preyPath.size() > getPreyAbandonDistance()) {
			//System.out.println("no path");
			stopChasing();
			return;
		}
		
		//System.out.println("step to prey");
		entity.pos.addStep(preyPath.get(0));
	}
	
	
	private void attackPrey(Entity prey)
	{
		if (!isPreyInAttackRange(prey)) {
			//System.out.println("prey out of attack range, cant attack");
			return;
		}
		
		prey.receiveAttack(entity, getAttackStrength());
	}
	
	
	protected void setSleepTime(double secs)
	{
		timerSleepStart.setDuration(secs);
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
		return sleeping ? 1 + rand.nextInt(3) : 4 + rand.nextInt(4); // For override
	}
	
	
	@DefaultImpl
	protected int getPreyAbandonDistance()
	{
		return 5 + rand.nextInt(4); // For override
	}
	
	
	@DefaultImpl
	protected double getAttackDistance()
	{
		return 1.6;
	}
	
	
	@DefaultImpl
	protected int getAttackStrength()
	{
		return 1; // For override
	}
	
	
	@DefaultImpl
	protected boolean shouldSkipScan()
	{
		return false;
	}
	
	
	@DefaultImpl
	protected boolean shouldRandomlyAbandonPrey()
	{
		return false;
	}
}
