package mightypork.gamecore.control.bus.events;


import mightypork.gamecore.control.bus.events.types.DelayedEvent;
import mightypork.gamecore.control.bus.events.types.ImmediateEvent;
import mightypork.gamecore.control.bus.events.types.SingleReceiverEvent;


/**
 * <p>
 * Something that can be handled by HANDLER.
 * </p>
 * <p>
 * Can be annotated as {@link SingleReceiverEvent} to be delivered once only,
 * and {@link DelayedEvent} or {@link ImmediateEvent} to specify default sending
 * mode.
 * </p>
 * <p>
 * Default sending mode (if not changed by annotations) is <i>queued</i> with
 * zero delay.
 * </p>
 * 
 * @author MightyPork
 * @param <HANDLER> handler type
 */
public interface Event<HANDLER> {
	
	/**
	 * Ask handler to handle this message.
	 * 
	 * @param handler handler instance
	 */
	public void handleBy(HANDLER handler);
}
