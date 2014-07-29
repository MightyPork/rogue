package mightypork.rogue.init;


import mightypork.gamecore.core.App;
import mightypork.gamecore.core.init.InitTaskDisplay;
import mightypork.rogue.Const;


public class RogueSetupDisplay extends InitTaskDisplay {

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
	
}
