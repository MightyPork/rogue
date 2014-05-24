package mightypork.gamecore.util.objects;


import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Varargs parser<br>
 * Converts an array of repeated "key, value" pairs to a LinkedHashMap.<br>
 * example:
 * 
 * <pre>
 * Object[] array = { &quot;one&quot;, 1, &quot;two&quot;, 4, &quot;three&quot;, 9, &quot;four&quot;, 16 };
 * Map&lt;String, Integer&gt; args = new VarargsParser&lt;String, Integer&gt;().parse(array);
 * </pre>
 * 
 * @author Ondřej Hruška (MightyPork)
 * @param <K> Type for Map keys
 * @param <V> Type for Map values
 */
public class VarargsParser<K, V> {
	
	/**
	 * Parse array of vararg key, value pairs to a LinkedHashMap.
	 * 
	 * @param args varargs
	 * @return LinkedHashMap
	 * @throws ClassCastException in case of incompatible type in the array
	 * @throws IllegalArgumentException in case of invalid array length (odd)
	 */
	@SuppressWarnings("unchecked")
	public Map<K, V> parse(Object... args) throws ClassCastException, IllegalArgumentException
	{
		final LinkedHashMap<K, V> attrs = new LinkedHashMap<>();
		
		if (args.length % 2 != 0) {
			throw new IllegalArgumentException("Odd number of elements in varargs map!");
		}
		
		K key = null;
		for (final Object o : args) {
			if (key == null) {
				if (o == null) throw new RuntimeException("Key cannot be NULL in varargs map.");
				key = (K) o;
			} else {
				attrs.put(key, (V) o);
				key = null;
			}
		}
		
		return attrs;
	}
}
