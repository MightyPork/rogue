package mightypork.rogue.init;


import mightypork.gamecore.core.config.Config;
import mightypork.gamecore.core.config.InitTaskConfig;


public class SetupConfig extends InitTaskConfig {

	@Override
	protected Config buildConfig()
	{
		final Config cfg = new Config("config.ini", "Rogue config file");

		cfg.addBoolean("display.fullscreen", false, "Start in fullscreen (remembers state at exit)");
		cfg.addInteger("display.width", 1024, "Initial width (remembers from last time)");
		cfg.addInteger("display.height", 768, "Initial height (remembers from last time)");

		cfg.addBoolean("opt.show_story", true, "Show story on start-up.");
		
		// keys

		cfg.addKeyStroke("global.quit", "CTRL+Q", "Quit the game");
		cfg.addKeyStroke("global.quit_force", "CTRL+SHIFT+Q", "Quit the game without asking, low-level");

		cfg.addKeyStroke("global.screenshot", "F2", "Take screenshot (save into working directory)");
		cfg.addKeyStroke("global.fullscreen", "F11", "Toggle fullscreen");
		cfg.addKeyStroke("global.fps_meter", "F3", "Toggle FPS meter overlay");

		cfg.addKeyStroke("general.close", "ESC", "Leave a dialog or screen");
		cfg.addKeyStroke("general.cancel", "ESC", "\"Cancel\" option in dialogs");
		cfg.addKeyStroke("general.confirm", "ENTER", "\"Confirm\" option in dialogs");
		cfg.addKeyStroke("general.yes", "Y", "\"Yes\" option in dialogs");
		cfg.addKeyStroke("general.no", "N", "\"No\" option in dialogs");

		cfg.addKeyStroke("game.quit", "ESC", "Quit to menu");
		cfg.addKeyStroke("game.save", "CTRL+S", "Save to file");
		cfg.addKeyStroke("game.load", "CTRL+L", "Load from file");
		cfg.addKeyStroke("game.zoom", "Z", "Toggle zoom");
		cfg.addKeyStroke("game.minimap", "M", "Toggle minimap");
		cfg.addKeyStroke("game.eat", "E", "Eat smallest food item");
		cfg.addKeyStroke("game.drop", "D", "Drop last picked item");
		cfg.addKeyStroke("game.inventory", "I", "Toggle inventory view");
		cfg.addKeyStroke("game.pause", "P", "Pause the game");

		cfg.addKeyStroke("game.walk.up", "UP", "Walk north");
		cfg.addKeyStroke("game.walk.down", "DOWN", "Walk south");
		cfg.addKeyStroke("game.walk.left", "LEFT", "Walk west");
		cfg.addKeyStroke("game.walk.right", "RIGHT", "Walk east");

		cfg.addKeyStroke("game.cheat.xray", "CTRL+SHIFT+X", "Cheat to see unexplored tiles");

		cfg.addKeyStroke("game.inv.use", "E", "Use (eat or equip) the selected item");
		cfg.addKeyStroke("game.inv.drop", "D", "Drop the selected item");
		cfg.addKeyStroke("game.inv.move.left", "LEFT", "Move inventory cursor left");
		cfg.addKeyStroke("game.inv.move.right", "RIGHT", "Move inventory cursor right");
		cfg.addKeyStroke("game.inv.move.up", "UP", "Move inventory cursor up");
		cfg.addKeyStroke("game.inv.move.down", "DOWN", "Move inventory cursor down");
		
		return cfg;
	}

}
