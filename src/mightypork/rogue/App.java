package mightypork.rogue;


import java.io.File;
import java.util.Arrays;
import java.util.Locale;

import mightypork.gamecore.app.BaseApp;
import mightypork.gamecore.app.MainLoop;
import mightypork.gamecore.app.MainLoopRequest;
import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.EventBus;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.logging.writers.LogWriter;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.resources.loading.AsyncResourceLoader;
import mightypork.gamecore.util.ion.Ion;
import mightypork.gamecore.util.strings.StringUtils;
import mightypork.rogue.GameStateManager.GameState;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;
import mightypork.rogue.events.GameStateRequest;
import mightypork.rogue.screens.FpsOverlay;
import mightypork.rogue.screens.LoadingOverlay;
import mightypork.rogue.screens.game.ScreenGame;
import mightypork.rogue.screens.layout_testing.LayoutTestScreen;
import mightypork.rogue.screens.menu.ScreenMainMenu;
import mightypork.rogue.screens.select_world.ScreenSelectWorld;
import mightypork.rogue.world.Inventory;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.level.Level;


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
		Log.f3(Arrays.toString(args));
		
		try {
			boolean lwd = false;
			String lwdDir = null;
			
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("--workdir") || args[i].equals("-w")) {
					lwd = true;
					lwdDir = args[i + 1];
					i++;
				}
			}
			
			if (!lwd) {
				Paths.init();
			} else {
				Paths.init(lwdDir);
			}
			
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.e("Malformed arguments.");
		}
		
		Log.i("Using workdir: " + Paths.WORKDIR.getAbsolutePath());
		
		(new App()).start();
	}
	
	
	@Override
	protected File getLockFile()
	{
		return Paths.LOCK;
	}
	
	
	@Override
	protected void preInit()
	{
		Config.init();
		Config.save();
	}
	
	
	@Override
	protected LogWriter createLog()
	{
		Locale.setDefault(Locale.ENGLISH); // for decimal point in numbers
		
		return Log.create("runtime", Paths.LOG_FILE, 5);
	}
	
	
	@Override
	protected void registerIonizables()
	{
		super.registerIonizables();
		
		Ion.registerType(Level.ION_MARK, Level.class);
		Ion.registerType(Inventory.ION_MARK, Inventory.class);
	}
	
	
	@Override
	protected void initBus(EventBus bus)
	{
		bus.detailedLogging = true;
	}
	
	
	@Override
	protected void initDisplay(DisplaySystem display)
	{
		display.createMainWindow(Const.WINDOW_W, Const.WINDOW_H, true, Config.START_IN_FS, Const.TITLEBAR);
		display.setTargetFps(Const.FPS_RENDER);
	}
	
	
	@Override
	protected void initResources()
	{
		final AsyncResourceLoader thread = AsyncResourceLoader.launch(this);
		thread.enableMainLoopQueuing(true);
		
		Res.load(this);
	}
	
	
	@Override
	protected void initScreens(ScreenRegistry screens)
	{
		super.initScreens(screens);
		
		/* game screen references world provider instance */
		WorldProvider.init(this);
		
		getEventBus().subscribe(new GameStateManager(this));
		
		screens.addScreen("main_menu", new ScreenMainMenu(this));
		screens.addScreen("select_world", new ScreenSelectWorld(this));
		screens.addScreen("game", new ScreenGame(this));
		screens.addScreen("test.layout", new LayoutTestScreen(this));
		
		screens.addOverlay(new FpsOverlay(this));
		screens.addOverlay(new LoadingOverlay(this));
	}
	
	
	@Override
	protected void initInputSystem(InputSystem input)
	{
		// this will work only with reusable events (such as requests)
		bindEventToKey(new ActionRequest(RequestType.FULLSCREEN), Keys.F11);
		bindEventToKey(new ActionRequest(RequestType.SCREENSHOT), Keys.F2);
		
		bindEventToKey(new GameStateRequest(GameState.EXIT), Keys.Q, Keys.MOD_SHIFT | Keys.MOD_CONTROL);
		bindEventToKey(new GameStateRequest(GameState.MAIN_MENU), Keys.M, Keys.MOD_SHIFT | Keys.MOD_CONTROL);
	}
	
	
	private void bindEventToKey(final BusEvent<?> event, int key)
	{
		bindEventToKey(event, key, Keys.MOD_NONE);
	}
	
	
	private void bindEventToKey(final BusEvent<?> event, int key, int mod)
	{
		getInput().bindKey(new KeyStroke(key, mod), new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(event);
			}
		});
	}
	
	
	@Override
	protected MainLoop createMainLoop()
	{
		return new GameLoop(this);
	}
	
	
	@Override
	protected void postInit()
	{
		getEventBus().send(new MainLoopRequest(new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(new GameStateRequest(GameState.MAIN_MENU));
				//getEventBus().send(new CrossfadeRequest("test.layout", true));
			}
		}));
	}
}
