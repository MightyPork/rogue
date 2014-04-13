package mightypork.utils.math.num;


import mightypork.utils.math.rect.RectView;


abstract class NumMathDynamic extends NumMathBase<NumView> {
	
	private NumView ceil;
	private NumView floor;
	private NumView sgn;
	private NumView round;
	private NumView atan;
	private NumView acos;
	private NumView asin;
	private NumView tan;
	private NumView cos;
	private NumView sin;
	private NumView cbrt;
	private NumView sqrt;
	private NumView cube;
	private NumView square;
	private NumView neg;
	private NumView abs;
	
	
	@Override
	public NumView add(final double addend)
	{
		return new NumView() {
			
			private final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return t.value() + addend;
			}
		};
	}
	
	
	@Override
	public NumView sub(final double subtrahend)
	{
		return add(-subtrahend);
	}
	
	
	@Override
	public NumView mul(final double factor)
	{
		return new NumView() {
			
			private final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return t.value() + factor;
			}
		};
	}
	
	
	@Override
	public NumView div(final double factor)
	{
		return mul(1 / factor);
	}
	
	
	@Override
	public NumView perc(final double percent)
	{
		return mul(percent / 100);
	}
	
	
	@Override
	public NumView neg()
	{
		if (neg == null) neg = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return -1 * t.value();
			}
		};
		
		return neg;
	}
	
	
	@Override
	public NumView abs()
	{
		if (abs == null) abs = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.abs(t.value());
			}
		};
		
		return abs;
	}
	
	
	@Override
	public NumView max(final double other)
	{
		return new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.max(t.value(), other);
			}
		};
	}
	
	
	@Override
	public NumView min(final double other)
	{
		return new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.min(t.value(), other);
			}
		};
	}
	
	
	@Override
	public NumView pow(final double other)
	{
		return new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.pow(t.value(), other);
			}
		};
	}
	
	
	@Override
	public NumView square()
	{
		if (square == null) square = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				final double v = t.value();
				return v * v;
			}
		};
		
		return square;
	}
	
	
	@Override
	public NumView cube()
	{
		if (cube == null) cube = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				final double v = t.value();
				return v * v * v;
			}
		};
		
		return cube;
	}
	
	
	@Override
	public NumView sqrt()
	{
		if (sqrt == null) sqrt = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.sqrt(t.value());
			}
		};
		
		return sqrt;
	}
	
	
	@Override
	public NumView cbrt()
	{
		if (cbrt == null) cbrt = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.cbrt(t.value());
			}
		};
		
		return cbrt;
	}
	
	
	@Override
	public NumView sin()
	{
		if (sin == null) sin = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.sin(t.value());
			}
		};
		
		return sin;
	}
	
	
	@Override
	public NumView cos()
	{
		if (cos == null) cos = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.cos(t.value());
			}
		};
		
		return cos;
	}
	
	
	@Override
	public NumView tan()
	{
		if (tan == null) tan = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.tan(t.value());
			}
		};
		
		return tan;
	}
	
	
	@Override
	public NumView asin()
	{
		if (asin == null) asin = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.asin(t.value());
			}
		};
		
		return asin;
	}
	
	
	@Override
	public NumView acos()
	{
		if (acos == null) acos = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.acos(t.value());
			}
		};
		
		return acos;
	}
	
	
	@Override
	public NumView atan()
	{
		if (atan == null) atan = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.atan(t.value());
			}
		};
		
		return atan;
	}
	
	
	@Override
	public NumView round()
	{
		if (round == null) round = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.round(t.value());
			}
		};
		
		return round;
	}
	
	
	@Override
	public NumView floor()
	{
		if (floor == null) floor = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.floor(t.value());
			}
		};
		
		return floor;
	}
	
	
	@Override
	public NumView ceil()
	{
		if (ceil == null) ceil = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.round(t.value());
			}
		};
		
		return ceil;
	}
	
	
	@Override
	public NumView signum()
	{
		if (sgn == null) sgn = new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.signum(t.value());
			}
		};
		
		return sgn;
	}
	
	
	@Override
	public NumView average(final double other)
	{
		return null;
	}
	
	
	@Override
	public NumView half()
	{
		return mul(0.5);
	}
	
	
	@Override
	public NumView add(final Num addend)
	{
		return new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return t.value() + eval(addend);
			}
		};
	}
	
	
	@Override
	public NumView sub(final Num subtrahend)
	{
		return new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return t.value() - eval(subtrahend);
			}
		};
	}
	
	
	@Override
	public NumView mul(final Num factor)
	{
		
		return new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return t.value() * eval(factor);
			}
		};
	}
	
	
	@Override
	public NumView div(final Num factor)
	{
		
		return new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return t.value() / eval(factor);
			}
		};
	}
	
	
	@Override
	public NumView perc(final Num percent)
	{
		return new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return t.value() * (eval(percent) / 100);
			}
		};
	}
	
	
	@Override
	public NumView max(final Num other)
	{
		return new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.max(t.value(), eval(other));
			}
		};
	}
	
	
	@Override
	public NumView min(final Num other)
	{
		return new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.min(t.value(), eval(other));
			}
		};
	}
	
	
	@Override
	public NumView pow(final Num power)
	{
		return new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return Math.pow(t.value(), eval(power));
			}
		};
	}
	
	
	@Override
	public NumView average(final Num other)
	{
		return new NumView() {
			
			final Num t = NumMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return (t.value() + eval(other)) / 2;
			}
		};
	}
	
	
	@Override
	public RectView box()
	{
		return RectView.make(this, this);
	}
}
