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
import mightypork.rogue.screens.CrossfadeOverlay;
import mightypork.rogue.screens.CrossfadeRequest;
import mightypork.rogue.screens.FpsOverlay;
import mightypork.rogue.screens.gamescreen.ScreenGame;
import mightypork.rogue.screens.main_menu.ScreenMainMenu;
import mightypork.rogue.screens.test_bouncyboxes.ScreenTestBouncy;
import mightypork.rogue.screens.test_cat_sound.ScreenTestCat;
import mightypork.rogue.screens.test_render.ScreenTestRender;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.Level;
import mightypork.util.control.eventbus.BusEvent;
import mightypork.util.control.eventbus.EventBus;
import mightypork.util.files.ion.Ion;
import mightypork.util.logging.Log;
import mightypork.util.logging.writers.LogWriter;


/**
 * Main class
 * 
 * @author MightyPork
 */
public final class App extends BaseApp {
	
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
		// world provider instance is referenced by screens
		WorldProvider.init(this);
		
		screens.addScreen(new ScreenTestBouncy(this));
		screens.addScreen(new ScreenTestCat(this));
		screens.addScreen(new ScreenTestRender(this));
		screens.addScreen(new ScreenMainMenu(this));
		screens.addScreen(new ScreenGame(this));
		
		screens.addOverlay(new FpsOverlay(this));
		screens.addOverlay(new CrossfadeOverlay(this));
		
		screens.showScreen("game_screen");//main_menu
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
	protected void preInit()
	{
		Ion.registerBinary(Item.ION_MARK, Item.class);
		Ion.registerBinary(Level.ION_MARK, Level.class);
	}
	
	@Override
	protected void postInit()
	{
		// TODO tmp
		WorldProvider.get().createWorld(42);
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
		// this will work only with reusable events (such as requests)
		bindEventToKey(new ActionRequest(RequestType.FULLSCREEN), Keys.F11);
		bindEventToKey(new ActionRequest(RequestType.SCREENSHOT), Keys.F2);
		bindEventToKey(new CrossfadeRequest(null), Keys.L_CONTROL, Keys.Q);
		bindEventToKey(new CrossfadeRequest("main_menu"), Keys.L_CONTROL, Keys.M);
		getInput().bindKey(new KeyStroke(Keys.N), new Runnable() {
			
			@Override
			public void run()
			{
				WorldProvider.get().createWorld(Double.doubleToLongBits(Math.random()));
			}
		});
	}
	
	
	private void bindEventToKey(final BusEvent<?> event, int... keys)
	{
		getInput().bindKey(new KeyStroke(keys), new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(event);
			}
		});
	}
	
}
