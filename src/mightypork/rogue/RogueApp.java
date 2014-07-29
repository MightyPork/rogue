package mightypork.rogue;


import java.io.File;
import java.util.logging.Level;

import mightypork.gamecore.backends.lwjgl.LwjglBackend;
import mightypork.gamecore.core.App;
import mightypork.gamecore.core.events.MainLoopRequest;
import mightypork.gamecore.core.init.InitTaskIonizables;
import mightypork.gamecore.core.init.InitTaskLog;
import mightypork.gamecore.core.init.InitTaskLogHeader;
import mightypork.gamecore.core.plugins.screenshot.InitTaskPluginScreenshot;
import mightypork.gamecore.core.plugins.screenshot.ScreenshotRequestListener;
import mightypork.gamecore.gui.events.ViewportChangeEvent;
import mightypork.gamecore.gui.events.ViewportChangeListener;
import mightypork.gamecore.resources.Res;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
import mightypork.rogue.init.RogueAddResources;
import mightypork.rogue.init.RogueInitUI;
import mightypork.rogue.init.RogueSetupConfig;
import mightypork.rogue.init.RogueSetupDisplay;
import mightypork.rogue.init.RogueSetupGlobalKeys;
import mightypork.rogue.init.RogueSetupWorkdir;
import mightypork.rogue.world.Inventory;
import mightypork.utils.ion.Ion;
import mightypork.utils.logging.Log;


/**
 * Main class
 *
 * @author Ondřej Hruška (MightyPork)
 */
public final class RogueApp extends App implements ViewportChangeListener, ScreenshotRequestListener {
	
	public RogueApp(File workdir, final Level logStdout)
	{
		super(new LwjglBackend());

		addInitTask(new InitTaskLog() {

			@Override
			public void init()
			{
				setArchiveCount(5);
				setLevels(Level.ALL, logStdout);
				setLogDir("logs");
				setLogName("runtime");
			}
		});
		
		addInitTask(new InitTaskLogHeader() {

			@Override
			public void before()
			{
				Log.i("## Starting Rogue v." + Const.VERSION + " ##");
			}
		});

		addInitTask(new RogueSetupWorkdir(workdir));

		addInitTask(new RogueSetupConfig());

		addInitTask(new InitTaskPluginScreenshot("screenshots"));

		addInitTask(new InitTaskIonizables() {

			@Override
			public void after()
			{
				Ion.register(mightypork.rogue.world.level.Level.class);
				Ion.register(Inventory.class);
			}
		});
		
		
		addInitTask(new RogueSetupDisplay());
		addInitTask(new RogueSetupGlobalKeys());
		addInitTask(new RogueAddResources());
		addInitTask(new RogueInitUI());
	}
	
	
	@Override
	public void onScreenshotRequest()
	{
		Res.sound("gui.shutter").play(0.8);
	}
	
	
	@Override
	protected void postInit()
	{
		bus().send(new MainLoopRequest(new Runnable() {
			
			@Override
			public void run()
			{
				if (cfg().getValue("opt.show_story")) {
					cfg().setValue("opt.show_story", false);
					bus().send(new RogueStateRequest(RogueState.STORY, true));
				} else {
					bus().send(new RogueStateRequest(RogueState.MAIN_MENU, true));
				}
			}
		}, false));
	}
	
	
	@Override
	public void onViewportChanged(ViewportChangeEvent event)
	{
		// save viewport size to config file
		final boolean fs = gfx().isFullscreen();
		
		cfg().setValue("display.fullscreen", fs);
		
		if (!fs) {
			cfg().setValue("display.width", gfx().getWidth());
			cfg().setValue("display.height", gfx().getHeight());
		}
	}
}
