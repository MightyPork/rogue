package mightypork.gamecore;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.util.files.config.Property;
import mightypork.gamecore.util.files.config.PropertyManager;


/**
 * Static application config file accessor
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
		
		public void add(String cfgKey, String dataString)
		{
			KeyProperty kprop = new KeyProperty(prefixKey(cfgKey), KeyStroke.createFromDataString(dataString), null);
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
		
		public KeyProperty(String key, KeyStroke defaultValue, String comment)
		{
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
	public static Map<String, Config.KeyProperty> strokes = new HashMap<>();
	
	private static PropertyManager cfg;
	
	
	public static void init(File file, String comment)
	{
		cfg = new PropertyManager(file, comment);
	}
	

	public static void registerKeys(KeySetup layout)
	{
		layout.addKeys(keyOpts);
	}
	
	
	
	public static void registerOptions(ConfigSetup cfgl)
	{
		cfgl.addOptions(cfg);
	}
	
	
	public static void load()
	{
		cfg.load();
	}
	
	
	public static void save()
	{
		cfg.save();
	}
	
	
	public static <T> T getOption(String key)
	{
		return cfg.getValue(key);
	}
	
	
	public static <T> void setOption(String key, T value)
	{
		cfg.setValue(key, value);
	}
	

	private static String prefixKey(String cfgKey)
	{
		return "key." + cfgKey;
	}
	
	
	public static KeyStroke getKey(String cfgKey)
	{
		final Config.KeyProperty kp = strokes.get(prefixKey(cfgKey));
		if (kp == null) throw new IllegalArgumentException("No such stroke: " + cfgKey);
		return kp.getValue();
	}
	
	
	public static void setKey(String cfgKey, int key, int mod)
	{
		final Config.KeyProperty kp = strokes.get(prefixKey(cfgKey));
		if (kp == null) throw new IllegalArgumentException("No such stroke: " + cfgKey);
		
		kp.getValue().setTo(key, mod);
	}
}
