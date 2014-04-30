package mightypork.rogue.world.entity.entities;


import java.io.IOException;
import java.util.List;

import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Step;
import mightypork.gamecore.util.math.algo.pathfinding.PathFinder;
import mightypork.gamecore.util.math.algo.pathfinding.PathFindingContext;
import mightypork.gamecore.util.math.algo.pathfinding.PathFindingContextProxy;
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
			System.out.println("Mob looks around.");
			lookForTarget();
		}
	};
	
	private final AiTimer timerSleepStart = new AiTimer(10) {
		
		@Override
		public void run()
		{
			System.out.println("Mob going to sleep");
			sleeping = true;
		}
	};
	
	private PathFindingContext pathfcNoDoor;
	
	private int targetId = -1;
	
	
	public MonsterAi(final Entity entity)
	{
		super(entity);
		
		pathfcNoDoor = new PathFindingContextProxy(entity.getPathfindingContext()) {
			
			@Override
			public boolean isAccessible(Coord pos)
			{
				final Tile t = entity.getLevel().getTile(pos);
				if (t.isDoor()) return false;
				
				return super.isAccessible(pos);
			}
			
		};
	}
	
	
	private void lookForTarget()
	{
		if (rand.nextInt(3) == 0) return; // not hungry right now
		
		final Entity prey = entity.getLevel().getClosestEntity(entity, EntityType.PLAYER, getScanRadius());
		if (prey != null) {
			
			// check if reachable without leaving room
			final List<Coord> noDoorPath = PathFinder.findPath(pathfcNoDoor, entity.getCoord(), prey.getCoord());
			
			if (noDoorPath == null) { return; // cant reach, give up
			}
			
			startChasing(prey);
		}
	}
	
	
	private void startChasing(Entity prey)
	{
		// TODO if too close, attack directly
		
		targetId = prey.getEntityId();
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
		chasing = false;
		targetId = -1;
		timerSleepStart.restart();
		timerFindPrey.restart();
	}
	
	
	private List<Step> getPathToPrey(Entity prey)
	{
		return PathFinder.findPathRelative(entity.getPathfindingContext(), entity.getCoord(), prey.getCoord());
	}
	
	
	protected double getScanRadius()
	{
		return sleeping ? 1 + rand.nextInt(3) : 4 + rand.nextInt(4); // For override
	}
	
	
	protected int getPreyAbandonDistance()
	{
		return 5 + rand.nextInt(4); // For override
	}
	
	
	@DefaultImpl
	@Override
	public void onStepFinished()
	{
		if (chasing) {
			final Entity prey = entity.getLevel().getEntity(targetId);
			if (prey == null || prey.isDead()) {
				stopChasing();
				return;
			}
			
			
			stepTowardsPrey(prey);
			
			// TODO if close enough, attack.
		}
	}
	
	
	private void stepTowardsPrey(Entity prey)
	{
		// if close enough
		final Coord preyPos = prey.getCoord();
		if (preyPos.dist(entity.getCoord()) <= 1.5) {
			attackPrey(prey);
			return;
		}
		
		final List<Step> preyPath = getPathToPrey(prey);
		
		if (preyPath.size() > getPreyAbandonDistance()) {
			stopChasing();
			return;
		}
		
		entity.pos.addStep(preyPath.get(0));
	}
	
	
	private void attackPrey(Entity prey)
	{
		prey.receiveAttack(entity, getAttackStrength());
	}
	
	
	protected int getAttackStrength()
	{
		return 1; // For override
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
	}
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		bundle.loadBundled("tscan", timerFindPrey);
		bundle.loadBundled("tsleep", timerSleepStart);
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
	}
	
}
