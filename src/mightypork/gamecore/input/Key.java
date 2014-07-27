package mightypork.gamecore.input;


import java.util.HashSet;
import java.util.Set;

import mightypork.gamecore.core.App;


/**
 * Abstraction above a physical keyboard key.<br>
 * Provides name, aliases, and the {@link InputModule} may assign it a numeric
 * code that corresponds to the underlying keyboard system.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Key {
	
	private int code = -1;
	private final String name;
	private final Set<String> aliases = new HashSet<>(1);
	
	
	/**
	 * Create a key. Note that both name and aliases are converted to uppercase,
	 * and all underscores are ignored when the aliases are matched.
	 * 
	 * @param name key name (primary alias)
	 * @param aliases extra aliases (used for matching)
	 */
	public Key(String name, String... aliases) {
		
		// assign name and aliases, converting both to uppercase
		
		this.name = name;
		this.aliases.add(prepareForMatch(name));
		
		for (String al : aliases) {
			this.aliases.add(prepareForMatch(al));
		}
	}
	
	
	public boolean isDown()
	{
		return App.input().isKeyDown(this);
	}
	
	
	/**
	 * Set a key code. This can be used by the {@link InputModule} to store a
	 * numeric code in the key.
	 * 
	 * @param code a code to assign
	 */
	public void setCode(int code)
	{
		this.code = code;
	}
	
	
	/**
	 * Check if the provided alias matches this key.<br>
	 * Both the primary alias and the extra aliases are considered.
	 * 
	 * @param alias
	 * @return true if matches (this is the key)
	 */
	public boolean matches(String alias)
	{
		if (alias == null) return false;
		return aliases.contains(prepareForMatch(alias));
	}
	
	
	private String prepareForMatch(String matched)
	{
		return matched.toUpperCase().replace("_", "");
	}
	
	
	/**
	 * Get key name (primary alias).
	 * 
	 * @return name (uppercase)
	 */
	public String getName()
	{
		return name;
	}
	
	
	/**
	 * Get the numeric code assigned to this key. If none is assigned, the value
	 * is -1.
	 * 
	 * @return numeric key code.
	 */
	public int getCode()
	{
		return code;
	}
	
	
	/**
	 * Get if this key is not a NONE or undefined key.
	 * 
	 * @return true if the key is defined.
	 */
	public boolean isDefined()
	{
		return code > 0;
	}
}
