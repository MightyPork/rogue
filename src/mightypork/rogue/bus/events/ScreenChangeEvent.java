package mightypork.rogue.bus.events;


import mightypork.utils.control.bus.events.Event;
import mightypork.utils.math.coord.Coord;


/**
 * Screen resolution or mode was changed
 * 
 * @author MightyPork
 */
public class ScreenChangeEvent implements Event<ScreenChangeEvent.Listener> {
	
	private final boolean fullscreen;
	private final Coord screenSize;
	private final boolean fsChanged;
	
	
	public ScreenChangeEvent(boolean fsChanged, boolean fullscreen, Coord size) {
		this.fullscreen = fullscreen;
		this.screenSize = size;
		this.fsChanged = fsChanged;
	}
	
	
	public boolean isFullscreen()
	{
		return fullscreen;
	}
	
	
	public boolean fullscreenChanged()
	{
		return fsChanged;
	}
	
	
	public Coord getScreenSize()
	{
		return screenSize;
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.receive(this);
	}
	
	public interface Listener {
		
		void receive(ScreenChangeEvent event);
	}
}
