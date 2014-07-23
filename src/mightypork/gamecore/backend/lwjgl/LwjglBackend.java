package mightypork.gamecore.backend.lwjgl;


import mightypork.gamecore.backend.Backend;
import mightypork.gamecore.render.RenderModule;
import mightypork.utils.eventbus.BusAccess;


/**
 * Game backend using LWJGL
 * 
 * @author MightyPork
 */
public class LwjglBackend extends Backend {
	
	public LwjglBackend(BusAccess busAccess) {
		super(busAccess);
	}


	private LwjglRenderModule renderer;
	
	
	@Override
	public void initialize()
	{
		addChildClient(renderer = new LwjglRenderModule(this));
	}
	
	
	@Override
	public RenderModule getRenderer()
	{
		return renderer;
	}
	
}
