package mightypork.gamecore.render.events;


import mightypork.dynmath.vect.Vect;
import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.NonConsumableEvent;
import mightypork.gamecore.eventbus.event_flags.NotLoggedEvent;


/**
 * Screen resolution or mode was changed
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@NonConsumableEvent
@NotLoggedEvent
public class ViewportChangeEvent extends BusEvent<ViewportChangeListener> {
	
	private final Vect screenSize;
	
	
	/**
	 * @param size new screen size
	 */
	public ViewportChangeEvent(Vect size)
	{
		this.screenSize = size;
	}
	
	
	/**
	 * @return new screen size
	 */
	public Vect getScreenSize()
	{
		return screenSize;
	}
	
	
	@Override
	public void handleBy(ViewportChangeListener handler)
	{
		handler.onViewportChanged(this);
	}
}
