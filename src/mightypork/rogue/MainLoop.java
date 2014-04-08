package mightypork.rogue;


import mightypork.gamecore.GameLoop;
import mightypork.gamecore.input.Action;
import mightypork.gamecore.render.Renderable;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;
import mightypork.rogue.util.Utils;


public class MainLoop extends GameLoop implements ActionRequest.Listener {
	
	final Renderable renderable;
	
	
	public MainLoop(App app, Renderable masterRenderable) {
		super(app);
		
		if (masterRenderable == null) {
			throw new NullPointerException("Master renderable must not be null.");
		}
		
		this.renderable = masterRenderable;
	}
	
	
	@Override
	protected void tick()
	{
		renderable.render();
	}
	
	
	@Override
	public void requestAction(RequestType request)
	{
		switch (request) {
			case FULLSCREEN:
				queueTask(taskFullscreen);
				break;
			
			case SCREENSHOT:
				queueTask(taskScreenshot);
				break;
			
			case SHUTDOWN:
				queueTask(taskShutdown);
		}
	}
	
	/** Take a screenshot */
	private final Action taskScreenshot = new Action() {
		
		@Override
		public void execute()
		{
			Res.getEffect("gui.shutter").play(1);
			Utils.runAsThread(new TaskTakeScreenshot(disp()));
		}
	};
	
	/** Shutdown the application */
	private final Action taskShutdown = new Action() {
		
		@Override
		public void execute()
		{
			shutdown();
		}
	};
	
	/** Toggle fullscreen */
	private final Action taskFullscreen = new Action() {
		
		@Override
		public void execute()
		{
			disp().switchFullscreen();
		}
	};
}
