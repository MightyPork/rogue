package mightypork.utils.objects;


import java.util.ArrayList;
import java.util.List;


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
	
	
	public static String arrayToString(Object[] arr)
	{
		final StringBuilder sb = new StringBuilder();
		
		sb.append('[');
		final boolean first = true;
		for (final Object o : arr) {
			if (!first) sb.append(',');
			sb.append(o.toString());
		}
		sb.append(']');
		
		return sb.toString();
	}
	
	
	public static <T extends Object> List<T> arrayToList(T[] objs)
	{
		final ArrayList<T> list = new ArrayList<T>();
		for (final T o : objs) {
			list.add(o);
		}
		return list;
	}
}
