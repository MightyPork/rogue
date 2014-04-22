package mightypork.rogue.world;

import mightypork.rogue.world.map.Level;
import mightypork.util.control.timing.Updateable;


/**
 * Abstraction of client-server connection from the client's view
 * 
 * @author MightyPork
 */
public abstract class ClientWorldAccess implements Updateable {
		
	public abstract Level getLevel();
	
}
