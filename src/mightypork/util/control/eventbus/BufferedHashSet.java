package mightypork.util.control.eventbus;


import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * HashSet that buffers "add" and "remove" calls and performs them all at once
 * when a flush() method is called.
 * 
 * @author MightyPork
 * @param <E> element type
 */
public class BufferedHashSet<E> extends HashSet<E> {
	
	private final List<E> toAdd = new LinkedList<>();
	private final List<Object> toRemove = new LinkedList<>();
	private boolean buffering = false;
	
	
	/**
	 * make empty
	 */
	public BufferedHashSet()
	{
		super();
	}
	
	
	/**
	 * make from elements of a collection
	 * 
	 * @param c
	 */
	public BufferedHashSet(Collection<? extends E> c)
	{
		super(c);
	}
	
	
	/**
	 * make new
	 * 
	 * @param initialCapacity
	 * @param loadFactor
	 */
	public BufferedHashSet(int initialCapacity, float loadFactor)
	{
		super(initialCapacity, loadFactor);
	}
	
	
	/**
	 * make new
	 * 
	 * @param initialCapacity
	 */
	public BufferedHashSet(int initialCapacity)
	{
		super(initialCapacity);
	}
	
	
	@Override
	public boolean add(E e)
	{
		if (buffering) {
			toAdd.add(e);
		} else {
			super.add(e);
		}
		
		return true;
	}
	
	
	@Override
	public boolean remove(Object e)
	{
		if (buffering) {
			toRemove.add(e);
		} else {
			super.remove(e);
		}
		
		return true;
	}
	
	
	/**
	 * Flush all
	 */
	public void flush()
	{
		for (final E e : toAdd) {
			super.add(e);
		}
		
		for (final Object e : toRemove) {
			super.remove(e);
		}
		
		toAdd.clear();
		toRemove.clear();
	}
	
	
	@Override
	public boolean removeAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}
	
	
	@Override
	public boolean containsAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}
	
	
	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		throw new UnsupportedOperationException();
	}
	
	
	@Override
	public boolean retainAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}
	
	
	/**
	 * Toggle buffering
	 * 
	 * @param enable enable buffering
	 */
	public void setBuffering(boolean enable)
	{
		if (this.buffering && !enable) {
			flush();
		}
		
		this.buffering = enable;
	}
	
}
