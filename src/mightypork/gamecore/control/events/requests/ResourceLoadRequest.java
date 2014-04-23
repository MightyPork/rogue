package mightypork.gamecore.control.events.requests;


import mightypork.gamecore.loading.Deferred;
import mightypork.util.control.eventbus.BusEvent;
import mightypork.util.control.eventbus.event_flags.SingleReceiverEvent;


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
