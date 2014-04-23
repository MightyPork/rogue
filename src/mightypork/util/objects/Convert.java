package mightypork.util.objects;


import mightypork.util.logging.Log;
import mightypork.util.math.Calc;
import mightypork.util.math.Range;
import mightypork.util.math.constraints.vect.Vect;
import mightypork.util.math.constraints.vect.VectConst;


/**
 * Utility for converting Object to data types; Can also convert strings to data
 * types.
 * 
 * @author MightyPork
 */
public class Convert {
	
	/**
	 * Get INTEGER
	 * 
	 * @param o object
	 * @param def default value
	 * @return integer
	 */
	public static int toInteger(Object o, Integer def)
	{
		try {
			if (o == null) return def;
			if (o instanceof Number) return ((Number) o).intValue();
			if (o instanceof String) return (int) Math.round(Double.parseDouble((String) o));
			if (o instanceof Range) return ((Range) o).randInt();
			if (o instanceof Boolean) return ((Boolean) o) ? 1 : 0;
		} catch (final NumberFormatException e) {}
		return def;
	}
	
	
	/**
	 * Get DOUBLE
	 * 
	 * @param o object
	 * @param def default value
	 * @return double
	 */
	public static double toDouble(Object o, Double def)
	{
		try {
			if (o == null) return def;
			if (o instanceof Number) return ((Number) o).doubleValue();
			if (o instanceof String) return Double.parseDouble((String) o);
			if (o instanceof Range) return ((Range) o).randDouble();
			if (o instanceof Boolean) return ((Boolean) o) ? 1 : 0;
		} catch (final NumberFormatException e) {}
		return def;
	}
	
	
	/**
	 * Get FLOAT
	 * 
	 * @param o object
	 * @param def default value
	 * @return float
	 */
	public static double toFloat(Object o, Float def)
	{
		try {
			if (o == null) return def;
			if (o instanceof Number) return ((Number) o).floatValue();
		} catch (final NumberFormatException e) {}
		return def;
	}
	
	
	/**
	 * Get BOOLEAN
	 * 
	 * @param o object
	 * @param def default value
	 * @return boolean
	 */
	public static boolean toBoolean(Object o, Boolean def)
	{
		if (o == null) return def;
		if (o instanceof Boolean) return ((Boolean) o).booleanValue();
		if (o instanceof Number) return ((Number) o).intValue() != 0;
		
		if (o instanceof String) {
			final String s = ((String) o).trim().toLowerCase();
			if (s.equals("0")) return false;
			if (s.equals("1")) return true;
			try {
				final double n = Double.parseDouble(s);
				return n != 0;
			} catch (final NumberFormatException e) {}
			
			if (s.equals("true")) return true;
			if (s.equals("yes")) return true;
			if (s.equals("y")) return true;
			if (s.equals("a")) return true;
			if (s.equals("enabled")) return true;
			
			if (s.equals("false")) return false;
			if (s.equals("no")) return false;
			if (s.equals("n")) return false;
			if (s.equals("disabled")) return true;
		}
		
		return def;
	}
	
	
	/**
	 * Get STRING
	 * 
	 * @param o object
	 * @param def default value
	 * @return String
	 */
	public static String toString(Object o, String def)
	{
		if (o == null) return def;
		if (o instanceof String) return ((String) o);
		
		if (o instanceof Float) return Calc.toString((float) o);
		
		if (o instanceof Double) return Calc.toString((double) o);
		
		if (o instanceof Vect) {
			final Vect c = (Vect) o;
			return String.format("(%f|%f|%f)", c.x(), c.y(), c.z());
		}
		
		if (o instanceof Range) {
			final Range c = (Range) o;
			return String.format("{%f|%f}", c.getMin(), c.getMax());
		}
		
		if (o instanceof Class<?>) {
			return Log.str(o);
		}
		
		return o.toString();
	}
	
	
	/**
	 * Get a vector
	 * 
	 * @param o object
	 * @param def default value
	 * @return vector
	 */
	public static VectConst toVect(Object o, Vect def)
	{
		try {
			if (o == null) return def.freeze();
			if (o instanceof Vect) return ((Vect) o).freeze();
			if (o instanceof String) {
				String s = ((String) o).trim();
				
				// drop whitespace
				s = s.replaceAll("\\s", "");
				
				// drop brackets
				s = s.replaceAll("[\\(\\[\\{\\)\\]\\}]", "");
				
				// norm separators
				s = s.replaceAll("[:;]", "|");
				
				// norm floating point
				s = s.replaceAll("[,]", ".");
				
				final String[] parts = s.split("[|]");
				
				if (parts.length >= 2) {
					
					final double x = Double.parseDouble(parts[0].trim());
					final double y = Double.parseDouble(parts[1].trim());
					
					if (parts.length == 2) {
						return Vect.make(x, y);
					}
					
					final double z = Double.parseDouble(parts[2].trim());
					
					return Vect.make(x, y, z);
				}
			}
		} catch (final NumberFormatException | ArrayIndexOutOfBoundsException e) {
			// ignore
		}
		
		return def.freeze();
	}
	
	
	/**
	 * Get RANGE
	 * 
	 * @param o object
	 * @param def default value
	 * @return AiCoord
	 */
	public static Range toRange(Object o, Range def)
	{
		try {
			if (o == null) return def;
			if (o instanceof Range) return (Range) o;
			if (o instanceof Number) return new Range(((Number) o).doubleValue(), ((Number) o).doubleValue());
			if (o instanceof String) {
				String s = ((String) o).trim();
				
				// drop whitespace
				s = s.replaceAll("\\s", "");
				
				// drop brackets
				s = s.replaceAll("[\\(\\[\\{\\)\\]\\}]", "");
				
				// norm separators
				s = s.replaceAll("[:;]", "|").replace("..", "|");
				
				// norm floating point
				s = s.replaceAll("[,]", ".");
				
				// dash to pipe, if not a minus sign
				s = s.replaceAll("([0-9])\\s?[\\-]", "$1|");
				
				final String[] parts = s.split("[|]");
				
				if (parts.length >= 1) {
					
					final double low = Double.parseDouble(parts[0].trim());
					
					if (parts.length == 2) {
						final double high = Double.parseDouble(parts[1].trim());
						return Range.make(low, high);
					}
					
					return Range.make(low, low);
				}
			}
		} catch (final NumberFormatException e) {
			// ignore
		}
		return def;
	}
	
	
	/**
	 * Get INTEGER
	 * 
	 * @param o object
	 * @return integer
	 */
	public static int toInteger(Object o)
	{
		return toInteger(o, 0);
	}
	
	
	/**
	 * Get DOUBLE
	 * 
	 * @param o object
	 * @return double
	 */
	public static double toDouble(Object o)
	{
		return toDouble(o, 0d);
	}
	
	
	/**
	 * Get FLOAT
	 * 
	 * @param o object
	 * @return float
	 */
	public static double toFloat(Object o)
	{
		return toFloat(o, 0f);
	}
	
	
	/**
	 * Get BOOLEAN
	 * 
	 * @param o object
	 * @return boolean
	 */
	public static boolean toBoolean(Object o)
	{
		return toBoolean(o, false);
	}
	
	
	/**
	 * Get STRING
	 * 
	 * @param o object
	 * @return String
	 */
	public static String toString(Object o)
	{
		return toString(o, "");
	}
	
	
	/**
	 * Get a vector of two or three coordinates
	 * 
	 * @param o object
	 * @return Coord
	 */
	public static VectConst toVect(Object o)
	{
		return toVect(o, Vect.ZERO);
	}
	
	
	/**
	 * Get RANGE
	 * 
	 * @param o object
	 * @return AiCoord
	 */
	public static Range toRange(Object o)
	{
		return toRange(o, new Range());
	}
	
}
