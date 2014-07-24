package mightypork.gamecore.core.config;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.input.KeyStroke;
import mightypork.utils.files.config.Property;
import mightypork.utils.files.config.PropertyManager;
import mightypork.utils.logging.Log;


/**
 * Static application configuration; wrapper around {@link PropertyManager}
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Config {
	
	private static Map<String, KeyProperty> strokes = new HashMap<>();
	
	private static PropertyManager propertyManager;
	
	
	/**
	 * Initialize property manger for a file
	 * 
	 * @param file config file
	 * @param headComment file comment
	 */
	public static void init(File file, String headComment)
	{
		propertyManager = new PropertyManager(file, headComment);
		
		propertyManager.cfgNewlineBeforeComments(true);
		propertyManager.cfgSeparateSections(true);
	}
	
	
	/**
	 * Add a keystroke property
	 * 
	 * @param key key in config file
	 * @param defval default value (keystroke datastring)
	 * @param comment optional comment, can be null
	 */
	public void addKeyProperty(String key, String defval, String comment)
	{
		final KeyProperty kprop = new KeyProperty(Config.prefixKey(key), KeyStroke.createFromDataString(defval), comment);
		strokes.put(Config.prefixKey(key), kprop);
		propertyManager.putProperty(kprop);
	}
	
	
	/**
	 * Add a boolean property (flag)
	 * 
	 * @param key key in config file
	 * @param defval default value
	 * @param comment optional comment, can be null
	 */
	public void addBooleanProperty(String key, boolean defval, String comment)
	{
		propertyManager.putBoolean(key, defval, comment);
	}
	
	
	/**
	 * Add an integer property
	 * 
	 * @param key key in config file
	 * @param defval default value
	 * @param comment optional comment, can be null
	 */
	public void addIntegerProperty(String key, int defval, String comment)
	{
		propertyManager.putInteger(key, defval, comment);
	}
	
	
	/**
	 * Add a double property
	 * 
	 * @param key key in config file
	 * @param defval default value
	 * @param comment optional comment, can be null
	 */
	public void addDoubleProperty(String key, double defval, String comment)
	{
		propertyManager.putDouble(key, defval, comment);
	}
	
	
	/**
	 * Add a string property
	 * 
	 * @param key key in config file
	 * @param defval default value
	 * @param comment optional comment, can be null
	 */
	public void addStringProperty(String key, String defval, String comment)
	{
		propertyManager.putString(key, defval, comment);
	}
	
	
	/**
	 * Add an arbitrary property (can be custom type)
	 * 
	 * @param prop the property to add
	 */
	public <T> void addProperty(Property<T> prop)
	{
		propertyManager.putProperty(prop);
	}
	
	
	/**
	 * Load config from file
	 */
	public static void load()
	{
		propertyManager.load();
	}
	
	
	/**
	 * Save config to file
	 */
	public static void save()
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
	public static <T> T getValue(String key)
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
	public static <T> void setValue(String key, T value)
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
		final KeyProperty kp = strokes.get(prefixKey(cfgKey));
		if (kp == null) {
			throw new IllegalArgumentException("No such stroke: " + cfgKey);
		}
		
		kp.getValue().setTo(key, mod);
	}
}
