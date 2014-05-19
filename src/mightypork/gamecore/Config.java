package mightypork.gamecore;


import java.io.File;

import mightypork.gamecore.util.files.config.PropertyManager;


/**
 * Static application config file accessor
 * 
 * @author MightyPork
 */
public class Config {
	
	private static PropertyManager cfg;
	
	
	public static void init(File file, String comment)
	{
		cfg = new PropertyManager(file, comment);
	}
	
	
	public static PropertyManager getProp()
	{
		return cfg;
	}
	
	
	public static void load()
	{
		cfg.load();
	}
	
	
	public static void save()
	{
		cfg.save();
	}
	
	
	public static <T> T get(String key)
	{
		return cfg.getValue(key);
	}
	
	
	public static <T> void set(String key, T value)
	{
		cfg.setValue(key, value);
	}
	
}
