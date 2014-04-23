package mightypork.rogue.world;


import java.io.IOException;
import java.util.ArrayList;

import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.Level;
import mightypork.util.control.timing.Updateable;
import mightypork.util.ion.IonBundle;
import mightypork.util.ion.IonBundled;


/**
 * World on a server. To a server, all players and levels are equal.
 * 
 * @author MightyPork
 */
public class World implements IonBundled, Updateable {
	
	private final ArrayList<Level> levels = new ArrayList<>();
	
	private final PlayerInfo playerInfo = new PlayerInfo();
	private Entity playerEntity;
	
	private final PlayerControl control = new PlayerControl(this);
	
	private long seed;	// world seed
	private int eid; // next entity ID
	
	
	@Override
	public void load(IonBundle in) throws IOException
	{
		seed = in.get("seed", 0L);
		eid = in.get("next_eid", 0);
		in.loadSequence("levels", levels);
		in.loadBundled("player", playerInfo);
		
		playerEntity = levels.get(playerInfo.getLevel()).getEntity(playerInfo.getEID());
	}
	
	
	@Override
	public void save(IonBundle out) throws IOException
	{
		out.put("seed", seed);
		out.put("next_eid", eid);
		out.putSequence("levels", levels);
		out.putBundled("player", playerInfo);
	}
	
	
	public void addLevel(Level level)
	{
		levels.add(level);
	}
	
	
	@Override
	public void update(double delta)
	{
		getCurrentLevel().update(this, delta);
	}
	
	
	public void setSeed(long seed)
	{
		this.seed = seed;
	}
	
	
	public long getSeed()
	{
		return seed;
	}
	
	
	/**
	 * @return new entity ID
	 */
	public int getNewEID()
	{
		return eid++;
	}
	
	
	public void createPlayer(int x, int y, int level)
	{
		if (playerInfo.isInitialized()) {
			throw new RuntimeException("Player already created.");
		}
		
		// make entity
		final int playerEid = getNewEID();
		
		playerEntity = Entities.PLAYER.createEntity(playerEid, new WorldPos(x, y));
		
		playerInfo.setLevel(level);
		playerInfo.setEID(playerEid);
		
		levels.get(level).addEntity(playerEntity);
	}
	
	
	public Level getCurrentLevel()
	{
		return levels.get(playerInfo.getLevel());
	}
	
	
	public PlayerControl getPlayerControl()
	{
		return control;
	}


	public Entity getPlayerEntity()
	{
		return playerEntity;
	}
}
