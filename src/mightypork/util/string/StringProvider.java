package mightypork.util.string;


/**
 * Can be used for dynamic string generating
 * 
 * @author MightyPork
 */
public interface StringProvider {
	
	String getString();
	
	/**
	 * String provider with constant string
	 * 
	 * @author MightyPork
	 */
	public static class StringWrapper implements StringProvider {
		
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
}
