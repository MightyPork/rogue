package mightypork.gamecore;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.util.files.config.Property;
import mightypork.gamecore.util.files.config.PropertyManager;


/**
 * Static application configuration
 * 
 * @author MightyPork
 */
public class Config {
	
	/**
	 * Config setup. Used to populate the config file.
	 */
	public static interface ConfigSetup {
		
		void addOptions(PropertyManager prop);
	}
	
	/**
	 * Key configurator access
	 */
	public static class KeyOpts {
		
		private KeyOpts() {
		}
		
		
		public void add(String cfgKey, String dataString)
		{
			add(cfgKey, dataString, null);
		}
		
		
		public void add(String cfgKey, String dataString, String comment)
		{
			KeyProperty kprop = new KeyProperty(prefixKey(cfgKey), KeyStroke.createFromDataString(dataString), comment);
			strokes.put(prefixKey(cfgKey), kprop);
			cfg.putProperty(kprop);
		}
	}
	
	/**
	 * Key configurator
	 */
	public static interface KeySetup {
		
		public void addKeys(KeyOpts keys);
	}
	
	/**
	 * Key property.<br>
	 * The stored value must be invariant ({@link KeyStroke} is mutable).
	 * 
	 * @author MightyPork
	 */
	public static class KeyProperty extends Property<KeyStroke> {
		
		public KeyProperty(String key, KeyStroke defaultValue, String comment) {
			super(key, defaultValue, comment);
		}
		
		
		@Override
		public KeyStroke decode(String string, KeyStroke defval)
		{
			if (string != null) {
				// keep it invariant
				
				final int backup_key = getValue().getKey();
				final int backup_mod = getValue().getMod();
				
				getValue().fromDataString(string);
				if (getValue().getKey() == Keys.NONE) {
					getValue().setTo(backup_key, backup_mod);
				}
			}
			
			return getValue();
		}
		
		
		@Override
		public String encode(KeyStroke value)
		{
			return value.toDataString();
		}
		
		
		@Override
		public void setValue(Object value)
		{
			// keep it invariant
			getValue().setTo(((KeyStroke) value).getKey(), ((KeyStroke) value).getMod());
		}
	}
	
	public static Config.KeyOpts keyOpts = new Config.KeyOpts();
	public static Map<String, KeyProperty> strokes = new HashMap<>();
	
	private static PropertyManager cfg;
	
	
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
		cfg.save();
	}
	
	
	/**
	 * Get an option for key
	 * 
	 * @param key
	 * @return option value
	 */
	public static <T> T getOption(String key)
	{
		if (cfg.getProperty(key) == null) {
			throw new IllegalArgumentException("No such property: " + key);
		}
		
		return cfg.getValue(key);
	}
	
	
	/**
	 * Set option to a value
	 * 
	 * @param key option key
	 * @param value value to set
	 */
	public static <T> void setOption(String key, T value)
	{
		if (cfg.getProperty(key) == null) {
			throw new IllegalArgumentException("No such property: " + key);
		}
		
		cfg.setValue(key, value);
	}
	
	
	private static String prefixKey(String cfgKey)
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
		final Config.KeyProperty kp = strokes.get(prefixKey(cfgKey));
		if (kp == null) {
			throw new IllegalArgumentException("No such stroke: " + cfgKey);
		}
		
		kp.getValue().setTo(key, mod);
	}
}
