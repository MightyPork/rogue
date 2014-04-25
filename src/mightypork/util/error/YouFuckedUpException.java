package mightypork.util.error;


public class YouFuckedUpException extends RuntimeException {
	
	public YouFuckedUpException()
	{
		super();
	}
	
	
	public YouFuckedUpException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	
	public YouFuckedUpException(String message)
	{
		super(message);
	}
	
	
	public YouFuckedUpException(Throwable cause)
	{
		super(cause);
	}
	
}
