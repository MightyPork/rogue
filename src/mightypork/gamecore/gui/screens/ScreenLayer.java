package mightypork.gamecore.gui.screens;


import mightypork.utils.annotations.Stub;


/**
 * Screen display layer
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class ScreenLayer extends Overlay {
	
	private final Screen screen;
	
	
	/**
	 * @param screen parent screen
	 */
	public ScreenLayer(Screen screen) {
		this.screen = screen;
	}
	
	
	/**
	 * @return parent screen instance
	 */
	protected final Screen getScreen()
	{
		return screen;
	}
	
	
	/**
	 * Called when the screen becomes active
	 */
	@Stub
	protected void onScreenEnter()
	{
	}
	
	
	/**
	 * Called when the screen is no longer active
	 */
	@Stub
	protected void onScreenLeave()
	{
	}
}
