package mightypork.gamecore.control.events.gui;


import mightypork.util.constraints.vect.Vect;
import mightypork.util.control.eventbus.BusEvent;
import mightypork.util.control.eventbus.events.flags.NonConsumableEvent;


/**
 * Screen resolution or mode was changed
 * 
 * @author MightyPork
 */
@NonConsumableEvent
public class ViewportChangeEvent extends BusEvent<ViewportChangeListener> {
	
	private final boolean fullscreen;
	private final Vect screenSize;
	private final boolean fsChanged;
	
	
	/**
	 * @param fsChanged fullscreen change triggered the event
	 * @param fullscreen is now fullscreen
	 * @param size new screen size
	 */
	public ViewportChangeEvent(boolean fsChanged, boolean fullscreen, Vect size) {
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
