package mightypork.gamecore.control;


import mightypork.gamecore.audio.SoundSystem;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.util.control.eventbus.EventBus;


/**
 * App access adapter (defualt {@link AppAccess} implementation)
 * 
 * @author MightyPork
 */
public class AppAdapter implements AppAccess {
	
	private final AppAccess app;
	
	
	/**
	 * @param app app access
	 */
	public AppAdapter(AppAccess app)
	{
		if (app == null) throw new NullPointerException("AppAccess instance cannot be null.");
		
		this.app = app;
	}
	
	
	@Override
	public final SoundSystem getSoundSystem()
	{
		return app.getSoundSystem();
	}
	
	
	@Override
	public final InputSystem getInput()
	{
		return app.getInput();
	}
	
	
	@Override
	public final DisplaySystem getDisplay()
	{
		return app.getDisplay();
	}
	
	
	@Override
	public final EventBus getEventBus()
	{
		return app.getEventBus();
	}
	
	
	@Override
	public final void shutdown()
	{
		app.shutdown();
	}
	
}
