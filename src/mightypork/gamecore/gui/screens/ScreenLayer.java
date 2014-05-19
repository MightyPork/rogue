package mightypork.gamecore.gui.screens;


import mightypork.gamecore.util.annot.DefaultImpl;


/**
 * Screen display layer
 * 
 * @author MightyPork
 */
public abstract class ScreenLayer extends Overlay {
	
	private final Screen screen;
	
	
	/**
	 * @param screen parent screen
	 */
	public ScreenLayer(Screen screen)
	{
		super(screen); // screen as AppAccess
		
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
	@DefaultImpl
	protected void onScreenEnter()
	{
	}
	
	
	/**
	 * Called when the screen is no longer active
	 */
	@DefaultImpl
	protected void onScreenLeave()
	{
	}
	
	
	@Override
	public boolean isListening()
	{
		return (isVisible() || isEnabled()) && super.isListening();
	}
}
