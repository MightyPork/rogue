package mightypork.rogue;


public class RogueKeys implements KeySetup {
	
	@Override
	public void addKeys(KeyOpts keys)
	{
		keys.addKey("global.quit", "CTRL+Q", "Quit the game");
		keys.addKey("global.quit_force", "CTRL+SHIFT+Q", "Quit the game without asking, low-level");
		
		keys.addKey("global.screenshot", "F2", "Take screenshot (save into working directory)");
		keys.addKey("global.fullscreen", "F11", "Toggle fullscreen");
		keys.addKey("global.fps_meter", "F3", "Toggle FPS meter overlay");
		
		keys.addKey("general.close", "ESC", "Leave a dialog or screen");
		keys.addKey("general.cancel", "ESC", "\"Cancel\" option in dialogs");
		keys.addKey("general.confirm", "ENTER", "\"Confirm\" option in dialogs");
		keys.addKey("general.yes", "Y", "\"Yes\" option in dialogs");
		keys.addKey("general.no", "N", "\"No\" option in dialogs");
		
		keys.addKey("game.quit", "ESC", "Quit to menu");
		keys.addKey("game.save", "CTRL+S", "Save to file");
		keys.addKey("game.load", "CTRL+L", "Load from file");
		keys.addKey("game.zoom", "Z", "Toggle zoom");
		keys.addKey("game.minimap", "M", "Toggle minimap");
		keys.addKey("game.eat", "E", "Eat smallest food item");
		keys.addKey("game.drop", "D", "Drop last picked item");
		keys.addKey("game.inventory", "I", "Toggle inventory view");
		keys.addKey("game.pause", "P", "Pause the game");
		
		keys.addKey("game.walk.up", "UP", "Walk north");
		keys.addKey("game.walk.down", "DOWN", "Walk south");
		keys.addKey("game.walk.left", "LEFT", "Walk west");
		keys.addKey("game.walk.right", "RIGHT", "Walk east");
		
		keys.addKey("game.cheat.xray", "CTRL+SHIFT+X", "Cheat to see unexplored tiles");
		
		keys.addKey("game.inv.use", "E", "Use (eat or equip) the selected item");
		keys.addKey("game.inv.drop", "D", "Drop the selected item");
		keys.addKey("game.inv.move.left", "LEFT", "Move inventory cursor left");
		keys.addKey("game.inv.move.right", "RIGHT", "Move inventory cursor right");
		keys.addKey("game.inv.move.up", "UP", "Move inventory cursor up");
		keys.addKey("game.inv.move.down", "DOWN", "Move inventory cursor down");
	}
}
