package mightypork.gamecore.core.config;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.input.KeyStroke;
import mightypork.utils.files.config.PropertyManager;
import mightypork.utils.logging.Log;


/**
 * Static application configuration; wrapper around {@link PropertyManager}
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Config {
	
	public static KeyOpts keyOpts = new KeyOpts();
	public static Map<String, KeyProperty> strokes = new HashMap<>();
	
	static PropertyManager cfg;
	
	
	/**
	 * Initialize property manger for a file
	 * 
	 * @param file config file
	 * @param comment file comment
	 */
	public static void init(File file, String comment)
	{
		cfg = new PropertyManager(file, comment);
		cfg.cfgNewlineBeforeComments(true);
		cfg.cfgSeparateSections(true);
	}
	
	
	/**
	 * populate config with keys from a key setup
	 * 
	 * @param keys key setup to add
	 */
	public static void registerKeys(KeySetup keys)
	{
		keys.addKeys(Config.keyOpts);
	}
	
	
	/**
	 * Populate config with options from a config setup
	 * 
	 * @param conf config setup to add
	 */
	public static void registerOptions(ConfigSetup conf)
	{
		conf.addOptions(Config.cfg);
	}
	
	
	/**
	 * Load config from file
	 */
	public static void load()
	{
		cfg.load();
	}
	
	
	/**
	 * Save config to file
	 */
	public static void save()
	{
		Log.f3("Saving config.");
		cfg.save();
	}
	
	
	/**
	 * Get an option for key
	 * 
	 * @param key
	 * @return option value
	 */
	public static <T> T getValue(String key)
	{
		try {
			if (cfg.getProperty(key) == null) {
				throw new IllegalArgumentException("No such property: " + key);
			}
			
			return cfg.getValue(key);
		} catch (final ClassCastException cce) {
			throw new RuntimeException("Property of incompatible type: " + key);
		}
	}
	
	
	/**
	 * Set option to a value
	 * 
	 * @param key option key
	 * @param value value to set
	 */
	public static <T> void setValue(String key, T value)
	{
		if (cfg.getProperty(key) == null) {
			throw new IllegalArgumentException("No such property: " + key);
		}
		
		cfg.setValue(key, value);
	}
	
	
	/**
	 * Add "key." before the given config file key
	 * 
	 * @param cfgKey config key
	 * @return key. + cfgKey
	 */
	static String prefixKey(String cfgKey)
	{
		return "key." + cfgKey;
	}
	
	
	/**
	 * Get keystroke for name
	 * 
	 * @param cfgKey stroke identifier in config file
	 * @return the stroke
	 */
	public static KeyStroke getKey(String cfgKey)
	{
		final KeyProperty kp = strokes.get(prefixKey(cfgKey));
		if (kp == null) {
			throw new IllegalArgumentException("No such stroke: " + cfgKey);
		}
		
		return kp.getValue();
	}
	
	
	/**
	 * Set a keystroke for name
	 * 
	 * @param cfgKey stroke identifier in config file
	 * @param key stroke key
	 * @param mod stroke modifiers
	 */
	public static void setKey(String cfgKey, int key, int mod)
	{
		final KeyProperty kp = strokes.get(prefixKey(cfgKey));
		if (kp == null) {
			throw new IllegalArgumentException("No such stroke: " + cfgKey);
		}
		
		kp.getValue().setTo(key, mod);
	}
}
