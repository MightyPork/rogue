package mightypork.utils.objects;


import mightypork.utils.math.Calc;


/**
 * Structure of 2 objects.
 * 
 * @author MightyPork
 * @copy (c) 2012
 * @param <T1> 1st object class
 * @param <T2> 2nd object class
 */
public class Pair<T1, T2> {
	
	/**
	 * 1st object
	 */
	public T1 first;
	
	/**
	 * 2nd object
	 */
	public T2 second;
	
	
	/**
	 * Make structure of 2 objects
	 * 
	 * @param first 1st object
	 * @param second 2nd object
	 */
	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}
	
	
	/**
	 * @return 1st object
	 */
	public T1 getFirst()
	{
		return first;
	}
	
	
	/**
	 * @return 2nd object
	 */
	public T2 getSecond()
	{
		return second;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) {
			return false;
		}
		
		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}
		
		Pair<?, ?> t = (Pair<?, ?>) obj;
		
		return Calc.areObjectsEqual(first, t.first) && Calc.areObjectsEqual(second, t.second);
		
	}
	
	
	@Override
	public int hashCode()
	{
		int hash = 13;
		hash += (first == null ? 0 : first.hashCode());
		hash += (second == null ? 0 : second.hashCode());
		return hash;
	}
	
	
	@Override
	public String toString()
	{
		return "PAIR{" + first + "," + second + "}";
	}
	
}
