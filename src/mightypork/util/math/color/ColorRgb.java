package mightypork.util.math.color;


import mightypork.util.math.constraints.num.Num;


public class ColorRgb extends Color {
	
	private final Num r;
	private final Num g;
	private final Num b;
	private final Num a;
	
	
	public ColorRgb(Num r, Num g, Num b, Num a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	
	@Override
	public double red()
	{
		return clamp(r);
	}
	
	
	@Override
	public double green()
	{
		return clamp(g);
	}
	
	
	@Override
	public double blue()
	{
		return clamp(b);
	}
	
	
	@Override
	public double rawAlpha()
	{
		return clamp(a);
	}
	
}
