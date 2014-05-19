package mightypork.rogue;


import mightypork.gamecore.Config;
import mightypork.gamecore.Config.KeyOpts;
import mightypork.gamecore.Config.KeySetup;


public class RogueKeys implements Config.KeySetup {
	
	@Override
	public void addKeys(Config.KeyOpts keys)
	{
		keys.add("global.quit", "CTRL+Q");
		keys.add("global.menu", "CTRL+M");
		keys.add("global.screenshot", "F2");
		keys.add("global.fullscreen", "F11");
		
		keys.add("general.cancel", "ESC");
		keys.add("general.confirm", "ENTER");
		keys.add("general.yes", "Y");
		keys.add("general.no", "N");
	}
}
