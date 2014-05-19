package mightypork.rogue;


import mightypork.gamecore.input.KeyConfig.KeyOpts;
import mightypork.gamecore.input.KeyConfig.KeySetup;


public class RogueKeys implements KeySetup {
	
	@Override
	public void addKeys(KeyOpts keys)
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
