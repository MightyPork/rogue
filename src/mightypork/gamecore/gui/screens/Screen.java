package mightypork.gamecore.gui.screens;


import mightypork.gamecore.core.AppAccess;
import mightypork.gamecore.core.AppSubModule;
import mightypork.gamecore.gui.events.LayoutChangeEvent;
import mightypork.gamecore.gui.events.LayoutChangeListener;
import mightypork.gamecore.input.KeyBinder;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.KeyStroke.Edge;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.Renderable;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;


/**
 * Screen class.
 * 
 * @author MightyPork
 */
public abstract class Screen extends AppSubModule implements Renderable, RectBound, KeyBinder, LayoutChangeListener {
	
	private final KeyBindingPool keybindings = new KeyBindingPool();
	
	private volatile boolean active;
	private volatile boolean needSetupViewport = false;
	
	
	/**
	 * @param app app access
	 */
	public Screen(AppAccess app)
	{
		super(app);
		
		// disable events initially
		setListening(false);
		
		addChildClient(keybindings);
	}
	
	
	private void fireLayoutChangeEvent()
	{
		getEventBus().sendDirectToChildren(this, new LayoutChangeEvent());
	}
	
	
	@Override
	public final void bindKey(KeyStroke stroke, Edge edge, Runnable task)
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
		return getDisplay().getRect();
	}
	
	
	@Override
	public void render()
	{
		if (!isActive()) return;
		
		if (needSetupViewport) {
			Render.setupOrtho(DisplaySystem.getSize());
		}
		
		Render.pushState();
		
		renderScreen();
		
		Render.popState();
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
	
	
	/**
	 * Render screen contents (context is ready for 2D rendering)
	 */
	protected abstract void renderScreen();
	
}
