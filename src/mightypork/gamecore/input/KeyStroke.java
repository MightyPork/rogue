package mightypork.gamecore.input;


import mightypork.utils.string.StringUtil;


/**
 * Key stroke
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class KeyStroke {
	
	private byte mod;
	private Key key;
	
	
	/**
	 * Create a Key Stroke
	 * 
	 * @param key key code
	 * @param modmask modifiers
	 */
	public KeyStroke(Key key, int modmask) {
		setTo(key, modmask);
	}
	
	
	/**
	 * Change to...<br>
	 * (KeyStroke is mutable, so that upon changing it in Config, all existing
	 * key bindings are updated automatically.)
	 * 
	 * @param key key code
	 * @param modmask modifiers
	 */
	public void setTo(Key key, int modmask)
	{
		this.key = key;
		this.mod = (byte) (modmask | Keys.keyToMod(key)); // for mods alone
	}
	
	
	/**
	 * Create a new keystroke without modifiers
	 * 
	 * @param key key
	 */
	public KeyStroke(Key key) {
		this(key, Keys.MOD_NONE);
	}
	
	
	/**
	 * Get if the key is down and modifiers match
	 * 
	 * @return true if the key is currently down & modifiers match
	 */
	public boolean isDown()
	{
		return key.isDown() && (Keys.getActiveMod() == mod);
	}
	
	
	public String saveToString()
	{
		return Keys.modToString(mod) + "+" + key.getName();
	}
	
	
	public static KeyStroke createFromString(String dataString)
	{
		final KeyStroke ks = new KeyStroke(Keys.NONE, Keys.MOD_NONE);
		ks.loadFromString(dataString);
		return ks;
	}
	
	
	public void loadFromString(String dataString)
	{
		final String dataString1 = dataString.toUpperCase().replace('-', '+').replaceAll("[^A-Z0-9_+]", "");
		
		if (dataString1.contains("+")) {
			
			final String keyStr = StringUtil.fromLastChar(dataString1, '+');
			final String modStr = StringUtil.toLastChar(dataString1, '+');
			
			setTo(Keys.stringToKey(keyStr), Keys.stringToMod(modStr));
			
		} else {
			setTo(Keys.stringToKey(dataString1), Keys.MOD_NONE);
		}
	}
	
	
	public Key getKey()
	{
		return key;
	}
	
	
	public byte getMod()
	{
		return mod;
	}
	
	
	@Override
	public String toString()
	{
		return saveToString();
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + key.getCode();
		result = prime * result + mod;
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final KeyStroke other = (KeyStroke) obj;
		if (key.getCode() != other.key.getCode()) return false;
		if (mod != other.mod) return false;
		return true;
	}
}
