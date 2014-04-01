package mightypork.rogue.bus;

import mightypork.rogue.AppAccess;
import mightypork.rogue.AppAdapter;


/**
 * Simplest event bus client with app access
 * 
 * @author MightyPork
 */
public class SimpleBusClient extends AppAdapter {
	
	/**
	 * @param app
	 *            app access
	 */
	public SimpleBusClient(AppAccess app) {
		super(app);
		
		bus().subscribe(this);
	}
}
