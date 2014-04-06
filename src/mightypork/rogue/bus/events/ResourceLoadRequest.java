package mightypork.rogue.bus.events;


import mightypork.rogue.loading.Deferred;
import mightypork.utils.control.bus.Event;
import mightypork.utils.control.bus.SingularEvent;


/**
 * Request to schedule loading a deferred resource.
 * 
 * @author MightyPork
 */
public class ResourceLoadRequest implements Event<ResourceLoadRequest.Listener>, SingularEvent {
	
	private final Deferred resource;
	
	
	public ResourceLoadRequest(Deferred resource) {
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
		public void loadResource(Deferred resource);
	}
}
