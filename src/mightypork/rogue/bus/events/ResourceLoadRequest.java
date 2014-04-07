package mightypork.rogue.bus.events;


import mightypork.rogue.loading.DeferredResource;
import mightypork.utils.control.bus.events.Event;
import mightypork.utils.control.bus.events.types.SingleReceiverEvent;


/**
 * Request to schedule loading a deferred resource.
 * 
 * @author MightyPork
 */
@SingleReceiverEvent
public class ResourceLoadRequest implements Event<ResourceLoadRequest.Listener> {
	
	private final DeferredResource resource;
	
	
	public ResourceLoadRequest(DeferredResource resource) {
		this.resource = resource;
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.loadResource(resource);
	}
	
	public interface Listener {
		
		/**
		 * Load a resource
		 * 
		 * @param resource
		 */
		void loadResource(DeferredResource resource);
	}
}
