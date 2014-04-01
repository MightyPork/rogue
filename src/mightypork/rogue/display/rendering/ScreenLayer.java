package mightypork.rogue.display.rendering;


import java.util.Collection;
import java.util.LinkedHashSet;

import mightypork.rogue.AppAdapter;
import mightypork.rogue.bus.UpdateReceiver;
import mightypork.rogue.display.Screen;
import mightypork.rogue.input.KeyBinder;
import mightypork.rogue.input.KeyBindingPool;
import mightypork.rogue.input.KeyStroke;
import mightypork.utils.patterns.subscription.clients.DelegatingClient;
import mightypork.utils.time.Updateable;


/**
 * Screen layer<br>
 * Plugged into a screen as a child bus subscriber, receiving update and render
 * calls directly from the screen.
 * 
 * @author MightyPork
 */
public abstract class ScreenLayer extends AppAdapter implements KeyBinder, DelegatingClient, Renderable, Updateable {
	
	private Screen screen;

	private KeyBindingPool keybindings;

	private Collection<Object> layerChildClients = new LinkedHashSet<Object>();
	
	
	public ScreenLayer(Screen screen) {
		super(screen); // screen as AppAccess
		
		this.screen = screen;
		
		layerChildClients.add(keybindings = new KeyBindingPool());
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
	
	
	@Override
	public abstract void render();
	
	
	@Override
	public abstract void update(double delta);
	
	
	protected Screen screen()
	{
		return screen;
	}


	@Override
	public Collection<Object> getChildClients()
	{
		return layerChildClients;
	}


	@Override
	public final boolean doesDelegate()
	{
		return true;
	}
	
}
