package mightypork.gamecore.gui.screens;


import java.util.Collection;
import java.util.LinkedHashSet;

import mightypork.gamecore.control.AppSubModule;
import mightypork.gamecore.gui.Hideable;
import mightypork.gamecore.gui.components.Renderable;
import mightypork.gamecore.gui.components.layout.ConstraintLayout;
import mightypork.gamecore.input.KeyBinder;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.util.annotations.DefaultImpl;
import mightypork.util.constraints.vect.Vect;
import mightypork.util.control.timing.Updateable;


/**
 * Screen display layer
 * 
 * @author MightyPork
 */
public abstract class ScreenLayer extends AppSubModule implements Updateable, Comparable<ScreenLayer>, Renderable, KeyBinder, Hideable {
	
	private boolean visible = true;
	
	private final KeyBindingPool keybindings = new KeyBindingPool();
	
	/** Root layout, rendered and attached to the event bus */
	protected final ConstraintLayout root;
	
	protected final Vect mouse;
	
	private final Screen screen;
	
	/** Extra rendered items (outside root) */
	protected final Collection<Renderable> rendered = new LinkedHashSet<>();
	
	/** Extra updated items (outside root - those can just implement Updateable) */
	protected final Collection<Updateable> updated = new LinkedHashSet<>();
	
	
	/**
	 * @param screen parent screen
	 */
	public ScreenLayer(Screen screen) {
		super(screen); // screen as AppAccess
		
		this.screen = screen;
		
		this.mouse = getInput().getMousePos();
		
		this.root = new ConstraintLayout(screen, screen);
		addChildClient(root);
		addChildClient(keybindings);
		
		rendered.add(root);
		
		// root is on the bus, all attached components
		// will receive events (such as update)
	}
	
	
	@Override
	public final void bindKeyStroke(KeyStroke stroke, Runnable task)
	{
		keybindings.bindKeyStroke(stroke, task);
	}
	
	
	@Override
	public final void unbindKeyStroke(KeyStroke stroke)
	{
		keybindings.unbindKeyStroke(stroke);
	}
	
	
	/**
	 * @return parent screen instance
	 */
	protected final Screen getScreen()
	{
		return screen;
	}
	
	
	@Override
	public final boolean isVisible()
	{
		return visible;
	}
	
	
	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	
	@Override
	public final int compareTo(ScreenLayer o)
	{
		return getPriority() - o.getPriority();
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
	 * Update GUI for new screen size
	 * 
	 * @param size screen size
	 */
	@DefaultImpl
	protected void onSizeChanged(Vect size)
	{
	}
	
	
	/**
	 * @return higher = on top.
	 */
	@DefaultImpl
	public abstract int getPriority();
	
	
	@Override
	public final void render()
	{
		if (!visible) return;
		
		renderLayer();
	}
	
	
	/**
	 * Render layer contents.
	 */
	protected void renderLayer()
	{
		// render renderables (including root layout)
		for (Renderable r : rendered)
			r.render();
	}
	
	
	@Override
	public void update(double delta)
	{
		// update updateables
		for (Updateable u : updated)
			u.update(delta);
	}
	
}
