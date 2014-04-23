package mightypork.gamecore.control.events.requests;


/**
 * {@link MainLoopRequest} listener
 * 
 * @author MightyPork
 */
public interface MainLoopRequestListener {
	
	/**
	 * Perform the requested action
	 * 
	 * @param request
	 */
	void queueTask(Runnable request);
}
