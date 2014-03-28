package mightypork.utils.objects;


/**
 * Mutable object
 * 
 * @author MightyPork
 * @param <T> type
 */
public class Mutable<T> {

	/** The wrapped value */
	public T o = null;


	/**
	 * Implicint constructor
	 */
	public Mutable() {}


	/**
	 * new mutable object
	 * 
	 * @param o value
	 */
	public Mutable(T o) {
		this.o = o;
	}


	/**
	 * Get the wrapped value
	 * 
	 * @return value
	 */
	public T get()
	{
		return o;
	}


	/**
	 * Set value
	 * 
	 * @param o new value to set
	 */
	public void set(T o)
	{
		this.o = o;
	}
}
