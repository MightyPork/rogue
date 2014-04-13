package mightypork.utils.math.num;

import mightypork.utils.math.constraints.NumBound;


public abstract class NumMathBase<N extends NumMath<N>> extends AbstractNum implements NumMath<N> {
	
	/**
	 * Convert to double, turning null into zero.
	 * 
	 * @param a num
	 * @return double
	 */
	protected static double eval(final NumBound a)
	{
		return toNum(a).value();
	}
	
	
	/**
	 * Convert {@link NumBound} to {@link Num}, turning null to Num.ZERO.
	 * @param a numeric bound
	 * @return num
	 */
	protected static Num toNum(final NumBound a)
	{
		return (a == null) ? Num.ZERO : (a.getNum() == null ? Num.ZERO : a.getNum());
	}
	
	
	@Override
	public Num getNum()
	{
		return this;
	}
	
	
	@Override
	public boolean lt(double other)
	{
		return !gte(other);
	}
	
	
	@Override
	public boolean lt(final Num other)
	{
		return !gte(other);
	}
	
	
	@Override
	public boolean lte(double other)
	{
		return !gt(other);
	}
	
	
	@Override
	public boolean lte(final Num other)
	{
		return !gt(other);
	}
	
	
	@Override
	public boolean gt(double other)
	{
		return Math.signum(value() - other) >= 0;
	}
	
	
	@Override
	public boolean gt(final Num other)
	{
		return gt(eval(other));
	}
	
	
	@Override
	public boolean gte(double other)
	{
		return Math.signum(value() - other) >= 0;
	}
	
	
	@Override
	public boolean gte(final Num other)
	{
		return gte(eval(other));
	}
	
	
	@Override
	public boolean eq(double other)
	{
		return Math.abs(value() - other) <= CMP_EPSILON;
	}
	
	
	@Override
	public boolean eq(final Num a)
	{
		return eq(eval(a));
	}
	
	
	@Override
	public boolean isNegative()
	{
		return Math.signum(value()) < 0;
	}
	
	
	@Override
	public boolean isPositive()
	{
		return Math.signum(value()) > 0;
	}
	
	
	@Override
	public boolean isZero()
	{
		return Math.abs(value()) <= CMP_EPSILON;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof NumMathBase)) return false;
		NumMathBase<?> other = (NumMathBase<?>) obj;
		
		return eq(other);
	}
}
