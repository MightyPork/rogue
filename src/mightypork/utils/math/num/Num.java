package mightypork.utils.math.num;


import mightypork.utils.annotations.FactoryMethod;
import mightypork.utils.math.Calc;
import mightypork.utils.math.constraints.NumBound;


public abstract class Num implements NumBound {
	
	static final double CMP_EPSILON = 0.0000001;
	
	public static final NumConst ZERO = Num.make(0);
	public static final NumConst ONE = Num.make(1);
	
	
	@FactoryMethod
	public static NumConst make(double value)
	{
		return new NumConst(value);
	}
	
	
	@FactoryMethod
	public static NumVar makeVar()
	{
		return makeVar(0);
	}
	
	
	@FactoryMethod
	public static NumVar makeVar(double value)
	{
		return new NumVar(value);
	}
	
	
	@FactoryMethod
	public static NumVar makeVar(Num copied)
	{
		return new NumVar(eval(copied));
	}
	
	private Num p_ceil;
	private Num p_floor;
	private Num p_sgn;
	private Num p_round;
	private Num p_atan;
	private Num p_acos;
	private Num p_asin;
	private Num p_tan;
	private Num p_cos;
	private Num p_sin;
	private Num p_cbrt;
	private Num p_sqrt;
	private Num p_cube;
	private Num p_square;
	private Num p_neg;
	private Num p_abs;
	
	
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
	 * 
	 * @param a numeric bound
	 * @return num
	 */
	protected static Num toNum(final NumBound a)
	{
		return (a == null) ? Num.ZERO : (a.getNum() == null ? Num.ZERO : a.getNum());
	}
	
	
	public NumConst freeze()
	{
		return new NumConst(value());
	}
	
	
	@Override
	public Num getNum()
	{
		return this;
	}
	
	
	/**
	 * @return the number
	 */
	public abstract double value();
	
	
	public Num abs()
	{
		if (p_abs == null) p_abs = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.abs(t.value());
			}
		};
		
		return p_abs;
	}
	
	
	public Num acos()
	{
		if (p_acos == null) p_acos = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.acos(t.value());
			}
		};
		
		return p_acos;
	}
	
	
	public Num add(final double addend)
	{
		return new Num() {
			
			private final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return t.value() + addend;
			}
		};
	}
	
	
	public Num add(final Num addend)
	{
		return new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return t.value() + eval(addend);
			}
		};
	}
	
	
	public Num asin()
	{
		if (p_asin == null) p_asin = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.asin(t.value());
			}
		};
		
		return p_asin;
	}
	
	
	public Num atan()
	{
		if (p_atan == null) p_atan = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.atan(t.value());
			}
		};
		
		return p_atan;
	}
	
	
	public Num average(final double other)
	{
		return new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return (t.value() + other) / 2;
			}
		};
	}
	
	
	public Num average(final Num other)
	{
		return new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return (t.value() + eval(other)) / 2;
			}
		};
	}
	
	
	public Num cbrt()
	{
		if (p_cbrt == null) p_cbrt = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.cbrt(t.value());
			}
		};
		
		return p_cbrt;
	}
	
	
	public Num ceil()
	{
		if (p_ceil == null) p_ceil = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.round(t.value());
			}
		};
		
		return p_ceil;
	}
	
	
	public Num cos()
	{
		if (p_cos == null) p_cos = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.cos(t.value());
			}
		};
		
		return p_cos;
	}
	
	
	public Num cube()
	{
		if (p_cube == null) p_cube = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				final double v = t.value();
				return v * v * v;
			}
		};
		
		return p_cube;
	}
	
	
	public Num div(final double factor)
	{
		return mul(1 / factor);
	}
	
	
	public Num div(final Num factor)
	{
		
		return new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return t.value() / eval(factor);
			}
		};
	}
	
	
	public boolean eq(double other)
	{
		return value() == other;
	}
	
	
	public boolean eq(final Num a)
	{
		return eq(eval(a));
	}
	
	
	public Num floor()
	{
		if (p_floor == null) p_floor = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.floor(t.value());
			}
		};
		
		return p_floor;
	}
	
	
	public boolean gt(double other)
	{
		return Math.signum(value() - other) >= 0;
	}
	
	
	public boolean gt(final Num other)
	{
		return gt(eval(other));
	}
	
	
	public boolean gte(double other)
	{
		return Math.signum(value() - other) >= 0;
	}
	
	
	public boolean gte(final Num other)
	{
		return gte(eval(other));
	}
	
	
	public Num half()
	{
		return mul(0.5);
	}
	
	
	public boolean isNegative()
	{
		return value() < 0;
	}
	
	
	public boolean isPositive()
	{
		return value() > 0;
	}
	
	
	public boolean isZero()
	{
		return value() == 0;
	}
	
	
	public boolean lt(double other)
	{
		return !gte(other);
	}
	
	
	public boolean lt(final Num other)
	{
		return !gte(other);
	}
	
	
	public boolean lte(double other)
	{
		return !gt(other);
	}
	
	
	public boolean lte(final Num other)
	{
		return !gt(other);
	}
	
	
	public Num max(final double other)
	{
		return new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.max(t.value(), other);
			}
		};
	}
	
	
	public Num max(final Num other)
	{
		return new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.max(t.value(), eval(other));
			}
		};
	}
	
	
	public Num min(final double other)
	{
		return new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.min(t.value(), other);
			}
		};
	}
	
	
	public Num min(final Num other)
	{
		return new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.min(t.value(), eval(other));
			}
		};
	}
	
	
	public Num mul(final double factor)
	{
		return new Num() {
			
			private final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return t.value() * factor;
			}
		};
	}
	
	
	public Num mul(final Num factor)
	{
		
		return new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return t.value() * eval(factor);
			}
		};
	}
	
	
	public Num neg()
	{
		if (p_neg == null) p_neg = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return -1 * t.value();
			}
		};
		
		return p_neg;
	}
	
	
	public Num perc(final double percent)
	{
		return mul(percent / 100);
	}
	
	
	public Num perc(final Num percent)
	{
		return new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return t.value() * (eval(percent) / 100);
			}
		};
	}
	
	
	public Num pow(final double other)
	{
		return new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.pow(t.value(), other);
			}
		};
	}
	
	
	public Num pow(final Num power)
	{
		return new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.pow(t.value(), eval(power));
			}
		};
	}
	
	
	public Num round()
	{
		if (p_round == null) p_round = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.round(t.value());
			}
		};
		
		return p_round;
	}
	
	
	public Num signum()
	{
		if (p_sgn == null) p_sgn = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.signum(t.value());
			}
		};
		
		return p_sgn;
	}
	
	
	public Num sin()
	{
		if (p_sin == null) p_sin = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.sin(t.value());
			}
		};
		
		return p_sin;
	}
	
	
	public Num sqrt()
	{
		if (p_sqrt == null) p_sqrt = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.sqrt(t.value());
			}
		};
		
		return p_sqrt;
	}
	
	
	public Num square()
	{
		if (p_square == null) p_square = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				final double v = t.value();
				return v * v;
			}
		};
		
		return p_square;
	}
	
	
	public Num sub(final double subtrahend)
	{
		return add(-subtrahend);
	}
	
	
	public Num sub(final Num subtrahend)
	{
		return new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return t.value() - eval(subtrahend);
			}
		};
	}
	
	
	public Num tan()
	{
		if (p_tan == null) p_tan = new Num() {
			
			final Num t = Num.this;
			
			
			@Override
			public double value()
			{
				return Math.tan(t.value());
			}
		};
		
		return p_tan;
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
		if (!(obj instanceof Num)) return false;
		final Num other = (Num) obj;
		
		return eq(other);
	}
	
	
	@Override
	public String toString()
	{
		return Calc.toString(value());
	}
}
