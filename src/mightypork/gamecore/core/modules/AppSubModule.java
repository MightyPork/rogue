package mightypork.gamecore.core.modules;


import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.resources.audio.SoundSystem;
import mightypork.utils.eventbus.clients.BusNode;
import mightypork.utils.eventbus.clients.DelegatingClient;
import mightypork.utils.eventbus.clients.RootBusNode;


/**
 * Delegating bus client, to be attached to any {@link DelegatingClient}, such
 * as a {@link RootBusNode}.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class AppSubModule extends BusNode implements AppAccess {
	
	private final AppAccess app;
	
	
	/**
	 * Create submodule
	 * 
	 * @param app access to app systems
	 */
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
	public final void shutdown()
	{
		app.shutdown();
	}
	
}
