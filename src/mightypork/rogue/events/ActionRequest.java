package mightypork.rogue.events;


import mightypork.gamecore.control.bus.events.Event;
import mightypork.gamecore.control.bus.events.types.SingleReceiverEvent;


/**
 * Request for a global sction to be done in the main loop.
 * 
 * @author MightyPork
 */
@SingleReceiverEvent
public class ActionRequest implements Event<ActionRequest.Listener> {
	
	private final RequestType type;
	
	
	public ActionRequest(RequestType request) {
		type = request;
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.requestAction(type);
	}
	
	public interface Listener {
		
		/**
		 * Perform the requested action
		 * 
		 * @param request
		 */
		void requestAction(RequestType request);
	}
	
	
	@Override
	public String toString()
	{
		return "ActionRequest(" + type + ")";
	}
	
	public static enum RequestType
	{
		FULLSCREEN, SCREENSHOT, SHUTDOWN;
	}
}
