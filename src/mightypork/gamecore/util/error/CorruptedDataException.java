package mightypork.gamecore.util.error;


import java.io.IOException;


/**
 * To be used when a data could not be read successfully.
 * 
 * @author MightyPork
 */
public class CorruptedDataException extends IOException {
	
	public CorruptedDataException()
	{
		super();
	}
	
	
	public CorruptedDataException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	
	public CorruptedDataException(String message)
	{
		super(message);
	}
	
	
	public CorruptedDataException(Throwable cause)
	{
		super(cause);
	}
	
}
