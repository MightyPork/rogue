package mightypork.rogue.world;


import java.io.IOException;
import java.util.ArrayList;

import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.Level;
import mightypork.util.constraints.rect.proxy.RectBound;
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
	
	final PlayerInfo player = new PlayerInfo();
	Entity playerEntity;
	
	private final PlayerControl control = new PlayerControl(this);
	
	private long seed;	// world seed
	private int eid; // next entity ID
	
	
	@Override
	public void load(IonBundle in) throws IOException
	{
		seed = in.get("seed", 0L);
		eid = in.get("next_eid", 0);
		in.loadSequence("levels", levels);
		in.loadBundled("player", player);
	}
	
	
	@Override
	public void save(IonBundle out) throws IOException
	{
		out.put("seed", seed);
		out.put("next_eid", eid);
		out.putSequence("levels", levels);
		out.putBundled("player", player);
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
		if (player.isInitialized()) {
			throw new RuntimeException("Player already created.");
		}
		
		// make entity
		final int playerEid = getNewEID();
		
		playerEntity = Entities.PLAYER.createEntity(playerEid, new WorldPos(x, y));
		
		player.setLevel(level);
		player.setEID(playerEid);
		
		levels.get(level).addEntity(playerEntity);
	}
	
	
	/**
	 * Draw on screen
	 * 
	 * @param viewport rendering area on screen
	 * @param xTiles Desired nr of tiles horizontally
	 * @param yTiles Desired nr of tiles vertically
	 * @param minSize minimum tile size
	 */
	public void render(RectBound viewport, final int xTiles, final int yTiles, final int minSize)
	{
		getCurrentLevel().render(playerEntity.getPosition(), viewport, xTiles, yTiles, minSize);
	}
	
	
	public Level getCurrentLevel()
	{
		return levels.get(player.getLevel());
	}
	
	
	public PlayerControl getPlayerControl()
	{
		return control;
	}
}
