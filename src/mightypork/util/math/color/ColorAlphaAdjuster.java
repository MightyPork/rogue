package mightypork.util.math.color;


import mightypork.util.constraints.num.Num;


public class ColorAlphaAdjuster extends Color {
	
	private final Color source;
	private final Num alphaAdjust;
	
	
	public ColorAlphaAdjuster(Color source, Num alphaMul) {
		this.source = source;
		this.alphaAdjust = alphaMul;
	}
	
	
	@Override
	public double red()
	{
		return source.red();
	}
	
	
	@Override
	public double green()
	{
		return source.green();
	}
	
	
	@Override
	public double blue()
	{
		return source.blue();
	}
	
	
	@Override
	protected double rawAlpha()
	{
		return source.rawAlpha() * alphaAdjust.value();
	}
	
}
