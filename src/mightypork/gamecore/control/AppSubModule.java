package mightypork.gamecore.control;


import mightypork.gamecore.audio.SoundSystem;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.util.control.eventbus.clients.BusNode;
import mightypork.util.control.eventbus.clients.DelegatingClient;
import mightypork.util.control.eventbus.clients.RootBusNode;


/**
 * Delegating bus client, to be attached to any {@link DelegatingClient}, such
 * as a {@link RootBusNode}.
 * 
 * @author MightyPork
 */
public class AppSubModule extends BusNode implements AppAccess {
	
	private final AppAccess app;
	
	
	/**
	 * Create submodule
	 * 
	 * @param app access to app systems
	 */
	public AppSubModule(AppAccess app)
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
