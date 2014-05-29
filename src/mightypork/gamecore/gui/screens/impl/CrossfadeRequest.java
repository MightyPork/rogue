package mightypork.gamecore.gui.screens.impl;


import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.SingleReceiverEvent;


/**
 * @author Ondřej Hruška (MightyPork)
 */
@SingleReceiverEvent
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
