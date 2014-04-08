package mightypork.rogue;


import mightypork.gamecore.control.BaseApp;
import mightypork.gamecore.control.GameLoop;
import mightypork.gamecore.input.Action;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;
import mightypork.rogue.util.Utils;


public class MainLoop extends GameLoop implements ActionRequest.Listener {
	
	public MainLoop(BaseApp app) {
		super(app);
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
			Utils.runAsThread(new TaskTakeScreenshot());
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
			getDisplay().switchFullscreen();
		}
	};
}
