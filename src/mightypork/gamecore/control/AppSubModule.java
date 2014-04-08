package mightypork.gamecore.control;


import mightypork.gamecore.audio.SoundSystem;
import mightypork.gamecore.control.bus.clients.BusNode;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;


/**
 * App event bus client, to be used for subsystems, screens and anything that
 * needs access to the eventbus
 * 
 * @author MightyPork
 */
public abstract class AppSubModule extends BusNode implements AppAccess {
	
	private final AppAccess app;
	
	
	public AppSubModule(AppAccess app) {
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
