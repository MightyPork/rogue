package mightypork.gamecore.core.config;


import mightypork.gamecore.input.Key;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.utils.config.propmgr.Property;


/**
 * Key property.<br>
 * The stored value must stay the same instance ({@link KeyStroke} is mutable).<br>
 * That ensures that bindings based on this keystroke are automatically updated
 * when the settings change.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class KeyStrokeProperty extends Property<KeyStroke> {
	
	public KeyStrokeProperty(String key, KeyStroke defaultValue, String comment) {
		super(key, defaultValue, comment);
	}
	
	
	@Override
	public void fromString(String string)
	{
		if (string != null) {
			// keep the same instance
			
			final Key backup_key = value.getKey();
			final int backup_mod = value.getMod();
			
			value.loadFromString(string);
			if (value.getKey() == Keys.NONE) {
				value.setTo(backup_key, backup_mod);
			}
		}
	}
	
	
	@Override
	public String toString()
	{
		return value.saveToString();
	}
	
	
	@Override
	public void setValue(Object value)
	{
		// keep the same instance
		this.value.setTo(((KeyStroke) value).getKey(), ((KeyStroke) value).getMod());
	}
}
