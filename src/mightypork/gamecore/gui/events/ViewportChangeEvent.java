package mightypork.gamecore.gui.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.NonConsumableEvent;
import mightypork.gamecore.eventbus.event_flags.NotLoggedEvent;
import mightypork.gamecore.util.math.constraints.vect.Vect;


/**
 * Screen resolution or mode was changed
 * 
 * @author MightyPork
 */
@NonConsumableEvent
@NotLoggedEvent
public class ViewportChangeEvent extends BusEvent<ViewportChangeListener> {
	
	private final boolean fullscreen;
	private final Vect screenSize;
	private final boolean fsChanged;
	
	
	/**
	 * @param fsChanged fullscreen change triggered the event
	 * @param fullscreen is now fullscreen
	 * @param size new screen size
	 */
	public ViewportChangeEvent(boolean fsChanged, boolean fullscreen, Vect size)
	{
		this.fullscreen = fullscreen;
		this.screenSize = size;
		this.fsChanged = fsChanged;
	}
	
	
	/**
	 * @return true if screen is now fullscreen
	 */
	public boolean isFullscreen()
	{
		return fullscreen;
	}
	
	
	/**
	 * @return true if event was triggered by fullscreen toggle
	 */
	public boolean fullscreenChanged()
	{
		return fsChanged;
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
