package mightypork.rogue;


import java.io.File;

import mightypork.gamecore.core.Config;
import mightypork.gamecore.core.events.MainLoopRequest;
import mightypork.gamecore.core.events.ShudownRequest;
import mightypork.gamecore.core.events.UserQuitRequest;
import mightypork.gamecore.core.modules.BaseApp;
import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.KeyStroke.Edge;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.render.events.FullscreenToggleRequest;
import mightypork.gamecore.render.events.ScreenshotRequest;
import mightypork.gamecore.render.events.ScreenshotRequestListener;
import mightypork.gamecore.render.events.ViewportChangeEvent;
import mightypork.gamecore.render.events.ViewportChangeListener;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.util.ion.Ion;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
import mightypork.rogue.screens.FpsOverlay;
import mightypork.rogue.screens.LoadingOverlay;
import mightypork.rogue.screens.game.ScreenGame;
import mightypork.rogue.screens.menu.ScreenMainMenu;
import mightypork.rogue.screens.select_world.ScreenSelectWorld;
import mightypork.rogue.screens.story.ScreenStory;
import mightypork.rogue.world.Inventory;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.level.Level;


/**
 * Main class
 * 
 * @author MightyPork
 */
public final class RogueApp extends BaseApp implements ViewportChangeListener, ScreenshotRequestListener {
	
	public RogueApp(File workdir, boolean singleInstance)
	{
		super(workdir, singleInstance);
		
		opt().addRoutes(new RogueRoutes());
		opt().addResources(new RogueResources());
		opt().addKeys(new RogueKeys());
		opt().addConfig(new RogueConfig());
		opt().setBusLogging(true);
		
		opt().setConfigFile("config.ini", "Rogue config file");
		opt().setLogOptions("logs", "runtime", 5, java.util.logging.Level.ALL);
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
		// init based on config
		final int w = Config.getValue("display.width");
		final int h = Config.getValue("display.height");
		final boolean fs = Config.getValue("display.fullscreen");
		
		display.createMainWindow(w, h, true, fs, Const.TITLEBAR);
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
		screens.addScreen("story", new ScreenStory(this));
		
		screens.addOverlay(new FpsOverlay(this));
		screens.addOverlay(new LoadingOverlay(this));
	}
	
	
	@Override
	protected void initInputSystem(InputSystem input)
	{
		// this will work only with reusable events (such as requests)
		bindEventToKey(new FullscreenToggleRequest(), "global.fullscreen");
		bindEventToKey(new ScreenshotRequest(), "global.screenshot");
		
		bindEventToKey(new UserQuitRequest(), "global.quit");
		bindEventToKey(new ShudownRequest(), "global.quit_force");
	}
	
	
	private void bindEventToKey(final BusEvent<?> event, String strokeName)
	{
		getInput().bindKey(Config.getKey(strokeName), Edge.RISING, new Runnable() {
			
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
				if (Config.getValue("opt.show_story")) {
					Config.setValue("opt.show_story", false);
					getEventBus().send(new RogueStateRequest(RogueState.STORY, true));
				} else {
					getEventBus().send(new RogueStateRequest(RogueState.MAIN_MENU, true));
				}
			}
		}, true));
	}
	
	
	@Override
	public void onViewportChanged(ViewportChangeEvent event)
	{
		// save viewport size to config file
		final boolean fs = DisplaySystem.isFullscreen();
		
		Config.setValue("display.fullscreen", fs);
		
		if (!fs) {
			Config.setValue("display.width", DisplaySystem.getWidth());
			Config.setValue("display.height", DisplaySystem.getHeight());
		}
	}
	
	
	@Override
	public void onScreenshotRequest()
	{
		// screenshot sound
		Res.getSoundEffect("gui.shutter").play(0.8);
	}
	
	@Override
	protected void writeLogHeader()
	{
		Log.i("## Starting Rogue v."+Const.VERSION+" ##");
		super.writeLogHeader();
	}
}
