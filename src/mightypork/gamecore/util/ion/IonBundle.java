package mightypork.gamecore.util.ion;


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
public class IonBundle implements IonObjBinary {
	
	private final Map<String, Object> backingMap = new HashMap<>();
	
	
	public void putBundled(String key, IonObjBundled saved) throws IOException
	{
		final IonBundle ib = new IonBundle();
		saved.save(ib);
		put(key, ib);
	}
	
	
	public <T extends IonObjBundled> T getBundled(String key, Class<T> objClass) throws IOException
	{
		return Ion.unwrap(get(key, (IonBundle) null), objClass);
	}
	
	
	public void loadBundled(String key, IonObjBundled loaded) throws IOException
	{
		loaded.load(get(key, new IonBundle()));
	}
	
	
	public void loadBundle(String key, IonBundle bundle)
	{
		if (!containsKey(key)) return;
		
		final IonBundle ib = get(key, new IonBundle());
		
		bundle.clear();
		bundle.putAll(ib);
	}
	
	
	public boolean containsKey(Object key)
	{
		return backingMap.containsKey(key);
	}
	
	
	public boolean containsValue(Object value)
	{
		return backingMap.containsValue(value);
	}
	
	
	public <K, V> Map<K, V> getMap(String key)
	{
		final Map<K, V> m = new HashMap<>();
		loadMap(key, m);
		return m;
	}
	
	
	public <K, V> void loadMap(String key, Map<K, V> filled)
	{
		final IonMapWrapper imw = get(key, null);
		if (imw == null) return;
		filled.clear();
		imw.fill(filled);
	}
	
	
	public <E> Collection<E> getSequence(String key)
	{
		final Collection<E> s = new ArrayList<>();
		loadSequence(key, s);
		return s;
	}
	
	
	public <E> void loadSequence(String key, Collection<E> filled)
	{
		final IonSequenceWrapper isw = get(key, null);
		if (isw == null) return;
		filled.clear();
		isw.fill(filled);
	}
	
	
	/**
	 * Get value, or fallback (if none found of with bad type).
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
	
	
	/**
	 * Get value, or null (if none found of with bad type).
	 * 
	 * @param key
	 * @return value
	 */
	public <T> T get(String key)
	{
		return get(key, (T) null);
	}
	
	
	public void put(String key, IonObjBinary value)
	{
		if (key == null || value == null) return;
		backingMap.put(key, value);
	}
	
	
	public void put(String key, boolean value)
	{
		backingMap.put(key, value);
	}

	public void put(String key, byte value)
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
	
	
	public void putAll(IonBundle otherBundle)
	{
		backingMap.putAll(otherBundle.backingMap);
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
