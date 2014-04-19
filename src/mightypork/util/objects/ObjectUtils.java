package mightypork.util.objects;


import java.util.ArrayList;
import java.util.List;

import mightypork.util.logging.Log;


/**
 * Object utils class
 * 
 * @author MightyPork
 */
public class ObjectUtils {
	
	public static Object fallback(Object... options)
	{
		for (final Object o : options) {
			if (o != null) return o;
		}
		return null; // error
	}
	
	
	public static <T> String arrayToString(T[] arr)
	{
		final StringBuilder sb = new StringBuilder();
		
		sb.append('[');
		final boolean first = true;
		for (final T o : arr) {
			if (!first) sb.append(',');
			sb.append(Log.str(o));
		}
		sb.append(']');
		
		return sb.toString();
	}
	
	
	public static <T> List<T> arrayToList(T[] objs)
	{
		final ArrayList<T> list = new ArrayList<>();
		for (final T o : objs) {
			list.add(o);
		}
		return list;
	}
}
