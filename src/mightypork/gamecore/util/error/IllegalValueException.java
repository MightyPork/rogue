package mightypork.gamecore.util.error;


/**
 * Thrown when a invalid value is given to a method, or found in a data object /
 * file etc
 * 
 * @author Ondřej Hruška
 */
public class IllegalValueException extends RuntimeException {
	
	public IllegalValueException()
	{
	}
	
	
	public IllegalValueException(String message)
	{
		super(message);
	}
	
	
	public IllegalValueException(Throwable cause)
	{
		super(cause);
	}
	
	
	public IllegalValueException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
}
