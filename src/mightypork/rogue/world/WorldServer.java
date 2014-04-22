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
 * World server. To a server, all players and levels are equal.
 * 
 * @author MightyPork
 */
public class WorldServer implements IonBundled, Updateable {
	
	private final ArrayList<Level> levels = new ArrayList<>();
	
	private final Map<String, Player> players = new HashMap<>();
	
	/** This seed can be used to re-create identical world. */
	private long seed;
	
	
	@Override
	public void load(IonBundle in) throws IOException
	{
		seed = in.get("seed", 0L);
		in.loadSequence("levels", levels);
		in.loadMap("players", players);
	}
	
	
	@Override
	public void save(IonBundle out) throws IOException
	{
		out.put("seed", seed);
		out.putSequence("levels", levels);
		out.putMap("players", players);
	}
	
	
	public void addLevel(Level level)
	{
		levels.add(level);
	}
	
	public void addPlayer(String name, Player player)
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
		for (final Player pl : players.values()) {
			pl.updateLogic(delta);
		}
		
		for (int level = 0; level < levels.size(); level++) {
			
			// more than 1 player can be on floor, update for all of them
			for (final Player pl : players.values()) {
				if (pl.getPosition().floor == level) {
					levels.get(level).updateLogic(pl, delta);
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
}
