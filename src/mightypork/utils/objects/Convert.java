package mightypork.utils.objects;


import mightypork.utils.logging.Log;
import mightypork.utils.math.Range;
import mightypork.utils.math.coord.Coord;


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
			if (o instanceof String) return (int) Math.round(Double.parseDouble((String) o));
			if (o instanceof Number) return ((Number) o).intValue();
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
			if (o instanceof String) return Double.parseDouble((String) o);
			if (o instanceof Number) return ((Number) o).doubleValue();
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
			if (s.equals("enabled")) return true;
			
			if (s.equals("false")) return false;
			if (s.equals("no")) return false;
			if (s.equals("n")) return false;
			if (s.equals("disabled")) return true;
		}
		
		if (o instanceof Boolean) return ((Boolean) o).booleanValue();
		if (o instanceof Number) return ((Number) o).intValue() != 0;
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
		
		if (o instanceof Boolean) {
			return (Boolean) o ? "True" : "False";
		}
		
		if (o instanceof Coord) {
			final Coord c = (Coord) o;
			return String.format("[%f:%f:%f]", c.x, c.y, c.z);
		}
		
		if (o instanceof Range) {
			final Range c = (Range) o;
			return String.format("%f:%f", c.getMin(), c.getMax());
		}
		
		if (o instanceof Class<?>) {
			return Log.str(o);
		}
		
		return o.toString();
	}
	
	
	/**
	 * Get AI_COORD<br>
	 * Converts special constants to magic coordinate instances.
	 * 
	 * @param o object
	 * @param def default value
	 * @return AiCoord
	 */
	public static Coord toCoord(Object o, Coord def)
	{
		try {
			if (o == null) return def;
			if (o instanceof String) {
				String s = ((String) o).trim().toUpperCase();
				
				// colon to semicolon
				s = s.replace(':', ';');
				// remove brackets if any
				s = s.replaceAll("[\\(\\[\\{\\)\\]\\}]", "");
				final String[] parts = s.split("[;]");
				return new Coord(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[1].trim()));
			}
			if (o instanceof Coord) return new Coord((Coord) o);
		} catch (final NumberFormatException e) {
			// ignore
		}
		return def;
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
			if (o instanceof Number) return new Range(((Number) o).doubleValue(), ((Number) o).doubleValue());
			if (o instanceof String) {
				String s = ((String) o).trim();
				
				// colon to semicolon
				s = s.replace(',', ':');
				// comma to dot.
				s = s.replace(';', ':');
				// dash
				s = s.replaceAll("([0-9])\\s?[\\-]", "$1:");
				// remove brackets if any
				s = s.replaceAll("[\\(\\[\\{\\)\\]\\}]", "");
				final String[] parts = s.split("[:]");
				if (parts.length == 2) return new Range(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[1].trim()));
				return new Range(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[0].trim()));
				
			}
			if (o instanceof Range) return (Range) o;
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
	 * Get Coord
	 * 
	 * @param o object
	 * @return Coord
	 */
	public static Coord toCoord(Object o)
	{
		return toCoord(o, Coord.zero());
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