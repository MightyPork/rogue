package mightypork.gamecore.gui.events;


import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.NonConsumableEvent;
import mightypork.utils.eventbus.events.flags.NotLoggedEvent;
import mightypork.utils.math.constraints.vect.Vect;


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
	public ViewportChangeEvent(Vect size) {
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
