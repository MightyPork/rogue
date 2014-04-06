package mightypork.rogue.bus.events;


import mightypork.utils.control.bus.events.Event;
import mightypork.utils.control.bus.events.types.QueuedEvent;
import mightypork.utils.control.bus.events.types.SingularEvent;


/**
 * Request for a global sction to be done in the main loop.
 * 
 * @author MightyPork
 */
@SingularEvent
@QueuedEvent
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
	
	public static enum RequestType
	{
		FULLSCREEN, SCREENSHOT, SHUTDOWN;
	}
}
