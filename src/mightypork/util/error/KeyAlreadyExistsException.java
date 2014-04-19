package mightypork.util.error;


public class KeyAlreadyExistsException extends RuntimeException {
	
	public KeyAlreadyExistsException()
	{
		super();
	}
	
	
	public KeyAlreadyExistsException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	
	public KeyAlreadyExistsException(String message)
	{
		super(message);
	}
	
	
	public KeyAlreadyExistsException(Throwable cause)
	{
		super(cause);
	}
	
}
