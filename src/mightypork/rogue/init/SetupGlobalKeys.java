package mightypork.rogue.init;


import mightypork.gamecore.core.App;
import mightypork.gamecore.core.init.InitTask;
import mightypork.gamecore.core.plugins.screenshot.ScreenshotRequest;
import mightypork.gamecore.graphics.FullscreenToggleRequest;
import mightypork.gamecore.input.Trigger;
import mightypork.utils.eventbus.BusEvent;


public class SetupGlobalKeys extends InitTask {


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


	@Override
	public String getName()
	{
		return "global_keys";
	}


	@Override
	public String[] getDependencies()
	{
		return new String[] { "config" };
	}
}
