package mightypork.gamecore.resources.loading;


import mightypork.gamecore.resources.DeferredResource;
import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.SingleReceiverEvent;


/**
 * Request to load a deferred resource.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@SingleReceiverEvent
public class ResourceLoadRequest extends BusEvent<ResourceLoader> {
	
	private final DeferredResource resource;
	
	
	/**
	 * @param resource resource to load
	 */
	public ResourceLoadRequest(DeferredResource resource) {
		this.resource = resource;
	}
	
	
	@Override
	public void handleBy(ResourceLoader handler)
	{
		handler.loadResource(resource);
	}
}
