package mightypork.util.error;


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
	
	
	public IllegalValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
