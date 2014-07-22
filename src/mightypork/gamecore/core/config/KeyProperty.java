package mightypork.gamecore.core.config;

import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.utils.files.config.Property;

/**
 * Key property.<br>
 * The stored value must be invariant ({@link KeyStroke} is mutable).
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class KeyProperty extends Property<KeyStroke> {
	
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