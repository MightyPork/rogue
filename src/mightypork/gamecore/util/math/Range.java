package mightypork.gamecore.util.math;


import java.util.Random;


/**
 * Numeric range, able to generate random numbers and give min/max values.
 * 
 * @author MightyPork
 */
public class Range {
	
	public static Range make(double low, double high)
	{
		return new Range(low, high);
	}
	
	private double min = 0;
	private double max = 1;
	
	private static Random rand = new Random();
	
	
	/**
	 * Implicit range constructor 0-1
	 */
	public Range()
	{
	}
	
	
	/**
	 * Create new range
	 * 
	 * @param min min number
	 * @param max max number
	 */
	public Range(double min, double max)
	{
		this.min = min;
		this.max = max;
		norm();
	}
	
	
	/**
	 * Create new range
	 * 
	 * @param minmax min = max number
	 */
	public Range(double minmax)
	{
		this.min = minmax;
		this.max = minmax;
	}
	
	
	/**
	 * Make sure min is <= max
	 */
	private void norm()
	{
		if (min > max) {
			final double t = min;
			min = max;
			max = t;
		}
	}
	
	
	/**
	 * Get random integer from range
	 * 
	 * @return random int
	 */
	public int randInt()
	{
		return (int) (Math.round(min) + rand.nextInt((int) (Math.round(max) - Math.round(min)) + 1));
	}
	
	
	/**
	 * Get random double from this range
	 * 
	 * @return random double
	 */
	public double randDouble()
	{
		return min + rand.nextDouble() * (max - min);
	}
	
	
	/**
	 * Get min
	 * 
	 * @return min number
	 */
	public double getMin()
	{
		return min;
	}
	
	
	/**
	 * Get max
	 * 
	 * @return max number
	 */
	public double getMax()
	{
		return max;
	}
	
	
	/**
	 * Set min
	 * 
	 * @param min min value
	 */
	public void setMin(double min)
	{
		this.min = min;
		norm();
	}
	
	
	/**
	 * Set max
	 * 
	 * @param max max value
	 */
	public void setMax(double max)
	{
		this.max = max;
		norm();
	}
	
	
	@Override
	public String toString()
	{
		return String.format("{%.1f|%.1f}", min, max);
	}
	
	
	/**
	 * Get identical copy
	 * 
	 * @return copy
	 */
	public Range copy()
	{
		return new Range(min, max);
	}
	
	
	/**
	 * Set to value of other range
	 * 
	 * @param other copied range
	 */
	public void setTo(Range other)
	{
		if (other == null) return;
		min = other.min;
		max = other.max;
		norm();
	}
	
	
	/**
	 * Set to min-max values
	 * 
	 * @param min min value
	 * @param max max value
	 */
	public void setTo(double min, double max)
	{
		this.min = min;
		this.max = max;
		norm();
	}
}
