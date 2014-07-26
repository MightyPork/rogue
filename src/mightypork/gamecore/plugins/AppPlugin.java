package mightypork.gamecore.plugins;


import mightypork.gamecore.core.App;
import mightypork.utils.annotations.Stub;
import mightypork.utils.eventbus.clients.BusNode;


/**
 * App plugin. Plugins are an easy way to extend app functionality.<br>
 * Typically, a plugin waits for trigger event(s) and performs some action upon
 * receiving them.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class AppPlugin extends BusNode {
	
	/**
	 * Initialize the plugin for the given App.<br>
	 * The plugin is already attached to the event bus.
	 * 
	 * @param app
	 */
	@Stub
	public void initialize(App app)
	{
	}
}
