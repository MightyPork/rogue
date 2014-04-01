package mightypork.utils.math.color;


import java.awt.Color;

import mightypork.utils.math.Calc;


/**
 * RGB color
 * 
 * @author MightyPork
 */
public class RGB {
	
	/** White */
	public static final RGB WHITE = new RGB(1, 1, 1);
	/** Black */
	public static final RGB BLACK = new RGB(0, 0, 0);
	/** Red */
	public static final RGB RED = new RGB(1, 0, 0);
	/** Lime green */
	public static final RGB GREEN = new RGB(0, 1, 0);
	/** Blue */
	public static final RGB BLUE = new RGB(0, 0, 1);
	/** Yellow */
	public static final RGB YELLOW = new RGB(1, 1, 0);
	/** Purple */
	public static final RGB PURPLE = new RGB(1, 0, 1);
	/** Cyan */
	public static final RGB CYAN = new RGB(0, 1, 1);
	/** orange */
	public static final RGB ORANGE = new RGB(1, 0.6, 0);
	/** no color (alpha=0) */
	public static final RGB TRANSPARENT = new RGB(0, 0, 0, 0);
	
	/** R */
	public double r;
	/** G */
	public double g;
	/** B */
	public double b;
	/** ALPHA */
	public double a = 1;
	
	
	/**
	 * Create black color 0,0,0
	 */
	public RGB() {
	}
	
	
	/**
	 * Get copy with custom alpha
	 * 
	 * @param alpha alpha to set
	 * @return copy w/ alpha
	 */
	public RGB setAlpha(double alpha)
	{
		return copy().setAlpha_ip(alpha);
	}
	
	
	/**
	 * set alpha IP
	 * 
	 * @param alpha alpha to set
	 * @return this
	 */
	public RGB setAlpha_ip(double alpha)
	{
		a = alpha;
		norm();
		return this;
	}
	
	
	/**
	 * Get copy.
	 * 
	 * @return copy
	 */
	public RGB copy()
	{
		return new RGB(r, g, b, a);
	}
	
	
	/**
	 * Get copy with alpha multiplied by custom value
	 * 
	 * @param alpha alpha to set
	 * @return copy w/ alpha
	 */
	public RGB mulAlpha(double alpha)
	{
		return copy().mulAlpha_ip(alpha);
	}
	
	
	/**
	 * Multiply alpha by given number
	 * 
	 * @param alpha alpha multiplier
	 * @return this
	 */
	public RGB mulAlpha_ip(double alpha)
	{
		a *= alpha;
		norm();
		return this;
	}
	
	
	/**
	 * Color from RGB 0-1
	 * 
	 * @param r red
	 * @param g green
	 * @param b blue
	 */
	public RGB(Number r, Number g, Number b) {
		this.r = r.doubleValue();
		this.g = g.doubleValue();
		this.b = b.doubleValue();
		norm();
	}
	
	
	/**
	 * Color from RGB 0-1
	 * 
	 * @param r red
	 * @param g green
	 * @param b blue
	 * @param a alpha
	 */
	public RGB(Number r, Number g, Number b, Number a) {
		this.r = r.doubleValue();
		this.g = g.doubleValue();
		this.b = b.doubleValue();
		this.a = a.doubleValue();
		norm();
	}
	
	
	/**
	 * Color from hex 0xRRGGBB
	 * 
	 * @param hex hex integer
	 */
	public RGB(int hex) {
		setTo(RGB.fromHex(hex));
		norm();
	}
	
	
	/**
	 * Color from hex 0xRRGGBB
	 * 
	 * @param hex hex integer
	 * @param alpha alpha color
	 */
	public RGB(int hex, double alpha) {
		setTo(RGB.fromHex(hex));
		a = alpha;
		norm();
	}
	
	
	/**
	 * Color from other RGB and alpha channel
	 * 
	 * @param color other RGB color
	 * @param alpha new alpha channel
	 */
	public RGB(RGB color, double alpha) {
		setTo(color);
		setAlpha_ip(alpha);
	}
	
	
	/**
	 * @return red channel 0-1
	 */
	public double r()
	{
		return r;
	}
	
	
	/**
	 * @return green channel 0-1
	 */
	public double g()
	{
		return g;
	}
	
	
	/**
	 * @return blue channel 0-1
	 */
	public double b()
	{
		return b;
	}
	
	
	/**
	 * @return alpha 0-1
	 */
	public double a()
	{
		return a;
	}
	
	
	/**
	 * Set color to other color
	 * 
	 * @param copied copied color
	 * @return this
	 */
	public RGB setTo(RGB copied)
	{
		r = copied.r;
		g = copied.g;
		b = copied.b;
		a = copied.a;
		
		norm();
		return this;
	}
	
	
	/**
	 * Set to represent hex color
	 * 
	 * @param hex hex integer RRGGBB
	 * @return this
	 */
	public RGB setTo(int hex)
	{
		setTo(RGB.fromHex(hex));
		norm();
		return this;
	}
	
	
	/**
	 * Set to R,G,B 0-1
	 * 
	 * @param r red
	 * @param g green
	 * @param b blue
	 * @param a alpha
	 * @return this
	 */
	public RGB setTo(Number r, Number g, Number b, Number a)
	{
		this.r = r.doubleValue();
		this.g = g.doubleValue();
		this.b = b.doubleValue();
		this.a = a.doubleValue();
		norm();
		return this;
	}
	
	
	/**
	 * Set to R,G,B 0-1
	 * 
	 * @param r red
	 * @param g green
	 * @param b blue
	 * @return this
	 */
	public RGB setTo(Number r, Number g, Number b)
	{
		this.r = r.doubleValue();
		this.g = g.doubleValue();
		this.b = b.doubleValue();
		this.a = 1;
		norm();
		return this;
	}
	
	
	/**
	 * Fix numbers out of range 0-1
	 * 
	 * @return this
	 */
	public RGB norm()
	{
		r = Calc.clampd(r, 0, 1);
		g = Calc.clampd(g, 0, 1);
		b = Calc.clampd(b, 0, 1);
		a = Calc.clampd(a, 0, 1);
		return this;
	}
	
	
	/**
	 * Get hex value 0xRRGGBB
	 * 
	 * @return hex value RRGGBB
	 */
	public int getHex()
	{
		int ri = (int) Math.round(r * 255);
		int gi = (int) Math.round(g * 255);
		int bi = (int) Math.round(b * 255);
		return (ri << 16) | (gi << 8) | bi;
	}
	
	
	/**
	 * Convert to HSV
	 * 
	 * @return HSV representation
	 */
	public HSV toHSV()
	{
		float[] hsv = { 0, 0, 0 };
		Color.RGBtoHSB((int) (r * 255), (int) (g * 255), (int) (b * 255), hsv);
		return new HSV(hsv[0], hsv[1], hsv[2]);
	}
	
	
	/**
	 * Create color from hex 0xRRGGBB
	 * 
	 * @param hex hex RRGGBB
	 * @return the new color
	 */
	public static RGB fromHex(int hex)
	{
		int bi = hex & 0xff;
		int gi = (hex >> 8) & 0xff;
		int ri = (hex >> 16) & 0xff;
		return new RGB(ri / 255D, gi / 255D, bi / 255D);
	}
	
	
	/**
	 * Make from HSV
	 * 
	 * @param color HSV color
	 * @return RGB
	 */
	public static RGB fromHSV(HSV color)
	{
		return color.toRGB();
	}
	
	
	@Override
	public String toString()
	{
		return "RGB[" + r + ";" + g + ";" + b + ";" + a + "]";
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		if (!(obj instanceof RGB)) return false;
		return ((RGB) obj).r == r && ((RGB) obj).g == g && ((RGB) obj).b == b && ((RGB) obj).a == a;
	}
	
	
	@Override
	public int hashCode()
	{
		return Double.valueOf(r).hashCode() ^ Double.valueOf(g).hashCode() ^ Double.valueOf(b).hashCode() ^ Double.valueOf(a).hashCode();
	}
	
}
