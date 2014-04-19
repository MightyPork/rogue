package mightypork.util.files.ion;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;


/**
 * Data bundle.
 * 
 * @author MightyPork
 */
public class IonBundle extends LinkedHashMap<String, Object> implements Ionizable {
	
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
	
	
	@Override
	public void load(InputStream in) throws IOException
	{
		clear();
		Ion.readMap(in, this);
	}
	
	
	@Override
	public void save(OutputStream out) throws IOException
	{
		Ion.writeMap(out, this);
	}
	
}
