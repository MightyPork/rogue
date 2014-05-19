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
		keys.add("global.fps_meter", "F3");

		keys.add("general.back", "ESC"); // leave a dialog / screen
		keys.add("general.cancel", "ESC"); // cancel operation
		keys.add("general.confirm", "ENTER"); // confirm default option
		keys.add("general.yes", "Y"); // answer YES
		keys.add("general.no", "N"); // answer NO
		
		keys.add("game.quit", "ESC");
		keys.add("game.save", "CTRL+S");
		keys.add("game.load", "CTRL+L");
		keys.add("game.zoom", "Z");
		keys.add("game.minimap", "M");
		keys.add("game.eat", "E");
		keys.add("game.drop", "D");
		keys.add("game.inventory", "I");
		keys.add("game.pause", "P");
		keys.add("game.pause2", "SPACE");
		keys.add("game.cheat.xray", "CTRL+SHIFT+X");
	}
}
