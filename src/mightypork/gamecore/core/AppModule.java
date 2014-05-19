package mightypork.gamecore.core;


import mightypork.gamecore.eventbus.clients.RootBusNode;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.resources.audio.SoundSystem;


/**
 * App event bus client, to be used for subsystems, screens and anything that
 * needs access to the eventbus and other systems; Attached directly to bus.
 * 
 * @author MightyPork
 */
public abstract class AppModule extends RootBusNode implements AppAccess {
	
	private final AppAccess app;
	
	
	/**
	 * Create a module
	 * 
	 * @param app access to app systems
	 */
	public AppModule(AppAccess app)
	{
		super(app);
		
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
	public final void shutdown()
	{
		app.shutdown();
	}
	
}
