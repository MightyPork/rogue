package mightypork.util.math.color;


import java.util.EmptyStackException;
import java.util.Stack;

import mightypork.util.annotations.FactoryMethod;
import mightypork.util.math.Calc;
import mightypork.util.math.constraints.num.Num;


/**
 * Color.<br>
 * All values are 0-1
 * 
 * @author MightyPork
 */
public abstract class Color {
	
	public static final Color NONE = rgba(0, 0, 0, 0);
	public static final Color SHADOW = rgba(0, 0, 0, 0.5);
	
	public static final Color WHITE = fromHex(0xFFFFFF);
	public static final Color BLACK = fromHex(0x000000);
	public static final Color DARK_GRAY = fromHex(0x808080);
	public static final Color GRAY = rgb(0.5, 0.5, 0.5);
	public static final Color LIGHT_GRAY = rgb(0.75, 0.75, 0.75);
	
	public static final Color RED = rgb(1, 0, 0);
	public static final Color GREEN = rgb(0, 1, 0);
	public static final Color BLUE = rgb(0, 0, 1);
	
	public static final Color YELLOW = rgb(1, 1, 0);
	public static final Color MAGENTA = rgb(1, 0, 1);
	public static final Color CYAN = rgb(0, 1, 1);
	
	public static final Color ORANGE = rgb(1, 0.78, 0);
	public static final Color PINK = rgb(1, 0.68, 0.68);
	
	private static final Stack<Num> alphaStack = new Stack<>();
	private static volatile boolean alphaStackEnabled = true;
	
	
	@FactoryMethod
	public static final Color fromHex(int rgb_hex)
	{
		final int bi = rgb_hex & 0xff;
		final int gi = (rgb_hex >> 8) & 0xff;
		final int ri = (rgb_hex >> 16) & 0xff;
		return rgb(ri / 255D, gi / 255D, bi / 255D);
	}
	
	
	@FactoryMethod
	public static final Color rgb(double r, double g, double b)
	{
		return rgba(Num.make(r), Num.make(g), Num.make(b), Num.ONE);
	}
	
	
	@FactoryMethod
	public static final Color rgba(double r, double g, double b, double a)
	{
		return rgba(Num.make(r), Num.make(g), Num.make(b), Num.make(a));
	}
	
	
	@FactoryMethod
	public static final Color rgba(Num r, Num g, Num b)
	{
		return rgba(r, g, b, Num.ONE);
	}
	
	
	@FactoryMethod
	public static final Color rgba(Num r, Num g, Num b, Num a)
	{
		return new ColorRgb(r, g, b, a);
	}
	
	
	@FactoryMethod
	public static final Color hsb(double h, double s, double b)
	{
		return hsba(Num.make(h), Num.make(s), Num.make(b), Num.ONE);
	}
	
	
	@FactoryMethod
	public static final Color hsba(double h, double s, double b, double a)
	{
		return hsba(Num.make(h), Num.make(s), Num.make(b), Num.make(a));
	}
	
	
	@FactoryMethod
	public static final Color hsb(Num h, Num s, Num b)
	{
		return hsba(h, s, b, Num.ONE);
	}
	
	
	@FactoryMethod
	public static final Color hsba(Num h, Num s, Num b, Num a)
	{
		return new ColorHsb(h, s, b, a);
	}
	
	
	@FactoryMethod
	public static final Color light(double a)
	{
		return light(Num.make(a));
	}
	
	
	@FactoryMethod
	public static final Color light(Num a)
	{
		return rgba(Num.ONE, Num.ONE, Num.ONE, a);
	}
	
	
	@FactoryMethod
	public static final Color dark(double a)
	{
		return dark(Num.make(a));
	}
	
	
	@FactoryMethod
	public static final Color dark(Num a)
	{
		return rgba(Num.ZERO, Num.ZERO, Num.ZERO, a);
	}
	
	
	protected static final double clamp(Num n)
	{
		return Calc.clamp(n.value(), 0, 1);
	}
	
	
	protected static final double clamp(double n)
	{
		return Calc.clamp(n, 0, 1);
	}
	
	
	/**
	 * @return red 0-1
	 */
	public abstract double r();
	
	
	/**
	 * @return green 0-1
	 */
	public abstract double g();
	
	
	/**
	 * @return blue 0-1
	 */
	public abstract double b();
	
	
	/**
	 * @return alpha 0-1
	 */
	public final double a()
	{
		double alpha = rawAlpha();
		
		if (alphaStackEnabled) {
			
			for (final Num n : alphaStack) {
				alpha *= clamp(n.value());
			}
		}
		
		return clamp(alpha);
	}
	
	
	/**
	 * @return alpha 0-1, before multiplying with the global alpha value.
	 */
	protected abstract double rawAlpha();
	
	
	/**
	 * <p>
	 * Push alpha multiplier on the stack (can be animated or whatever you
	 * like). Once done with rendering, the popAlpha() method should be called,
	 * otherwise you may experience unexpected glitches (such as all going
	 * transparent).
	 * </p>
	 * <p>
	 * multiplier value should not exceed the range 0..1, otherwise it will be
	 * clamped to it.
	 * </p>
	 * 
	 * @param alpha alpha multiplier
	 */
	public static void pushAlpha(Num alpha)
	{
		if (!alphaStackEnabled) {
			return;
		}
		
		alphaStack.push(alpha);
	}
	
	
	/**
	 * Remove a pushed alpha multiplier from the stack. If there's no remaining
	 * multiplier on the stack, an exception is raised.
	 * 
	 * @throws EmptyStackException if the stack is empty
	 */
	public static void popAlpha()
	{
		if (!alphaStackEnabled) {
			return;
		}
		
		if (alphaStack.isEmpty()) {
			throw new EmptyStackException();
		}
		
		alphaStack.pop();
	}
	
	
	/**
	 * Enable alpha stack. When disabled, pushAlpha() and popAlpha() have no
	 * effect.
	 * 
	 * @param yes
	 */
	public static void enableAlphaStack(boolean yes)
	{
		alphaStackEnabled = yes;
	}
	
	
	/**
	 * @return true if alpha stack is enabled.
	 */
	public static boolean isAlphaStackEnabled()
	{
		return alphaStackEnabled;
	}
	
	
	public Color withAlpha(double multiplier)
	{
		return new ColorAlphaAdjuster(this, Num.make(multiplier));
	}
	
	
	public Color withAlpha(Num multiplier)
	{
		return new ColorAlphaAdjuster(this, Num.make(multiplier));
	}
}
