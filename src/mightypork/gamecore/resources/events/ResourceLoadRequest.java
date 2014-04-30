package mightypork.gamecore.resources.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.SingleReceiverEvent;
import mightypork.gamecore.resources.loading.Deferred;


/**
 * Request to load a deferred resource.
 * 
 * @author MightyPork
 */
@SingleReceiverEvent
public class ResourceLoadRequest extends BusEvent<ResourceLoadRequestListener> {
	
	private final Deferred resource;
	
	
	/**
	 * @param resource resource to load
	 */
	public ResourceLoadRequest(Deferred resource)
	{
		this.resource = resource;
	}
	
	
	@Override
	public void handleBy(ResourceLoadRequestListener handler)
	{
		handler.loadResource(resource);
	}
}
