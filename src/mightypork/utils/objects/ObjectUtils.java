package mightypork.utils.objects;


import java.util.*;
import java.util.Map.Entry;


/**
 * Object utils class
 * 
 * @author MightyPork
 */
public class ObjectUtils {

	public static Object fallback(Object... options)
	{
		for (Object o : options) {
			if (o != null) return o;
		}
		return null; // error
	}


	/**
	 * Sort a map by keys, maintaining key-value pairs.
	 * 
	 * @param map map to be sorted
	 * @param comparator a comparator, or null for natural ordering
	 * @return linked hash map with sorted entries
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <K extends Comparable, V extends Comparable> Map<K, V> sortByKeys(Map<K, V> map, final Comparator<K> comparator)
	{
		List<K> keys = new LinkedList<K>(map.keySet());

		if (comparator == null) {
			Collections.sort(keys);
		} else {
			Collections.sort(keys, comparator);
		}

		// LinkedHashMap will keep the keys in the order they are inserted
		// which is currently sorted on natural ordering
		Map<K, V> sortedMap = new LinkedHashMap<K, V>();
		for (K key : keys) {
			sortedMap.put(key, map.get(key));
		}

		return sortedMap;
	}


	/**
	 * Sort a map by values, maintaining key-value pairs.
	 * 
	 * @param map map to be sorted
	 * @param comparator a comparator, or null for natural ordering
	 * @return linked hash map with sorted entries
	 */
	@SuppressWarnings("rawtypes")
	public static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(Map<K, V> map, final Comparator<V> comparator)
	{
		List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(map.entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {

			@Override
			public int compare(Entry<K, V> o1, Entry<K, V> o2)
			{
				if (comparator == null) return o1.getValue().compareTo(o2.getValue());
				return comparator.compare(o1.getValue(), o2.getValue());
			}
		});

		// LinkedHashMap will keep the keys in the order they are inserted
		// which is currently sorted on natural ordering
		Map<K, V> sortedMap = new LinkedHashMap<K, V>();

		for (Map.Entry<K, V> entry : entries) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}


	public static String arrayToString(Object[] arr)
	{
		StringBuilder sb = new StringBuilder();

		sb.append('[');
		boolean first = true;
		for (Object o : arr) {
			if (!first) sb.append(',');
			sb.append(o.toString());
		}
		sb.append(']');

		return sb.toString();
	}


	public static <T extends Object> List<T> arrayToList(T[] objs)
	{
		ArrayList<T> list = new ArrayList<T>();
		for (T o : objs) {
			list.add(o);
		}
		return list;
	}
}
