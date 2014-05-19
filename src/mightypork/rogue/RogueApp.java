package mightypork.rogue;


import java.io.File;

import mightypork.gamecore.Config;
import mightypork.gamecore.core.BaseApp;
import mightypork.gamecore.core.MainLoopRequest;
import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.KeyConfig;
import mightypork.gamecore.input.KeyStroke.Edge;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.render.events.FullscreenToggleRequest;
import mightypork.gamecore.render.events.ScreenshotRequest;
import mightypork.gamecore.util.ion.Ion;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
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
public final class RogueApp extends BaseApp {
	
	public RogueApp(File workdir, boolean singleInstance)
	{
		super(workdir, singleInstance);
		
		opt().addRoutes(new RogueRoutes());
		opt().addResources(new RogueResources());
		opt().addKeys(new RogueKeys());
		opt().addConfig(new RogueConfig());
		
		opt().setConfigFile(this, "config.ini", "Rogue config file");
		opt().setLogOptions("/", "runtime", 5, java.util.logging.Level.ALL);
	}
	
	
	@Override
	protected void registerIonizables()
	{
		super.registerIonizables();
		
		Ion.registerType(Level.ION_MARK, Level.class);
		Ion.registerType(Inventory.ION_MARK, Inventory.class);
	}
	
	
	@Override
	protected void initDisplay(DisplaySystem display)
	{
		display.createMainWindow(Const.WINDOW_W, Const.WINDOW_H, true, Config.<Boolean> get("opt.fullscreen"), Const.TITLEBAR);
		display.setTargetFps(Const.FPS_RENDER);
	}
	
	
	@Override
	protected void initScreens(ScreenRegistry screens)
	{
		super.initScreens(screens);
		
		/* game screen references world provider instance */
		WorldProvider.init(this);
		getEventBus().subscribe(new RogueStateManager(this));
		
		
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
		bindEventToKey(new FullscreenToggleRequest(), "global.fullscreen");
		bindEventToKey(new ScreenshotRequest(), "global.screenshot");
		
		bindEventToKey(new RogueStateRequest(RogueState.EXIT), "global.quit");
		bindEventToKey(new RogueStateRequest(RogueState.MAIN_MENU), "global.menu");
	}
	
	
	private void bindEventToKey(final BusEvent<?> event, String strokeName)
	{
		getInput().bindKey(KeyConfig.get(strokeName), Edge.RISING, new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(event);
			}
		});
	}
	
	
	@Override
	protected void postInit()
	{
		getEventBus().send(new MainLoopRequest(new Runnable() {
			
			@Override
			public void run()
			{
				getEventBus().send(new RogueStateRequest(RogueState.MAIN_MENU));
				//getEventBus().send(new CrossfadeRequest("test.layout", true));
			}
		}));
	}
}
