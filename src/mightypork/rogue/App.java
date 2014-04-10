package mightypork.rogue;


import java.io.File;

import mightypork.gamecore.control.BaseApp;
import mightypork.gamecore.control.GameLoop;
import mightypork.gamecore.control.bus.EventBus;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;
import mightypork.rogue.screens.test_bouncyboxes.ScreenTestBouncy;
import mightypork.rogue.screens.test_cat_sound.ScreenTestCat;
import mightypork.rogue.screens.test_font.ScreenTestFont;
import mightypork.rogue.screens.test_render.ScreenTestRender;


/**
 * Main class
 * 
 * @author MightyPork
 */
public class App extends BaseApp {
	
	@Override
	protected void initScreens(ScreenRegistry screens)
	{
		screens.add(new ScreenTestBouncy(this));
		screens.add(new ScreenTestCat(this));
		screens.add(new ScreenTestFont(this));
		screens.add(new ScreenTestRender(this));
		
		screens.showScreen("test.cat");
	}
	
	
	@Override
	protected void initBus(EventBus bus)
	{
		bus.addChannel(ActionRequest.class, ActionRequest.Listener.class);
		
		//bus.detailedLogging = true;
	}
	
	
	@Override
	protected void initInputSystem(InputSystem input)
	{
		// Go fullscreen
		getInput().bindKeyStroke(new KeyStroke(Keys.KEY_F11), new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(new ActionRequest(RequestType.FULLSCREEN));
			}
		});
		
		// Take screenshot
		getInput().bindKeyStroke(new KeyStroke(Keys.KEY_F2), new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(new ActionRequest(RequestType.SCREENSHOT));
			}
		});
		
		// Exit
		getInput().bindKeyStroke(new KeyStroke(Keys.KEY_LCONTROL, Keys.KEY_Q), new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(new ActionRequest(RequestType.SHUTDOWN));
			}
		});
	}
	
	
//	@Override
//	protected LogWriter createLog()
//	{
//		Locale.setDefault(Locale.ENGLISH);
//		
//		final LogWriter log = Log.create("runtime", Paths.LOG_FILE, 10);
//		log.setLevel(Level.WARNING);
//		log.enable(Config.LOGGING_ENABLED);
//		
//		return log;
//	}
	
	@Override
	protected void initDisplay(DisplaySystem display)
	{
		display.createMainWindow(Const.WINDOW_W, Const.WINDOW_H, true, Config.START_IN_FS, Const.TITLEBAR);
		display.setTargetFps(Const.FPS_RENDER);
	}
	
	
	@Override
	protected GameLoop createLoop()
	{
		return new MainLoop(this);
	}
	
	
	@Override
	protected void initResources()
	{
		Res.load(this);
	}
	
	
	@Override
	protected File getLockFile()
	{
		return Paths.LOCK;
	}
	
}
