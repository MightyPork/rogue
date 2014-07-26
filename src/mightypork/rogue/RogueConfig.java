package mightypork.rogue;


import mightypork.gamecore.config.ConfigSetup;
import mightypork.utils.config.propmgr.PropertyManager;


public class RogueConfig implements ConfigSetup {
	
	@Override
	public void addOptions(PropertyManager prop)
	{
		prop.addBoolean("display.fullscreen", false, "Start in fullscreen (remembers state at exit)");
		prop.addInteger("display.width", 1024, "Initial width (remembers from last time)");
		prop.addInteger("display.height", 768, "Initial height (remembers from last time)");
		
		prop.addBoolean("opt.show_story", true, "Show story on start-up.");
	}
	
}
