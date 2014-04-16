package mightypork.gamecore.control.events;


import mightypork.gamecore.loading.Deferred;
import mightypork.util.control.eventbus.events.Event;
import mightypork.util.control.eventbus.events.flags.SingleReceiverEvent;


/**
 * Request to load a deferred resource.
 * 
 * @author MightyPork
 */
@SingleReceiverEvent
public class ResourceLoadRequest implements Event<ResourceLoadRequest.Listener> {
	
	private final Deferred resource;
	
	
	/**
	 * @param resource resource to load
	 */
	public ResourceLoadRequest(Deferred resource) {
		this.resource = resource;
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.loadResource(resource);
	}
	
	/**
	 * {@link ResourceLoadRequest} listener
	 * 
	 * @author MightyPork
	 */
	public interface Listener {
		
		/**
		 * Load a resource
		 * 
		 * @param resource
		 */
		void loadResource(Deferred resource);
	}
}
