package mightypork.rogue.world.gen;


/**
 * Error in world generation
 * 
 * @author Ondřej Hruška
 */
public class WorldGenError extends RuntimeException {
	
	public WorldGenError()
	{
		super();
	}
	
	
	public WorldGenError(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	
	public WorldGenError(String message)
	{
		super(message);
	}
	
	
	public WorldGenError(Throwable cause)
	{
		super(cause);
	}
	
}
