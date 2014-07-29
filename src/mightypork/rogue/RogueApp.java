package mightypork.rogue;


import mightypork.gamecore.backends.lwjgl.LwjglBackend;
import mightypork.gamecore.core.App;
import mightypork.gamecore.core.events.MainLoopRequest;
import mightypork.gamecore.core.plugins.screenshot.ScreenshotRequestListener;
import mightypork.gamecore.gui.events.ViewportChangeEvent;
import mightypork.gamecore.gui.events.ViewportChangeListener;
import mightypork.gamecore.resources.Res;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;


/**
 * Main class
 *
 * @author Ondřej Hruška (MightyPork)
 */
public final class RogueApp extends App implements ViewportChangeListener, ScreenshotRequestListener {
	
	public RogueApp()
	{
		super(new LwjglBackend());
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
