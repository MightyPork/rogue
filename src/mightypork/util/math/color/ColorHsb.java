package mightypork.util.math.color;


import mightypork.util.constraints.num.Num;


public class ColorHsb extends Color {
	
	private final Num h;
	private final Num s;
	private final Num b;
	private final Num a;
	
	
	public ColorHsb(Num h, Num s, Num b, Num a) {
		this.h = h;
		this.s = s;
		this.b = b;
		this.a = a;
	}
	
	
	private double[] asRgb()
	{
		final int hex = java.awt.Color.HSBtoRGB((float) clamp(h), (float) clamp(s), (float) clamp(b));
		
		final int bi = hex & 0xff;
		final int gi = (hex >> 8) & 0xff;
		final int ri = (hex >> 16) & 0xff;
		return new double[] { ri / 255D, gi / 255D, bi / 255D, clamp(a) };
	}
	
	
	@Override
	public double red()
	{
		return asRgb()[0];
	}
	
	
	@Override
	public double green()
	{
		return asRgb()[1];
	}
	
	
	@Override
	public double blue()
	{
		return asRgb()[2];
	}
	
	
	@Override
	public double rawAlpha()
	{
		return asRgb()[3];
	}
	
}
