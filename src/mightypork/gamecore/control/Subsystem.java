package mightypork.gamecore.control;


import mightypork.gamecore.audio.SoundSystem;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;


/**
 * App event bus client, to be used for subsystems, screens and anything that
 * needs access to the eventbus
 * 
 * @author MightyPork
 */
public abstract class Subsystem extends ChildClient implements AppAccess {
	
	private final AppAccess app;
	
	
	public Subsystem(AppAccess app) {
		super(app);
		
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
	public void shutdown()
	{
		app.shutdown();
	}
	
}
