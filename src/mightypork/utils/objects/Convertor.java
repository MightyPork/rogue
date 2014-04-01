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
public class Convertor {
	
	/**
	 * Get INTEGER
	 * 
	 * @param o
	 *            object
	 * @param def
	 *            default value
	 * @return integer
	 */
	public static int getInteger(Object o, Integer def)
	{
		try {
			if (o == null) return def;
			if (o instanceof String) return (int) Math.round(Double.parseDouble((String) o));
			if (o instanceof Number) return ((Number) o).intValue();
			if (o instanceof Range) return ((Range) o).randInt();
			if (o instanceof Boolean) return ((Boolean) o) ? 1 : 0;
		} catch (NumberFormatException e) {
		}
		Log.w("Cannot convert " + o + " to Integer.");
		return def;
	}
	
	
	/**
	 * Get DOUBLE
	 * 
	 * @param o
	 *            object
	 * @param def
	 *            default value
	 * @return double
	 */
	public static double getDouble(Object o, Double def)
	{
		try {
			if (o == null) return def;
			if (o instanceof String) return Double.parseDouble((String) o);
			if (o instanceof Number) return ((Number) o).doubleValue();
			if (o instanceof Range) return ((Range) o).randDouble();
			if (o instanceof Boolean) return ((Boolean) o) ? 1 : 0;
		} catch (NumberFormatException e) {
		}
		Log.w("Cannot convert " + o + " to Double.");
		return def;
	}
	
	
	/**
	 * Get FLOAT
	 * 
	 * @param o
	 *            object
	 * @param def
	 *            default value
	 * @return float
	 */
	public static double getFloat(Object o, Float def)
	{
		try {
			if (o == null) return def;
			if (o instanceof Number) return ((Number) o).floatValue();
		} catch (NumberFormatException e) {
		}
		Log.w("Cannot convert " + o + " to Float.");
		return def;
	}
	
	
	/**
	 * Get BOOLEAN
	 * 
	 * @param o
	 *            object
	 * @param def
	 *            default value
	 * @return boolean
	 */
	public static boolean getBoolean(Object o, Boolean def)
	{
		if (o == null) return def;
		
		if (o instanceof String) {
			String s = ((String) o).trim().toLowerCase();
			if (s.equals("0")) return false;
			if (s.equals("1")) return true;
			try {
				double n = Double.parseDouble(s);
				return n != 0;
			} catch (NumberFormatException e) {
			}
			
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
		Log.w("Cannot convert " + o + " to Boolean.");
		return def;
	}
	
	
	/**
	 * Get STRING
	 * 
	 * @param o
	 *            object
	 * @param def
	 *            default value
	 * @return String
	 */
	public static String getString(Object o, String def)
	{
		if (o == null) return def;
		if (o instanceof String) return ((String) o);
		Log.w("Cannot convert " + o + " to String.");
		return o.toString();
	}
	
	
	/**
	 * Get AI_COORD<br>
	 * Converts special constants to magic coordinate instances.
	 * 
	 * @param o
	 *            object
	 * @param def
	 *            default value
	 * @return AiCoord
	 */
	public static Coord getCoord(Object o, Coord def)
	{
		try {
			if (o == null) return def;
			if (o instanceof String) {
				String s = ((String) o).trim().toUpperCase();
				
				// colon to semicolon
				s = s.replace(':', ';');
				// comma to semicolon
				s = s.replace(',', ';');
				// remove brackets if any
				s = s.replaceAll("[\\(\\[\\{\\)\\]\\}]", "");
				String[] parts = s.split("[;]");
				return new Coord(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[1].trim()));
			}
			if (o instanceof Coord) return new Coord((Coord) o);
		} catch (NumberFormatException e) {
			// ignore
		}
		Log.w("Cannot convert " + o + " to Coord.");
		return def;
	}
	
	
	/**
	 * Get RANGE
	 * 
	 * @param o
	 *            object
	 * @param def
	 *            default value
	 * @return AiCoord
	 */
	public static Range getRange(Object o, Range def)
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
				String[] parts = s.split("[:]");
				if (parts.length == 2) return new Range(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[1].trim()));
				return new Range(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[0].trim()));
				
			}
			if (o instanceof Range) return (Range) o;
		} catch (NumberFormatException e) {
		}
		Log.w("Cannot convert " + o + " to Range.");
		return def;
	}
	
	
	/**
	 * Get INTEGER
	 * 
	 * @param o
	 *            object
	 * @return integer
	 */
	public static int getInteger(Object o)
	{
		return getInteger(o, 0);
	}
	
	
	/**
	 * Get DOUBLE
	 * 
	 * @param o
	 *            object
	 * @return double
	 */
	public static double getDouble(Object o)
	{
		return getDouble(o, 0d);
	}
	
	
	/**
	 * Get FLOAT
	 * 
	 * @param o
	 *            object
	 * @return float
	 */
	public static double getFloat(Object o)
	{
		return getFloat(o, 0f);
	}
	
	
	/**
	 * Get BOOLEAN
	 * 
	 * @param o
	 *            object
	 * @return boolean
	 */
	public static boolean getBoolean(Object o)
	{
		return getBoolean(o, false);
	}
	
	
	/**
	 * Get STRING
	 * 
	 * @param o
	 *            object
	 * @return String
	 */
	public static String getString(Object o)
	{
		return getString(o, "");
	}
	
	
	/**
	 * Get AI_COORD (if special string constant is present instead, build coord
	 * of it)
	 * 
	 * @param o
	 *            object
	 * @return AiCoord
	 */
	public static Coord getCoord(Object o)
	{
		return getCoord(o, Coord.zero());
	}
	
	
	/**
	 * Get RANGE
	 * 
	 * @param o
	 *            object
	 * @return AiCoord
	 */
	public static Range getRange(Object o)
	{
		return getRange(o, new Range());
	}
	
}
