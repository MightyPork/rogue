package mightypork.util.math;


import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mightypork.util.math.constraints.vect.Vect;

import org.lwjgl.BufferUtils;


/**
 * Math utils
 * 
 * @author MightyPork
 */
public class Calc {
	
	/** Square root of two */
	public static final double SQ2 = 1.41421356237;
	
	
	/**
	 * Get distance from 2D line to 2D point [X,Y]
	 * 
	 * @param lineDirVec line directional vector
	 * @param linePoint point of line
	 * @param point point coordinate
	 * @return distance
	 */
	public static double linePointDist(Vect lineDirVec, Vect linePoint, Vect point)
	{
		// line point L[lx,ly]
		final double lx = linePoint.x();
		final double ly = linePoint.y();
		
		// line equation ax+by+c=0
		final double a = -lineDirVec.y();
		final double b = lineDirVec.x();
		final double c = -a * lx - b * ly;
		
		// checked point P[x,y]
		final double x = point.x();
		final double y = point.y();
		
		// distance
		return Math.abs(a * x + b * y + c) / Math.sqrt(a * a + b * b);
	}
	
	private static class Angles {
		
		public static double delta(double alpha, double beta, double a360)
		{
			while (Math.abs(alpha - beta) > a360 / 2D) {
				alpha = norm(alpha + a360 / 2D, a360);
				beta = norm(beta + a360 / 2D, a360);
			}
			
			return beta - alpha;
		}
		
		
		public static double norm(double angle, double a360)
		{
			while (angle < 0)
				angle += a360;
			while (angle > a360)
				angle -= a360;
			return angle;
		}
	}
	
	/**
	 * Calc subclass with buffer utils.
	 * 
	 * @author MightyPork
	 */
	public static class Buffers {
		
		/**
		 * Create java.nio.FloatBuffer of given floats, and flip it.
		 * 
		 * @param obj floats or float array
		 * @return float buffer
		 */
		public static FloatBuffer mkFillBuff(float... obj)
		{
			return (FloatBuffer) BufferUtils.createFloatBuffer(obj.length).put(obj).flip();
		}
		
		
		/**
		 * Fill java.nio.FloatBuffer with floats or float array
		 * 
		 * @param buff
		 * @param obj
		 */
		public static void fill(FloatBuffer buff, float... obj)
		{
			buff.put(obj);
			buff.flip();
		}
		
		
		/**
		 * Create new java.nio.FloatBuffer of given length
		 * 
		 * @param count elements
		 * @return the new java.nio.FloatBuffer
		 */
		public static FloatBuffer alloc(int count)
		{
			return BufferUtils.createFloatBuffer(count);
		}
		
	}
	
	/**
	 * Angle calculations for degrees.
	 * 
	 * @author MightyPork
	 */
	public static class Deg {
		
		/** 180° in degrees */
		public static final double a180 = 180;
		/** 270° in degrees */
		public static final double a270 = 270;
		/** 360° in degrees */
		public static final double a360 = 360;
		/** 45° in degrees */
		public static final double a45 = 45;
		/** 90° in degrees */
		public static final double a90 = 90;
		
		
		/**
		 * Subtract two angles alpha - beta
		 * 
		 * @param alpha first angle
		 * @param beta second angle
		 * @return (alpha - beta) in degrees
		 */
		public static double delta(double alpha, double beta)
		{
			return Angles.delta(alpha, beta, a360);
		}
		
		
		/**
		 * Difference of two angles (absolute value of delta)
		 * 
		 * @param alpha first angle
		 * @param beta second angle
		 * @return difference in radians
		 */
		public static double diff(double alpha, double beta)
		{
			return Math.abs(Angles.delta(alpha, beta, a360));
		}
		
		
		/**
		 * Cosinus in degrees
		 * 
		 * @param deg angle in degrees
		 * @return cosinus
		 */
		public static double cos(double deg)
		{
			return Math.cos(toRad(deg));
		}
		
		
		/**
		 * Sinus in degrees
		 * 
		 * @param deg angle in degrees
		 * @return sinus
		 */
		public static double sin(double deg)
		{
			return Math.sin(toRad(deg));
		}
		
		
		/**
		 * Tangents in degrees
		 * 
		 * @param deg angle in degrees
		 * @return tangents
		 */
		public static double tan(double deg)
		{
			return Math.tan(toRad(deg));
		}
		
		
		/**
		 * Angle normalized to 0-360 range
		 * 
		 * @param angle angle to normalize
		 * @return normalized angle
		 */
		public static double norm(double angle)
		{
			return Angles.norm(angle, a360);
		}
		
		
		/**
		 * Convert to radians
		 * 
		 * @param deg degrees
		 * @return radians
		 */
		public static double toRad(double deg)
		{
			return Math.toRadians(deg);
		}
		
		
		/**
		 * Round angle to 0,45,90,135...
		 * 
		 * @param deg angle in deg. to round
		 * @param increment rounding increment (45 - round to 0,45,90...)
		 * @return rounded
		 */
		public static int roundToIncrement(double deg, double increment)
		{
			final double half = increment / 2d;
			deg += half;
			deg = norm(deg);
			final int times = (int) Math.floor(deg / increment);
			double a = times * increment;
			if (a == 360) a = 0;
			return (int) Math.round(a);
		}
		
		
		/**
		 * Round angle to 0,45,90,135...
		 * 
		 * @param deg angle in deg. to round
		 * @return rounded
		 */
		public static int round45(double deg)
		{
			return roundToIncrement(deg, 45);
		}
		
		
		/**
		 * Round angle to 0,90,180,270
		 * 
		 * @param deg angle in deg. to round
		 * @return rounded
		 */
		public static int round90(double deg)
		{
			return roundToIncrement(deg, 90);
		}
		
		
		/**
		 * Round angle to 0,15,30,45,60,75,90...
		 * 
		 * @param deg angle in deg to round
		 * @return rounded
		 */
		public static int round15(double deg)
		{
			return roundToIncrement(deg, 15);
		}
	}
	
