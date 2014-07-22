package mightypork.rogue;


import mightypork.gamecore.core.config.KeyOpts;
import mightypork.gamecore.core.config.KeySetup;


public class RogueKeys implements KeySetup {
	
	@Override
	public void addKeys(KeyOpts keys)
	{
		keys.add("global.quit", "CTRL+Q", "Quit the game");
		keys.add("global.quit_force", "CTRL+SHIFT+Q", "Quit the game without asking, low-level");
		
		keys.add("global.screenshot", "F2", "Take screenshot (save into working directory)");
		keys.add("global.fullscreen", "F11", "Toggle fullscreen");
		keys.add("global.fps_meter", "F3", "Toggle FPS meter overlay");
		
		keys.add("general.close", "ESC", "Leave a dialog or screen");
		keys.add("general.cancel", "ESC", "\"Cancel\" option in dialogs");
		keys.add("general.confirm", "ENTER", "\"Confirm\" option in dialogs");
		keys.add("general.yes", "Y", "\"Yes\" option in dialogs");
		keys.add("general.no", "N", "\"No\" option in dialogs");
		
		keys.add("game.quit", "ESC", "Quit to menu");
		keys.add("game.save", "CTRL+S", "Save to file");
		keys.add("game.load", "CTRL+L", "Load from file");
		keys.add("game.zoom", "Z", "Toggle zoom");
		keys.add("game.minimap", "M", "Toggle minimap");
		keys.add("game.eat", "E", "Eat smallest food item");
		keys.add("game.drop", "D", "Drop last picked item");
		keys.add("game.inventory", "I", "Toggle inventory view");
		keys.add("game.pause", "P", "Pause the game");
		
		keys.add("game.walk.up", "UP", "Walk north");
		keys.add("game.walk.down", "DOWN", "Walk south");
		keys.add("game.walk.left", "LEFT", "Walk west");
		keys.add("game.walk.right", "RIGHT", "Walk east");
		
		keys.add("game.cheat.xray", "CTRL+SHIFT+X", "Cheat to see unexplored tiles");
		
		keys.add("game.inv.use", "E", "Use (eat or equip) the selected item");
		keys.add("game.inv.drop", "D", "Drop the selected item");
		keys.add("game.inv.move.left", "LEFT", "Move inventory cursor left");
		keys.add("game.inv.move.right", "RIGHT", "Move inventory cursor right");
		keys.add("game.inv.move.up", "UP", "Move inventory cursor up");
		keys.add("game.inv.move.down", "DOWN", "Move inventory cursor down");
	}
}
