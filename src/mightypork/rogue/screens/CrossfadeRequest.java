package mightypork.rogue.screens;


import mightypork.util.control.eventbus.BusEvent;


/**
 * @author MightyPork
 */
public class CrossfadeRequest extends BusEvent<CrossfadeRequest.Listener> {
	
	private final String screen;
	
	
	/**
	 * @param screen screen key to show. Null = exit the app.
	 */
	public CrossfadeRequest(String screen)
	{
		super();
		this.screen = screen;
	}
	
	public interface Listener {
		
		void goToScreen(String screen);
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.goToScreen(screen);
	}
	
}
