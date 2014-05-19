package mightypork.rogue;


import mightypork.gamecore.ConfigSetup;
import mightypork.gamecore.util.files.config.PropertyManager;


public class RogueConfig implements ConfigSetup {
	
	@Override
	public void addOptions(PropertyManager prop)
	{
		prop.putBoolean("opt.fullscreen", false, "Start in fullscreen");
	}
	
}
