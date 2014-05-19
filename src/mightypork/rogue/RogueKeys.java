package mightypork.rogue;


import mightypork.gamecore.input.KeyConfig;
import mightypork.gamecore.input.KeySetup;


public class RogueKeys implements KeySetup {
	
	@Override
	public void addKeys(KeyConfig keys)
	{
		keys.add("key.global.quit", "CTRL+SHIFT+Q");
		keys.add("key.global.menu", "CTRL+SHIFT+M");
		keys.add("key.global.screenshot", "F2");
		keys.add("key.global.fullscreen", "F11");
		
		keys.add("key.general.cancel", "ESC");
		keys.add("key.general.confirm", "ENTER");
		keys.add("key.general.yes", "Y");
		keys.add("key.general.no", "N");
	}
}
