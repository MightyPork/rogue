package mightypork.gamecore.core.config;

import mightypork.utils.files.config.PropertyManager;

/**
 * Config setup, class used to populate the config file.
 */
public interface ConfigSetup {
	
	void addOptions(PropertyManager prop);
}