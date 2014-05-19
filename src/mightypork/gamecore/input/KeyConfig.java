package mightypork.gamecore.input;


import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.Config.ConfigSetup;
import mightypork.gamecore.util.files.config.Property;
import mightypork.gamecore.util.files.config.PropertyManager;


public class KeyConfig implements ConfigSetup {
	
	/**
	 * Key configurator
	 */
	public static interface KeySetup {
		
		public void addKeys(KeyOpts keys);
	}
	
	/**
	 * Key configurator access
	 */
	public static class KeyOpts {
		
		public void add(String cfgKey, String dataString)
		{
			strokes.put(prefixKey(cfgKey), new KeyProperty(prefixKey(cfgKey), KeyStroke.createFromDataString(dataString), null));
		}
	}
	
	
	/**
	 * Key property.<br>
	 * The stored value must be invariant ({@link KeyStroke} is mutable).
	 * 
	 * @author MightyPork
	 */
	private static class KeyProperty extends Property<KeyStroke> {
		
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
	
	
	private static KeyConfig inst = new KeyConfig();
	public static KeyOpts keyOpts = new KeyOpts();
	
	private static Map<String, KeyProperty> strokes = new HashMap<>();
	private static PropertyManager prop;
	
	
	public static void registerKeys(KeySetup layout)
	{
		layout.addKeys(keyOpts);
	}
	
	
	public static String prefixKey(String cfgKey)
	{
		return "key." + cfgKey;
	}
	
	
	/**
	 * Add key options to a config manager
	 */
	@Override
	public void addOptions(PropertyManager prop)
	{
		for (final KeyProperty kp : strokes.values()) {
			prop.putProperty(kp);
		}
		
		this.prop = prop;
	}
	
	
	public static KeyStroke get(String cfgKey)
	{
		final KeyProperty kp = strokes.get(prefixKey(cfgKey));
		if (kp == null) throw new IllegalArgumentException("No such stroke: " + cfgKey);
		return kp.getValue();
	}
	
	
	public static void set(String cfgKey, int key, int mod)
	{
		final KeyProperty kp = strokes.get(prefixKey(cfgKey));
		if (kp == null) throw new IllegalArgumentException("No such stroke: " + cfgKey);
		
		kp.getValue().setTo(key, mod);
		prop.save();
	}
	
	
	public static KeyConfig inst()
	{
		return inst;
	}
}
