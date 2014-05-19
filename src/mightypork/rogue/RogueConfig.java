package mightypork.rogue;


import mightypork.gamecore.Config.ConfigSetup;
import mightypork.gamecore.util.files.config.PropertyManager;


public class RogueConfig implements ConfigSetup {
	
	@Override
	public void addOptions(PropertyManager prop)
	{
		prop.putBoolean("display.fullscreen", false, "Start in fullscreen (remembers state at exit)");
		prop.putInteger("display.width", 1024, "Initial width (remembers from last time)");
		prop.putInteger("display.height", 768, "Initial height (remembers from last time)");
	}
	
}
