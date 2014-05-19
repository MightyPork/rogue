package mightypork.gamecore.resources;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.SingleReceiverEvent;


/**
 * Request to load a deferred resource.
 * 
 * @author MightyPork
 */
@SingleReceiverEvent
public class ResourceLoadRequest extends BusEvent<ResourceLoader> {
	
	private final DeferredResource resource;
	
	
	/**
	 * @param resource resource to load
	 */
	public ResourceLoadRequest(DeferredResource resource)
	{
		this.resource = resource;
	}
	
	
	@Override
	public void handleBy(ResourceLoader handler)
	{
		handler.loadResource(resource);
	}
}
