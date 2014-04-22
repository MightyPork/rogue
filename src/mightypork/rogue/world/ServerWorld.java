package mightypork.rogue.world;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mightypork.rogue.world.map.Level;
import mightypork.util.control.timing.Updateable;
import mightypork.util.ion.IonBundle;
import mightypork.util.ion.IonBundled;


/**
 * World on a server. To a server, all players and levels are equal.
 * 
 * @author MightyPork
 */
public class ServerWorld implements IonBundled, Updateable, WorldAccess {
	
	private final ArrayList<Level> levels = new ArrayList<>();
	
	private final Map<String, PlayerEntity> players = new HashMap<>();
	
	/** This seed can be used to re-create identical world. */
	private long seed;

	/** Next spawned entity ID */
	private long eid;
	
	
	@Override
	public void load(IonBundle in) throws IOException
	{
		seed = in.get("seed", 0L);
		eid = in.get("eid", 0L);
		in.loadSequence("levels", levels);
		in.loadMap("players", players);
	}
	
	
	@Override
	public void save(IonBundle out) throws IOException
	{
		out.put("seed", seed);
		out.put("eid", eid);
		out.putSequence("levels", levels);
		out.putMap("players", players);
	}
	
	
	public void addLevel(Level level)
	{
		levels.add(level);
	}
	
	public void addPlayer(String name, PlayerEntity player)
	{
		players.put(name, player);
	}

	public void removePlayer(String name)
	{
		players.remove(name);
	}
	
	@Override
	public void update(double delta)
	{
		// food meters and such
		for (final PlayerEntity pl : players.values()) {
			if(pl.isConnected()) pl.updateLogic(this, delta);
		}
		
		for (int level = 0; level < levels.size(); level++) {
			
			// more than 1 player can be on floor, update for all of them
			for (final PlayerEntity pl : players.values()) {
				if (pl.getPosition().floor == level) {
					levels.get(level).updateLogic(this, pl, delta);
				}
			}
			
		}
	}
	
	
	public void setSeed(long seed)
	{
		this.seed = seed;
	}
	
	
	public long getSeed()
	{
		return seed;
	}


	public long genEid()
	{
		return eid++;
	}
	
	@Override
	public boolean isServer()
	{
		return true;
	}
}
