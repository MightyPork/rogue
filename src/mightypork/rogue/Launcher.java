package mightypork.rogue;


import java.io.File;
import java.util.logging.Level;

import mightypork.gamecore.core.App;
import mightypork.gamecore.core.DeltaMainLoop;
import mightypork.gamecore.core.MainLoop;
import mightypork.gamecore.core.init.InitTaskCrashHandler;
import mightypork.gamecore.core.init.InitTaskCustom;
import mightypork.gamecore.core.init.InitTaskDisplay;
import mightypork.gamecore.core.init.InitTaskIonizables;
import mightypork.gamecore.core.init.InitTaskLog;
import mightypork.gamecore.core.init.InitTaskLogHeader;
import mightypork.gamecore.core.init.InitTaskMainLoop;
import mightypork.gamecore.core.init.InitTaskResourceLoaderAsync;
import mightypork.gamecore.core.init.InitTaskScreens;
import mightypork.gamecore.core.plugins.screenshot.InitTaskPluginScreenshot;
import mightypork.gamecore.core.plugins.screenshot.ScreenshotRequest;
import mightypork.gamecore.graphics.FullscreenToggleRequest;
import mightypork.gamecore.graphics.Renderable;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.gui.screens.impl.CrossfadeOverlay;
import mightypork.gamecore.input.Trigger;
import mightypork.rogue.screens.FpsOverlay;
import mightypork.rogue.screens.LoadingOverlay;
import mightypork.rogue.screens.game.ScreenGame;
import mightypork.rogue.screens.menu.ScreenMainMenu;
import mightypork.rogue.screens.select_world.ScreenSelectWorld;
import mightypork.rogue.screens.story.ScreenStory;
import mightypork.rogue.world.Inventory;
import mightypork.rogue.world.WorldProvider;
import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.files.OsUtils;
import mightypork.utils.ion.Ion;
import mightypork.utils.logging.Log;


public class Launcher {

	/**
	 * Launcher
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
//		System.out.println("argv = " + Arrays.toString(args)+"\n");

		Level llSyso = Level.FINER;
		final Level llFile = Level.ALL;

		File workdir = null;
		boolean logBus = false;

		try {
			boolean localWorkdir = false;
			String lwdDir = null;

			for (int i = 0; i < args.length; i++) {
				final String arg = args[i];

				if (arg.equals("--workdir") || arg.equals("-w")) {
					localWorkdir = true;
					lwdDir = args[i + 1];
					i++;
					continue;

				} else if (arg.equals("--silent") || arg.equals("-s")) {
					llSyso = Level.OFF;
					continue;

				} else if (arg.equals("--warn") || arg.equals("-e")) {
					llSyso = Level.WARNING;
					continue;

				} else if (arg.equals("--verbose") || arg.equals("-v")) {
					llSyso = Level.ALL;
					continue;

				} else if (arg.equals("--help") || arg.equals("-h")) {
					printHelp();
					System.exit(0);

				} else if (arg.equals("--debug-bus")) {
					logBus = true;

				} else {
					System.err.println("Unknown argument: " + arg);
					printHelp();
					System.exit(1);
				}
			}

			if (!localWorkdir) {
				workdir = OsUtils.getHomeWorkDir(".rogue");
			} else {
				workdir = new File(lwdDir);
			}

		} catch (final Exception e) {
			System.out.println("Error parsing arguments:");
			e.printStackTrace();
			printHelp();
			System.exit(1);
		}

		final App app = new RogueApp();
		
		App.bus().detailedLogging = true;

		app.addInitTask(new RogueInitWorkdir(workdir));
		app.addInitTask(new RogueInitConfig());
		
		app.addInitTask(new InitTaskResourceLoaderAsync());
		app.addInitTask(new RogueInitResources());

		app.addInitTask(new InitTaskCrashHandler());
		app.addInitTask(new InitTaskPluginScreenshot("screenshots"));
		
		app.addInitTask(new InitTaskIonizables() {
			
			@Override
			public void after()
			{
				Ion.register(mightypork.rogue.world.level.Level.class);
				Ion.register(Inventory.class);
			}
		});
		
		
		final Level logLevelWrite = llFile, logLevelPrint = llSyso;
		
		app.addInitTask(new InitTaskLog() {

			@Override
			public void init()
			{
				setArchiveCount(5);
				setLevels(logLevelWrite, logLevelPrint);
				setLogDir("logs");
				setLogName("runtime");
			}
		});
		
		app.addInitTask(new InitTaskLogHeader() {
			
			@Override
			public void before()
			{
				Log.i("## Starting Rogue v." + Const.VERSION + " ##");
			}
		});
		
		
		app.addInitTask(new InitTaskDisplay() {

			@Override
			public void init()
			{
				final int w = App.cfg().getValue("display.width");
				final int h = App.cfg().getValue("display.height");
				final boolean fs = App.cfg().getValue("display.fullscreen");
				
				setSize(w, h);
				setResizable(true);
				setFullscreen(fs);
				setTitle(Const.TITLEBAR);
				setTargetFps(Const.FPS_RENDER);
			}
		});
		
		app.addInitTask(new InitTaskCustom("global_keys", new String[] { "config" }) {
			
			@Override
			public void run()
			{
				bindEventToKey(new FullscreenToggleRequest(), "global.fullscreen");
				bindEventToKey(new ScreenshotRequest(), "global.screenshot");
				
				final Runnable quitTask = new Runnable() {

					@Override
					public void run()
					{
						App.shutdown();
					}
				};

				App.input().bindKey(App.cfg().getKeyStroke("global.quit"), Trigger.RISING, quitTask);
				App.input().bindKey(App.cfg().getKeyStroke("global.quit_force"), Trigger.RISING, quitTask);
			}
			
			
			private void bindEventToKey(final BusEvent<?> event, String strokeName)
			{
				App.input().bindKey(App.cfg().getKeyStroke(strokeName), Trigger.RISING, new Runnable() {
					
					@Override
					public void run()
					{
						App.bus().send(event);
					}
				});
			}
			
		});
		
		
		app.addInitTask(new InitTaskMainLoop() {
			
			@Override
			protected MainLoop getLoopImpl()
			{
				final MainLoop loop = new DeltaMainLoop();
				return loop;
			}
		});
		
		
		app.addInitTask(new InitTaskScreens() {
			
			@Override
			protected Renderable getMainRenderableImpl()
			{
				final ScreenRegistry screens = new ScreenRegistry();
				app.addChildClient(screens);
				
				/* game screen references world provider instance */
				App.bus().subscribe(new RogueStateManager());
				App.bus().subscribe(WorldProvider.get());
				
				screens.addOverlay(new CrossfadeOverlay());

				screens.addScreen("main_menu", new ScreenMainMenu());
				screens.addScreen("select_world", new ScreenSelectWorld());
				screens.addScreen("game", new ScreenGame());
				screens.addScreen("story", new ScreenStory());

				screens.addOverlay(new FpsOverlay());
				screens.addOverlay(new LoadingOverlay());
				
				return screens;
			}
		});
		
		
		app.start();
	}


	private static void printHelp()
	{
		//@formatter:off
		System.out.println(
				"Arguments:\n" +
						"\t--workdir <path>, -w <path> .... specify working directory\n" +
						"\t--verbose, --debug, -v ......... print all messages\n" +
						"\t--silent, -s ................... print no messages\n" +
						"\t--warnings, -e ................. print only warning and error messages\n" +
				"\t--help, -h ..................... show this help\n");
		//@formatter:on
	}
}
