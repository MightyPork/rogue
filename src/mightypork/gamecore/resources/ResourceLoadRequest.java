package mightypork.gamecore.resources;


import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.SingleReceiverEvent;


/**
 * Request to load a deferred resource.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@SingleReceiverEvent
public class ResourceLoadRequest extends BusEvent<ResourceLoader> {
	
	private final LazyResource resource;
	
	
	/**
	 * @param resource resource to load
	 */
	public ResourceLoadRequest(LazyResource resource)
	{
		this.resource = resource;
	}
	
	
	@Override
	public void handleBy(ResourceLoader handler)
	{
		handler.loadResource(resource);
	}
}
