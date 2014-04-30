package mightypork.gamecore.app;


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
