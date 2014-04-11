package mightypork.gamecore.control.bus.events;


import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecView;


/**
 * Screen resolution or mode was changed
 * 
 * @author MightyPork
 */
public class ScreenChangeEvent implements Event<ScreenChangeEvent.Listener> {
	
	private final boolean fullscreen;
	private final Vec screenSize;
	private final boolean fsChanged;
	
	
	/**
	 * @param fsChanged fullscreen change triggered the event
	 * @param fullscreen is now fullscreen
	 * @param size new screen size
	 */
	public ScreenChangeEvent(boolean fsChanged, boolean fullscreen, Vec size) {
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
	public VecView getScreenSize()
	{
		return screenSize.view();
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.receive(this);
	}
	
	/**
	 * {@link ScreenChangeEvent} listener
	 * 
	 * @author MightyPork
	 */
	public interface Listener {
		
		/**
		 * Handle event
		 * 
		 * @param event
		 */
		void receive(ScreenChangeEvent event);
	}
}
