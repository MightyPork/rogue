package mightypork.gamecore.util;


import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Assorted utils
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public final class Utils {
	
	public static Thread runAsThread(Runnable r)
	{
		final Thread t = new Thread(r);
		t.start();
		return t;
	}
	
	
	public static boolean hasAnnotation(Object tested, Class<? extends Annotation> annotation)
	{
		return tested.getClass().isAnnotationPresent(annotation);
	}
	
	
	public static <T extends Annotation> T getAnnotation(Object tested, Class<T> annotation)
	{
		return tested.getClass().getAnnotation(annotation);
	}
	
	
	/**
	 * Pick first non-null option
	 * 
	 * @param options options
	 * @return the selected option
	 */
	public static Object fallback(Object... options)
	{
		for (final Object o : options) {
			if (o != null) return o;
		}
		
		return null; // all null
	}
	
	
	public static String getTime(String format)
	{
		return (new SimpleDateFormat(format)).format(new Date());
	}
}
