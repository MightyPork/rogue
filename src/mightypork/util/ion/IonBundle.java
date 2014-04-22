package mightypork.util.ion;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Ion data bundle - simplified Map
 * 
 * @author MightyPork
 */
public class IonBundle implements IonBinary {
	
	private final Map<String, Object> backingMap = new HashMap<>();
	
	
	public void putBundled(String key, IonBundled saved) throws IOException
	{
		final IonBundle ib = new IonBundle();
		saved.save(ib);
		put(key, ib);
	}
	
	
	public <T extends IonBundled> T getBundled(String key, Class<T> objClass) throws IOException
	{
		return Ion.unwrap(get(key, (IonBundle) null), objClass);
	}
	
	
	public void loadBundled(String key, IonBundled loaded) throws IOException
	{
		IonBundle ib = get(key, null);
		
		if (ib == null) ib = new IonBundle();
		
		loaded.load(ib);
	}
	
	
	public <K, V> Map<K, V> getMap(String key)
	{
		final Map<K, V> m = new HashMap<>();
		loadMap(key, m);
		return m;
	}
	
	
	public <K, V> void loadMap(String key, Map<K, V> map)
	{
		final IonMapWrapper imw = get(key, null);
		if (imw == null) return;
		map.clear();
		imw.fill(map);
	}
	
	
	public <E> Collection<E> getSequence(String key)
	{
		final Collection<E> s = new ArrayList<>();
		loadSequence(key, s);
		return s;
	}
	
	
	public <E> void loadSequence(String key, Collection<E> sequence)
	{
		final IonSequenceWrapper isw = get(key, null);
		if (isw == null) return;
		sequence.clear();
		isw.fill(sequence);
	}
	
	
	/**
	 * <p>
	 * Get an object.
	 * </p>
	 * <p>
	 * If not found or of type incompatible with fallback, fallback is returned.
	 * </p>
	 * 
	 * @param key
	 * @param fallback value
	 * @return value
	 */
	public <T> T get(String key, T fallback)
	{
		try {
			final T itm = (T) backingMap.get(key);
			if (itm == null) return fallback;
			return itm;
		} catch (final ClassCastException e) {
			return fallback;
		}
	}
	
	
	public void put(String key, IonBinary value)
	{
		if (key == null || value == null) return;
		backingMap.put(key, value);
	}
	
	
	public void put(String key, boolean value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, char value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, short value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, int value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, long value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, double value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, float value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, String value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, boolean[] value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, char[] value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, short[] value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, int[] value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, long[] value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, double[] value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, float[] value)
	{
		backingMap.put(key, value);
	}
	
	
	public void put(String key, String[] value)
	{
		backingMap.put(key, value);
	}
	
	
	@SuppressWarnings("rawtypes")
	public void putSequence(String key, Collection c)
	{
		backingMap.put(key, new IonSequenceWrapper(c));
	}
	
	
	@SuppressWarnings("rawtypes")
	public void putMap(String key, Map m)
	{
		backingMap.put(key, new IonMapWrapper(m));
	}
	
	
	@Override
	public short getIonMark()
	{
		return Ion.DATA_BUNDLE;
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		in.readMap(backingMap);
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		out.writeMap(backingMap);
	}
	
	
	public int size()
	{
		return backingMap.size();
	}
	
	
	public boolean isEmpty()
	{
		return backingMap.isEmpty();
	}
	
	
	public void clear()
	{
		backingMap.clear();
	}
	
	
	public Object remove(Object key)
	{
		return backingMap.remove(key);
	}
	
	
	@Override
	public String toString()
	{
		return backingMap.toString();
	}
	
	
	@Override
	public boolean equals(Object o)
	{
		return backingMap.equals(o);
	}
	
	
	@Override
	public int hashCode()
	{
		return 47 ^ backingMap.hashCode();
	}
}