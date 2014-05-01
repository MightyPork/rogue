package mightypork.rogue;


import java.io.File;
import java.util.Locale;

import mightypork.gamecore.app.BaseApp;
import mightypork.gamecore.app.MainLoop;
import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.EventBus;
import mightypork.gamecore.gui.events.CrossfadeRequest;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.logging.writers.LogWriter;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.resources.loading.AsyncResourceLoader;
import mightypork.gamecore.util.ion.Ion;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;
import mightypork.rogue.screens.FpsOverlay;
import mightypork.rogue.screens.game.ScreenGame;
import mightypork.rogue.screens.menu.ScreenMainMenu;
import mightypork.rogue.screens.test_bouncyboxes.ScreenTestBouncy;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.item.Item;
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
		
		Ion.registerType(Item.ION_MARK, Item.class);
		Ion.registerType(Level.ION_MARK, Level.class);
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
		
		screens.addScreen("test.bouncy", new ScreenTestBouncy(this));
		
		screens.addScreen("menu", new ScreenMainMenu(this));
		screens.addScreen("game", new ScreenGame(this));
		
		screens.addOverlay(new FpsOverlay(this));
	}
	
	
	@Override
	protected void initInputSystem(InputSystem input)
	{
		// this will work only with reusable events (such as requests)
		bindEventToKey(new ActionRequest(RequestType.FULLSCREEN), Keys.F11);
		bindEventToKey(new ActionRequest(RequestType.SCREENSHOT), Keys.F2);
		
		bindEventToKey(new CrossfadeRequest(null), Keys.L_CONTROL, Keys.Q);
		
		bindEventToKey(new CrossfadeRequest("menu"), Keys.ESCAPE);
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
	
	
	@Override
	protected MainLoop createMainLoop()
	{
		return new GameLoop(this);
	}
	
	
	@Override
	protected void postInit()
	{
		// TODO tmp
		WorldProvider.get().createWorld(Double.doubleToLongBits(Math.random()));
		
		getEventBus().send(new CrossfadeRequest("game", true));
	}
}
