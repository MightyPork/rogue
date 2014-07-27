package mightypork.gamecore.core.events;


import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.EventBus;
import mightypork.utils.logging.Log;


/**
 * Shutdown event.<br>
 * This event is dispatched when the <code>App.shutdown()</code> method is
 * called. If no client consumes it, the shutdown will immediately follow.<br>
 * This is a way to allow clients to abort the shutdown (ie. ask user to save
 * game). After the game is saved, the <code>App.shutdown()</code> method can be
 * called again.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class ShutdownEvent extends BusEvent<ShutdownListener> {
	
	private Runnable shutdownTask;
	
	
	public ShutdownEvent(Runnable doShutdown) {
		this.shutdownTask = doShutdown;
	}
	
	
	@Override
	protected void handleBy(ShutdownListener handler)
	{
		handler.onShutdown(this);
	}
	
	
	@Override
	public void onDispatchComplete(EventBus bus)
	{
		if (!isConsumed()) {
			Log.i("Shutting down...");
			shutdownTask.run();
		} else {
			Log.i("Shutdown aborted.");
		}
	}
	
}