	/**
	 * Angle calculations for radians.
	 * 
	 * @author MightyPork
	 */
	public static class Rad {
		
		/** 180° in radians */
		public static final double a180 = Math.PI;
		/** 270° in radians */
		public static final double a270 = Math.PI * 1.5D;
		/** 360° in radians */
		public static final double a360 = Math.PI * 2D;
		/** 45° in radians */
		public static final double a45 = Math.PI / 4D;
		/** 90° in radians */
		public static final double a90 = Math.PI / 2D;
		
		
		/**
		 * Subtract two angles alpha - beta
		 * 
		 * @param alpha first angle
		 * @param beta second angle
		 * @return (alpha - beta) in radians
		 */
		public static double delta(double alpha, double beta)
		{
			return Angles.delta(alpha, beta, a360);
		}
		
		
		/**
		 * Difference of two angles (absolute value of delta)
		 * 
		 * @param alpha first angle
		 * @param beta second angle
		 * @return difference in radians
		 */
		public static double diff(double alpha, double beta)
		{
			return Math.abs(Angles.delta(alpha, beta, a360));
		}
		
		
		/**
		 * Cos
		 * 
		 * @param rad angle in rads
		 * @return cos
		 */
		public static double cos(double rad)
		{
			return Math.cos(rad);
		}
		
		
		/**
		 * Sin
		 * 
		 * @param rad angle in rads
		 * @return sin
		 */
		public static double sin(double rad)
		{
			return Math.sin(rad);
		}
		
		
		/**
		 * Tan
		 * 
		 * @param rad angle in rads
		 * @return tan
		 */
		public static double tan(double rad)
		{
			return Math.tan(rad);
		}
		
		
		/**
		 * Angle normalized to 0-2*PI range
		 * 
		 * @param angle angle to normalize
		 * @return normalized angle
		 */
		public static double norm(double angle)
		{
			return Angles.norm(angle, a360);
		}
		
		
		/**
		 * Convert to degrees
		 * 
		 * @param rad radians
		 * @return degrees
		 */
		public static double toDeg(double rad)
		{
			return Math.toDegrees(rad);
		}
	}
	
