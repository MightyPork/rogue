package mightypork.utils.math.color;

import java.awt.Color;

import mightypork.utils.math.Calc;


/**
 * HSV color
 * 
 * @author MightyPork
 */
public class HSV {
	
	/** H */
	public double h;
	/** S */
	public double s;
	/** V */
	public double v;
	
	
	/**
	 * Create black color 0,0,0
	 */
	public HSV() {
	}
	
	
	/**
	 * Color from HSV 0-1
	 * 
	 * @param h
	 * @param s
	 * @param v
	 */
	public HSV(Number h, Number s, Number v) {
		this.h = h.doubleValue();
		this.s = s.doubleValue();
		this.v = v.doubleValue();
		norm();
	}
	
	
	/**
	 * @return hue 0-1
	 */
	public double h()
	{
		return h;
	}
	
	
	/**
	 * @return saturation 0-1
	 */
	public double s()
	{
		return s;
	}
	
	
	/**
	 * @return value/brightness 0-1
	 */
	public double v()
	{
		return v;
	}
	
	
	/**
	 * Set color to other color
	 * 
	 * @param copied
	 *            copied color
	 * @return this
	 */
	public HSV setTo(HSV copied)
	{
		h = copied.h;
		s = copied.s;
		v = copied.v;
		
		norm();
		return this;
	}
	
	
	/**
	 * Set to H,S,V 0-1
	 * 
	 * @param h
	 *            hue
	 * @param s
	 *            saturation
	 * @param v
	 *            value
	 * @return this
	 */
	public HSV setTo(Number h, Number s, Number v)
	{
		this.h = h.doubleValue();
		this.s = s.doubleValue();
		this.v = v.doubleValue();
		norm();
		return this;
	}
	
	
	/**
	 * Fix numbers out of range 0-1
	 */
	public void norm()
	{
		h = Calc.clampd(h, 0, 1);
		s = Calc.clampd(s, 0, 1);
		v = Calc.clampd(v, 0, 1);
	}
	
	
	/**
	 * Convert to RGB
	 * 
	 * @return RGB representation
	 */
	public RGB toRGB()
	{
		norm();
		
		int rgb = Color.HSBtoRGB((float) h, (float) s, (float) v);
		
		return RGB.fromHex(rgb);
	}
	
	
	/**
	 * Make from RGB
	 * 
	 * @param color
	 *            RGB
	 * @return HSV
	 */
	public static HSV fromRGB(RGB color)
	{
		return color.toHSV();
	}
	
	
	@Override
	public String toString()
	{
		return "HSV[" + h + ";" + s + ";" + v + "]";
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		if (!(obj instanceof HSV)) return false;
		return ((HSV) obj).h == h && ((HSV) obj).s == s && ((HSV) obj).v == v;
	}
	
	
	@Override
	public int hashCode()
	{
		return Double.valueOf(h).hashCode() ^ Double.valueOf(s).hashCode() ^ Double.valueOf(v).hashCode();
	}
	
	
	/**
	 * Get a copy
	 * 
	 * @return copy
	 */
	public HSV copy()
	{
		return new HSV().setTo(this);
	}
}
