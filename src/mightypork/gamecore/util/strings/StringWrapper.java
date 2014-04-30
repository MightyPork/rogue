package mightypork.gamecore.util.strings;


/**
 * String provider with constant string
 * 
 * @author MightyPork
 */
public class StringWrapper implements StringProvider {
	
	private final String value;
	
	
	public StringWrapper(String value)
	{
		this.value = value;
	}
	
	
	@Override
	public String getString()
	{
		return value;
	}
	
}