	private static Random rand = new Random();
	
	
	public static double sphereSurface(double radius)
	{
		return 4D * Math.PI * square(radius);
	}
	
	
	public static double sphereVolume(double radius)
	{
		return (4D / 3D) * Math.PI * cube(radius);
	}
	
	
	public static double sphereRadius(double volume)
	{
		return Math.cbrt((3D * volume) / (4 * Math.PI));
	}
	
	
	public static double circleSurface(double radius)
	{
		return Math.PI * square(radius);
	}
	
	
	public static double circleRadius(double surface)
	{
		return Math.sqrt(surface / Math.PI);
	}
	
	
	/**
	 * Safe equals that works with nulls
	 * 
	 * @param a
	 * @param b
	 * @return are equal
	 */
	public static boolean areEqual(Object a, Object b)
	{
		return a == null ? b == null : a.equals(b);
	}
	
	
	/**
	 * Clamp integer
	 * 
	 * @param number
	 * @param min
	 * @param max
	 * @return result
	 */
	public static int clamp(int number, int min, int max)
	{
		return number < min ? min : number > max ? max : number;
	}
	
	
	/**
	 * Clamp double
	 * 
	 * @param number
	 * @param min
	 * @param max
	 * @return result
	 */
	public static double clamp(double number, double min, double max)
	{
		return number < min ? min : number > max ? max : number;
	}
	
	
	/**
	 * Convert double to string, remove the mess at the end.
	 * 
	 * @param d double
	 * @return string
	 */
	public static String toString(double d)
	{
		String s = Double.toString(d);
		s = s.replace(',', '.');
		s = s.replaceAll("([0-9]+\\.[0-9]+)00+[0-9]+", "$1");
		s = s.replaceAll("0+$", "");
		s = s.replaceAll("\\.$", "");
		return s;
	}
	
	
	/**
	 * Convert float to string, remove the mess at the end.
	 * 
	 * @param f float
	 * @return string
	 */
	public static String toString(float f)
	{
		String s = Float.toString(f);
		s = s.replaceAll("([0-9]+\\.[0-9]+)00+[0-9]+", "$1");
		s = s.replaceAll("0+$", "");
		s = s.replaceAll("\\.$", "");
		return s;
	}
	
	
	public static boolean inRange(double number, double left, double right)
	{
		return number >= left && number <= right;
	}
	
	
	public static boolean inRange(int number, int low, int high)
	{
		return number >= low && number <= high;
	}
	
	
	/**
	 * Get number from A to B at delta time (A -> B)
	 * 
	 * @param from
	 * @param to
	 * @param elapsed progress ratio 0..1
	 * @param easing
	 * @return result
	 */
	public static double interpolate(double from, double to, double elapsed, Easing easing)
	{
		return from + (to - from) * easing.get(elapsed);
	}
	
	
	/**
	 * Get angle [degrees] from A to B at delta time (tween A to B)
	 * 
	 * @param from
	 * @param to
	 * @param elapsed progress ratio 0..1
	 * @param easing
	 * @return result
	 */
	public static double interpolateDeg(double from, double to, double elapsed, Easing easing)
	{
		return Deg.norm(from - Deg.delta(to, from) * easing.get(elapsed));
	}
	
	
	/**
	 * Get angle [radians] from A to B at delta time (tween A to B)
	 * 
	 * @param from
	 * @param to
	 * @param elapsed progress ratio 0..1
	 * @param easing
	 * @return result
	 */
	public static double interpolateRad(double from, double to, double elapsed, Easing easing)
	{
		return Rad.norm(from - Rad.delta(to, from) * easing.get(elapsed));
	}
	
	
	public static double max(double... numbers)
	{
		double highest = numbers[0];
		for (final double num : numbers) {
			if (num > highest) highest = num;
		}
		return highest;
	}
	
	
	public static int max(int... numbers)
	{
		int highest = numbers[0];
		for (final int num : numbers) {
			if (num > highest) highest = num;
		}
		return highest;
	}
	
	
	public static double min(double... numbers)
	{
		double lowest = numbers[0];
		for (final double num : numbers) {
			if (num < lowest) lowest = num;
		}
		return lowest;
	}
	
	
	public static int min(int... numbers)
	{
		int lowest = numbers[0];
		for (final int num : numbers) {
			if (num < lowest) lowest = num;
		}
		return lowest;
	}
	
	
	/**
	 * Split comma separated list of integers.
	 * 
	 * @param list String containing the list.
	 * @return array of integers or null.
	 */
	public static List<Integer> parseIntList(String list)
	{
		if (list == null) { return null; }
		final String[] parts = list.split(",");
		
		final ArrayList<Integer> intList = new ArrayList<>();
		
		for (final String part : parts) {
			try {
				intList.add(Integer.parseInt(part));
			} catch (final NumberFormatException e) {}
		}
		
		return intList;
		
	}
	
	
	/**
	 * Pick random element from a given list.
	 * 
	 * @param list list of choices
	 * @return picked element
	 */
	public static Object pick(List<?> list)
	{
		if (list.size() == 0) return null;
		return list.get(rand.nextInt(list.size()));
	}
	
	
	public static double square(double a)
	{
		return a * a;
	}
	
	
	public static double cube(double a)
	{
		return a * a * a;
	}
	
	
	/**
	 * @param d number
	 * @return fractional part
	 */
	public static double frag(double d)
	{
		return d - Math.floor(d);
	}
	
	
	/**
	 * Make sure value is within array length.
	 * 
	 * @param index tested index
	 * @param length array length
	 * @throws IndexOutOfBoundsException if the index is not in range.
	 */
	public static void assertValidIndex(int index, int length)
	{
		if (!inRange(index, 0, length - 1)) { throw new IndexOutOfBoundsException(); }
	}
}
