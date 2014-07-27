package mightypork.gamecore.gui.screens;


import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.Renderable;
import mightypork.gamecore.gui.events.LayoutChangeEvent;
import mightypork.gamecore.gui.events.LayoutChangeListener;
import mightypork.gamecore.input.KeyBinder;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Trigger;
import mightypork.utils.annotations.Stub;
import mightypork.utils.eventbus.clients.BusNode;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.RectBound;


/**
 * Screen class.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class Screen extends BusNode implements Renderable, RectBound, KeyBinder, LayoutChangeListener {
	
	private final KeyBindingPool keybindings = new KeyBindingPool();
	
	private volatile boolean active;
	private volatile boolean needSetupViewport = false;
	
	
	public Screen() {
		
		// disable events initially
		setListening(false);
		
		addChildClient(keybindings);
	}
	
	
	private void fireLayoutChangeEvent()
	{
		App.bus().sendDirectToChildren(this, new LayoutChangeEvent());
	}
	
	
	@Override
	public final void bindKey(KeyStroke stroke, Trigger edge, Runnable task)
	{
		keybindings.bindKey(stroke, edge, task);
	}
	
	
	@Override
	public final void unbindKey(KeyStroke stroke)
	{
		keybindings.unbindKey(stroke);
	}
	
	
	/**
	 * Prepare for being shown
	 * 
	 * @param shown true to show, false to hide
	 */
	public final void setActive(boolean shown)
	{
		if (shown) {
			active = true;
			needSetupViewport = true;
			
			fireLayoutChangeEvent();
			onScreenEnter();
			
			// enable events
			setListening(true);
			
		} else {
			onScreenLeave();
			
			active = false;
			
			// disable events
			setListening(false);
		}
	}
	
	
	/**
	 * @return true if screen is the current screen
	 */
	public final boolean isActive()
	{
		return active;
	}
	
	
	@Override
	public void onLayoutChanged()
	{
		if (!isActive()) return;
		
		needSetupViewport = true;
	}
	
	
	@Override
	public final Rect getRect()
	{
		return App.gfx().getRect();
	}
	
	
	@Override
	public void render()
	{
		if (!isActive()) return;
		
		if (needSetupViewport) {
			App.gfx().setupProjection();
		}
		
		App.gfx().pushState();
		
		renderScreen();
		
		App.gfx().popState();
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
	
	
	/**
	 * Render screen contents (context is ready for 2D rendering)
	 */
	protected abstract void renderScreen();
	
}
