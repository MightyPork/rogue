package mightypork.utils.math.num;


public abstract class NumMathStatic<N extends NumMathStatic<N>> extends NumMathBase<N> {
	
	protected abstract N result(double a);
	
	
	@Override
	public N add(double addend)
	{
		return result(value() + addend);
	}
	
	
	@Override
	public N sub(double subtrahend)
	{
		return add(-subtrahend);
	}
	
	
	@Override
	public N mul(double factor)
	{
		return result(value() * factor);
	}
	
	
	@Override
	public N div(double factor)
	{
		return mul(1 / factor);
	}
	
	
	@Override
	public N perc(double percents)
	{
		return mul(percents / 100);
	}
	
	
	@Override
	public N neg()
	{
		return mul(-1);
	}
	
	
	@Override
	public N abs()
	{
		return result(Math.abs(value()));
	}
	
	
	@Override
	public N max(double other)
	{
		return result(Math.max(value(), other));
	}
	
	
	@Override
	public N min(double other)
	{
		return result(Math.min(value(), other));
	}
	
	
	@Override
	public N pow(double power)
	{
		return result(Math.pow(value(), power));
	}
	
	
	@Override
	public N square()
	{
		final double v = value();
		return result(v * v);
	}
	
	
	@Override
	public N cube()
	{
		final double v = value();
		return result(v * v * v);
	}
	
	
	@Override
	public N sqrt()
	{
		return result(Math.sqrt(value()));
	}
	
	
	@Override
	public N cbrt()
	{
		return result(Math.cbrt(value()));
	}
	
	
	@Override
	public N sin()
	{
		return result(Math.sin(value()));
	}
	
	
	@Override
	public N cos()
	{
		return result(Math.cos(value()));
	}
	
	
	@Override
	public N tan()
	{
		return result(Math.tan(value()));
	}
	
	
	@Override
	public N asin()
	{
		return result(Math.asin(value()));
	}
	
	
	@Override
	public N acos()
	{
		return result(Math.acos(value()));
	}
	
	
	@Override
	public N atan()
	{
		return result(Math.atan(value()));
	}
	
	
	@Override
	public N signum()
	{
		return result(Math.signum(value()));
	}
	
	
	@Override
	public N average(double other)
	{
		return result((value() + other) / 2);
	}
	
	
	@Override
	public N round()
	{
		return result(Math.round(value()));
	}
	
	
	@Override
	public N ceil()
	{
		return result(Math.ceil(value()));
	}
	
	
	@Override
	public N floor()
	{
		return result(Math.floor(value()));
	}
	
	
	@Override
	public N half()
	{
		return mul(0.5);
	}
	
	
	@Override
	public N add(final Num addend)
	{
		return add(eval(addend));
	}
	
	
	@Override
	public N sub(final Num subtrahend)
	{
		return sub(eval(subtrahend));
	}
	
	
	@Override
	public N mul(final Num factor)
	{
		return mul(eval(factor));
	}
	
	
	@Override
	public N div(final Num factor)
	{
		return div(eval(factor));
	}
	
	
	@Override
	public N perc(final Num percent)
	{
		return perc(eval(percent));
	}
	
	
	@Override
	public N max(final Num other)
	{
		return min(eval(other));
	}
	
	
	@Override
	public N min(final Num other)
	{
		return min(eval(other));
	}
	
	
	@Override
	public N pow(final Num power)
	{
		return pow(eval(power));
	}
	
	
	@Override
	public N average(final Num other)
	{
		return average(eval(other));
	}
	
	
	@Override
	public String toString()
	{
		return String.format("{%.1f}", value());
	}
}
