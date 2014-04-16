package mightypork.utils.math.color;


import java.util.Stack;

import mightypork.utils.annotations.FactoryMethod;
import mightypork.utils.math.Calc;
import mightypork.utils.math.constraints.num.Num;


/**
 * Color.<br>
 * All values are 0-1
 * 
 * @author MightyPork
 */
public abstract class Color {
	
	public static final Color NONE = rgba(0, 0, 0, 0);
	public static final Color SHADOW = rgba(0, 0, 0, 0.5);
	
	public static final Color WHITE = rgb(1, 1, 1);
	public static final Color BLACK = rgb(0, 0, 0);
	public static final Color DARK_GRAY = rgb(0.25, 0.25, 0.25);
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
	
	private static final Stack<Num> globalAlphaStack = new Stack<>();
	
	
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
		return Calc.clampd(n.value(), 0, 1);
	}
	
	
	protected static final double clamp(double n)
	{
		return Calc.clampd(n, 0, 1);
	}
	
	
	/**
	 * @return red 0-1
	 */
	public abstract double red();
	
	
	/**
	 * @return green 0-1
	 */
	public abstract double green();
	
	
	/**
	 * @return blue 0-1
	 */
	public abstract double blue();
	
	
	/**
	 * @return alpha 0-1
	 */
	public final double alpha()
	{
		double alpha = rawAlpha();
		
		for (Num n : globalAlphaStack) {
			alpha *= clamp(n.value());
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
		globalAlphaStack.push(alpha);
	}
	
	
	/**
	 * Remove a pushed alpha multiplier from the stack. If there's no remaining
	 * multiplier on the stack, an exception is raised.
	 * 
	 * @return the popped multiplier
	 * @throws IllegalStateException if the stack is empty
	 */
	public static Num popAlpha()
	{
		if (globalAlphaStack.isEmpty()) {
			throw new IllegalStateException("Global alpha stack underflow.");
		}
		
		return globalAlphaStack.pop();
	}
}
