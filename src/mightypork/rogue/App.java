package mightypork.rogue;


import java.io.File;
import java.util.Locale;

import mightypork.gamecore.control.BaseApp;
import mightypork.gamecore.control.GameLoop;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.loading.AsyncResourceLoader;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;
import mightypork.rogue.screens.FpsOverlay;
import mightypork.rogue.screens.main_menu.ScreenMainMenu;
import mightypork.rogue.screens.test_bouncyboxes.ScreenTestBouncy;
import mightypork.rogue.screens.test_cat_sound.ScreenTestCat;
import mightypork.rogue.screens.test_render.ScreenTestRender;
import mightypork.util.control.eventbus.EventBus;
import mightypork.util.logging.Log;
import mightypork.util.logging.writers.LogWriter;


/**
 * Main class
 * 
 * @author MightyPork
 */
public class App extends BaseApp {
	
	/**
	 * Launcher
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Config.init();
		Config.save();
		
		(new App()).start();
	}
	
	
	@Override
	protected LogWriter createLog()
	{
		Locale.setDefault(Locale.ENGLISH); // for decimal point in numbers
		
		return Log.create("runtime", Paths.LOG_FILE, 5);
	}
	
	
	@Override
	protected void initDisplay(DisplaySystem display)
	{
		display.createMainWindow(Const.WINDOW_W, Const.WINDOW_H, true, Config.START_IN_FS, Const.TITLEBAR);
		display.setTargetFps(Const.FPS_RENDER);
	}
	
	
	@Override
	protected void initScreens(ScreenRegistry screens)
	{
		screens.addScreen(new ScreenTestBouncy(this));
		screens.addScreen(new ScreenTestCat(this));
		screens.addScreen(new ScreenTestRender(this));
		screens.addScreen(new ScreenMainMenu(this));
		
		screens.addOverlay(new FpsOverlay(this));
		
		screens.showScreen("rogue.menu");
	}
	
	
	@Override
	protected GameLoop createLoop()
	{
		return new MainLoop(this);
	}
	
	
	@Override
	protected void initResources()
	{
		final AsyncResourceLoader thread = AsyncResourceLoader.launch(this);
		thread.enableMainLoopQueuing(true);
		
		Res.load(this);
	}
	
	
	@Override
	protected File getLockFile()
	{
		return Paths.LOCK;
	}
	
	
	@Override
	protected void initBus(EventBus bus)
	{
		bus.detailedLogging = true;
	}
	
	
	@Override
	protected void initInputSystem(InputSystem input)
	{
		// Go fullscreen
		input.bindKey(new KeyStroke(Keys.F11), new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(new ActionRequest(RequestType.FULLSCREEN));
			}
		});
		
		// Take screenshot
		input.bindKey(new KeyStroke(Keys.F2), new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(new ActionRequest(RequestType.SCREENSHOT));
			}
		});
		
		// Exit
		input.bindKey(new KeyStroke(Keys.L_CONTROL, Keys.Q), new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(new ActionRequest(RequestType.SHUTDOWN));
			}
		});
	}
	
}
