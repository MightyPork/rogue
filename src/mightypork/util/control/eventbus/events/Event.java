package mightypork.util.control.eventbus.events;


import mightypork.util.control.eventbus.events.flags.DelayedEvent;
import mightypork.util.control.eventbus.events.flags.ImmediateEvent;
import mightypork.util.control.eventbus.events.flags.SingleReceiverEvent;
import mightypork.util.control.eventbus.events.flags.UnloggedEvent;


/**
 * <p>
 * Something that can be handled by HANDLER.
 * </p>
 * <p>
 * Can be annotated as {@link SingleReceiverEvent} to be delivered once only,
 * and {@link DelayedEvent} or {@link ImmediateEvent} to specify default sending
 * mode. When marked as {@link UnloggedEvent}, it will not appear in detailed
 * bus logging (useful for very frequent events, such as UpdateEvent).
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
