package mightypork.utils.math.num;


import mightypork.utils.math.constraints.NumBound;


/**
 * Constant number {@link NumBound}
 * 
 * @author MightyPork
 */
public class NumConst extends Num {
	
	private final double value;
	
	
	NumConst(Num copied) {
		this.value = copied.value();
	}
	
	
	NumConst(double value) {
		this.value = value;
	}
	
	
	@Override
	public double value()
	{
		return value;
	}
	
	
	/**
	 * No good to copy a constant.
	 */
	@Override
	@Deprecated
	public NumConst freeze()
	{
		return this;
	}
	
	
	@Override
	public NumConst add(double addend)
	{
		return Num.make(value() + addend);
	}
	
	
	@Override
	public NumConst sub(double subtrahend)
	{
		return add(-subtrahend);
	}
	
	
	@Override
	public NumConst mul(double factor)
	{
		return Num.make(value() * factor);
	}
	
	
	@Override
	public NumConst div(double factor)
	{
		return mul(1 / factor);
	}
	
	
	@Override
	public NumConst perc(double percents)
	{
		return mul(percents / 100);
	}
	
	
	@Override
	public NumConst neg()
	{
		return mul(-1);
	}
	
	
	@Override
	public NumConst abs()
	{
		return Num.make(Math.abs(value()));
	}
	
	
	@Override
	public NumConst max(double other)
	{
		return Num.make(Math.max(value(), other));
	}
	
	
	@Override
	public NumConst min(double other)
	{
		return Num.make(Math.min(value(), other));
	}
	
	
	@Override
	public NumConst pow(double power)
	{
		return Num.make(Math.pow(value(), power));
	}
	
	
	@Override
	public NumConst square()
	{
		final double v = value();
		return Num.make(v * v);
	}
	
	
	@Override
	public NumConst cube()
	{
		final double v = value();
		return Num.make(v * v * v);
	}
	
	
	@Override
	public NumConst sqrt()
	{
		return Num.make(Math.sqrt(value()));
	}
	
	
	@Override
	public NumConst cbrt()
	{
		return Num.make(Math.cbrt(value()));
	}
	
	
	@Override
	public NumConst sin()
	{
		return Num.make(Math.sin(value()));
	}
	
	
	@Override
	public NumConst cos()
	{
		return Num.make(Math.cos(value()));
	}
	
	
	@Override
	public NumConst tan()
	{
		return Num.make(Math.tan(value()));
	}
	
	
	@Override
	public NumConst asin()
	{
		return Num.make(Math.asin(value()));
	}
	
	
	@Override
	public NumConst acos()
	{
		return Num.make(Math.acos(value()));
	}
	
	
	@Override
	public NumConst atan()
	{
		return Num.make(Math.atan(value()));
	}
	
	
	@Override
	public NumConst signum()
	{
		return Num.make(Math.signum(value()));
	}
	
	
	@Override
	public NumConst average(double other)
	{
		return Num.make((value() + other) / 2);
	}
	
	
	@Override
	public NumConst round()
	{
		return Num.make(Math.round(value()));
	}
	
	
	@Override
	public NumConst ceil()
	{
		return Num.make(Math.ceil(value()));
	}
	
	
	@Override
	public NumConst floor()
	{
		return Num.make(Math.floor(value()));
	}
	
	
	@Override
	public NumConst half()
	{
		return mul(0.5);
	}
	
	
	public NumConst add(NumConst addend)
	{
		return make(value + addend.value);
	}
	
	
	public NumConst sub(NumConst addend)
	{
		return make(value - addend.value);
	}
	
	
	public NumConst mul(NumConst addend)
	{
		return make(value * addend.value);
	}
	
	
	public NumConst div(NumConst addend)
	{
		return make(value / addend.value);
	}
}
