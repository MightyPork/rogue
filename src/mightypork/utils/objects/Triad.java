package mightypork.utils.objects;

import mightypork.utils.math.Calc;


/**
 * Structure of 3 objects.
 * 
 * @author MightyPork
 * @copy (c) 2012
 * @param <T1> 1st object class
 * @param <T2> 2nd object class
 * @param <T3> 3rd object class
 */
public class Triad<T1, T2, T3> extends Pair<T1, T2> {
	
	/**
	 * 3rd object
	 */
	public T3 third;
	
	
	/**
	 * Make structure of 3 objects
	 * 
	 * @param objA 1st object
	 * @param objB 2nd object
	 * @param objC 3rd object
	 */
	public Triad(T1 objA, T2 objB, T3 objC) {
		super(objA, objB);
		third = objC;
	}
	
	
	/**
	 * @return 3rd object
	 */
	public T3 getThird()
	{
		return third;
	}
	
	
	/**
	 * Set 1st object
	 * 
	 * @param obj 1st object
	 */
	public void setThird(T3 obj)
	{
		third = obj;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (!super.equals(obj)) return false;
		
		return Calc.areObjectsEqual(third, ((Triad<?, ?, ?>) obj).third);
		
	}
	
	
	@Override
	public int hashCode()
	{
		return super.hashCode() + (third == null ? 0 : third.hashCode());
	}
	
	
	@Override
	public String toString()
	{
		return "TRIAD{" + first + "," + second + "," + third + "}";
	}
	
}
