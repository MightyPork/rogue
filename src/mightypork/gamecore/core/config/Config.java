package mightypork.gamecore.core.config;


import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.core.WorkDir;
import mightypork.gamecore.input.Key;
import mightypork.gamecore.input.KeyStroke;
import mightypork.utils.config.propmgr.Property;
import mightypork.utils.config.propmgr.PropertyManager;
import mightypork.utils.config.propmgr.PropertyStore;
import mightypork.utils.config.propmgr.store.PropertyFile;
import mightypork.utils.logging.Log;


/**
 * Settings repository.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Config {
	
	protected static Map<String, Config> configs = new HashMap<>();
	
	private Map<String, KeyStrokeProperty> strokes = new HashMap<>();
	
	private PropertyManager propertyManager;
	
	
	/**
	 * Get a config from the static map, by given alias
	 * 
	 * @param alias alias
	 * @return the config
	 */
	public static Config forAlias(String alias)
	{
		Config c = configs.get(alias);
		
		if (c == null) {
			throw new IllegalArgumentException("There is no config with alias \"" + alias + "\"");
		}
		
		return c;
	}
	
	
	/**
	 * Register a config by alias.
	 * 
	 * @param alias config alias
	 * @param config the config
	 */
	public static void register(String alias, Config config)
	{
		if (configs.get(alias) != null) {
			throw new IllegalArgumentException("The alias \"" + alias + "\" is already used.");
		}
		
		configs.put(alias, config);
	}
	
	
	/**
	 * Initialize property manager for a file
	 * 
	 * @param file config file, relative to workdir
	 * @param headComment file comment
	 */
	public Config(String file, String headComment) {
		this(new PropertyFile(WorkDir.getFile(file), headComment));
	}
	
	
	/**
	 * Initialize property manager for a given store
	 * 
	 * @param store property store backing the property manager
	 */
	public Config(PropertyStore store) {
		if (propertyManager != null) {
			throw new IllegalStateException("Config already initialized.");
		}
		
		propertyManager = new PropertyManager(store);
	}
	
	
	/**
	 * Add a keystroke property
	 * 
	 * @param key key in config file
	 * @param defval default value (keystroke datastring)
	 * @param comment optional comment, can be null
	 */
	public void addKeyStroke(String key, String defval, String comment)
	{
		final KeyStrokeProperty kprop = new KeyStrokeProperty(prefixKeyStroke(key), KeyStroke.createFromString(defval), comment);
		strokes.put(prefixKeyStroke(key), kprop);
		propertyManager.addProperty(kprop);
	}
	
	
	/**
	 * Add a boolean property (flag)
	 * 
	 * @param key key in config file
	 * @param defval default value
	 * @param comment optional comment, can be null
	 */
	public void addBoolean(String key, boolean defval, String comment)
	{
		propertyManager.addBoolean(key, defval, comment);
	}
	
	
	/**
	 * Add an integer property
	 * 
	 * @param key key in config file
	 * @param defval default value
	 * @param comment optional comment, can be null
	 */
	public void addInteger(String key, int defval, String comment)
	{
		propertyManager.addInteger(key, defval, comment);
	}
	
	
	/**
	 * Add a double property
	 * 
	 * @param key key in config file
	 * @param defval default value
	 * @param comment optional comment, can be null
	 */
	public void addDouble(String key, double defval, String comment)
	{
		propertyManager.addDouble(key, defval, comment);
	}
	
	
	/**
	 * Add a string property
	 * 
	 * @param key key in config file
	 * @param defval default value
	 * @param comment optional comment, can be null
	 */
	public void addString(String key, String defval, String comment)
	{
		propertyManager.addString(key, defval, comment);
	}
	
	
	/**
	 * Add an arbitrary property (can be custom type)
	 * 
	 * @param prop the property to add
	 */
	public <T> void addProperty(Property<T> prop)
	{
		propertyManager.addProperty(prop);
	}
	
	
	/**
	 * Load config from file
	 */
	public void load()
	{
		propertyManager.load();
	}
	
	
	/**
	 * Save config to file
	 */
	public void save()
	{
		Log.f3("Saving config.");
		propertyManager.save();
	}
	
	
	/**
	 * Get an option for key
	 * 
	 * @param key
	 * @return option value
	 */
	public <T> T getValue(String key)
	{
		try {
			if (propertyManager.getProperty(key) == null) {
				throw new IllegalArgumentException("No such property: " + key);
			}
			
			return propertyManager.getValue(key);
		} catch (final ClassCastException cce) {
			throw new RuntimeException("Property of incompatible type: " + key);
		}
	}
	
	
	/**
	 * Set option to a value. Call the save() method to make the change
	 * permanent.
	 * 
	 * @param key option key
	 * @param value value to set
	 */
	public <T> void setValue(String key, T value)
	{
		if (propertyManager.getProperty(key) == null) {
			throw new IllegalArgumentException("No such property: " + key);
		}
		
		propertyManager.setValue(key, value);
	}
	
	
	/**
	 * Add "key." before the given config file key
	 * 
	 * @param cfgKey config key
	 * @return key. + cfgKey
	 */
	private String prefixKeyStroke(String cfgKey)
	{
		return "key." + cfgKey;
	}
	
	
	/**
	 * Get keystroke for name
	 * 
	 * @param cfgKey stroke identifier in config file
	 * @return the stroke
	 */
	public KeyStroke getKeyStroke(String cfgKey)
	{
		final KeyStrokeProperty kp = strokes.get(prefixKeyStroke(cfgKey));
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
	public void setKeyStroke(String cfgKey, Key key, int mod)
	{
		final KeyStrokeProperty kp = strokes.get(prefixKeyStroke(cfgKey));
		if (kp == null) {
			throw new IllegalArgumentException("No such stroke: " + cfgKey);
		}
		
		kp.getValue().setTo(key, mod);
	}
}
