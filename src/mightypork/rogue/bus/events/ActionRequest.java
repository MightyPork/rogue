package mightypork.rogue.bus.events;


import mightypork.utils.control.bus.Event;


/**
 * Request for action that should be performed in the main thread.
 * 
 * @author MightyPork
 */
public class ActionRequest implements Event<ActionRequest.Listener> {
	
	private RequestType type;
	
	
	public ActionRequest(RequestType request) {
		type = request;
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.requestAction(type);
	}
	
	public interface Listener {
		
		public void requestAction(RequestType request);
	}
	
}
