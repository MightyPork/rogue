package mightypork.util.files.ion;

import mightypork.util.files.ion.templates.IonizableHashMap;


/**
 * <p>
 * Data bundle.
 * </p>
 * <p>
 * Storing data in a bundle guarantees that future versions will be compatible
 * with the older format. Reading using default values ensures that you will get
 * some value even if it was not saved in the file.
 * </p>
 * 
 * @author MightyPork
 */
public class IonBundle extends IonizableHashMap<String, Object> {
	
	/**
	 * Get an object. If not found, fallback is returned.
	 * 
	 * @param key key
	 * @param fallback fallback
	 * @return element
	 */
	public <T> T get(String key, T fallback)
	{
		try {
			final T itm = (T) super.get(key);
			if (itm == null) return fallback;
			return itm;
		} catch (final ClassCastException e) {
			return fallback;
		}
	}
	
	
	/**
	 * Store an element. It's allowed to store any object, but only primitive
	 * types, String, their arrays, and Ionizable objects can be successfully
	 * stored to stream..
	 */
	@Override
	public Object put(String key, Object value)
	{
		return super.put(key, value);
	}
	
	
	@Override
	public short getIonMark()
	{
		return Ion.DATA_BUNDLE;
	}
	
}
