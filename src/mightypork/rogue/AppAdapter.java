package mightypork.rogue;


import mightypork.rogue.display.DisplaySystem;
import mightypork.rogue.input.InputSystem;
import mightypork.rogue.sounds.SoundSystem;
import mightypork.rogue.textures.TextureRegistry;
import mightypork.utils.control.bus.EventBus;


/**
 * App access adapter
 * 
 * @author MightyPork
 */
public class AppAdapter implements AppAccess {
	
	private AppAccess app;
	
	
	public AppAdapter(AppAccess app) {
		if (app == null) throw new NullPointerException("AppAccess instance cannot be null.");
		
		this.app = app;
	}
	
	
	@Override
	public final SoundSystem snd()
	{
		return app.snd();
	}
	
	
	@Override
	public final InputSystem input()
	{
		return app.input();
	}
	
	
	@Override
	public final DisplaySystem disp()
	{
		return app.disp();
	}
	
	
	@Override
	public final EventBus bus()
	{
		return app.bus();
	}
	
	
	@Override
	public void shutdown()
	{
		app.shutdown();
	}
	
	
	@Override
	public TextureRegistry tx()
	{
		return app.tx();
	}
	
}
