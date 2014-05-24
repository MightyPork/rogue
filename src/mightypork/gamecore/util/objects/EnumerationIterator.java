package mightypork.gamecore.util.objects;


import java.util.Enumeration;
import java.util.Iterator;


/**
 * Helper class for iterationg over an {@link Enumeration}
 * 
 * @author Ondřej Hruška
 * @param <T> target element type (will be cast)
 */
public class EnumerationIterator<T> implements Iterable<T> {
	
	private final Enumeration<? extends T> enumeration;
	
	
	public EnumerationIterator(Enumeration<? extends T> enumeration)
	{
		this.enumeration = enumeration;
	}
	
	
	@Override
	public Iterator<T> iterator()
	{
		return new Iterator<T>() {
			
			@Override
			public boolean hasNext()
			{
				return enumeration.hasMoreElements();
			}
			
			
			@Override
			public T next()
			{
				return enumeration.nextElement();
			}
			
			
			@Override
			public void remove()
			{
				throw new UnsupportedOperationException("Operation not supported.");
			}
		};
	}
	
}
