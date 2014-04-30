package mightypork.gamecore.gui.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.gui.screens.impl.CrossfadeOverlay;


/**
 * @author MightyPork
 */
public class CrossfadeRequest extends BusEvent<CrossfadeOverlay> {
	
	private final String screen;
	private final boolean fromDark;
	
	/**
	 * @param screen screen key to show. Null = exit the app.
	 * @param fromDark true to fade from full black (ie. start of the game)
	 */
	public CrossfadeRequest(String screen, boolean fromDark)
	{
		super();
		this.screen = screen;
		this.fromDark = fromDark;
	}
	
	/**
	 * @param screen screen key to show. Null = exit the app.
	 */
	public CrossfadeRequest(String screen)
	{
		super();
		this.screen = screen;
		this.fromDark = false;
	}
	
	
	@Override
	public void handleBy(CrossfadeOverlay handler)
	{
		handler.goToScreen(screen, fromDark);
	}
	
}
