package mightypork.util.files.ion;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;

import mightypork.util.annotations.DefaultImpl;


/**
 * Ionizable HashMap
 * 
 * @author MightyPork
 * @param <K> key
 * @param <V> value
 */
public abstract class IonMap<K, V> extends LinkedHashMap<K, V> implements Ionizable {
	
	@Override
	public V get(Object key)
	{
		return super.get(key);
	}
	
	
	@Override
	public V put(K key, V value)
	{
		return super.put(key, value);
	}
	
	
	@Override
	public void loadFrom(InputStream in) throws IOException
	{
		try {
			while (true) {
				final short mark = Ion.readMark(in);
				if (mark == Ion.ENTRY) {
					final K key = (K) Ion.readObject(in);
					final V value = (V) Ion.readObject(in);
					put(key, value);
					
				} else if (mark == Ion.END) {
					break;
				} else {
					throw new RuntimeException("Unexpected mark in IonMap: " + mark);
				}
			}
			readCustomData(in);
		} catch (final IOException | ClassCastException e) {
			throw new IOException("Error reading ion map", e);
		}
	}
	
	
	@Override
	public void saveTo(OutputStream out) throws IOException
	{
		try {
			for (final java.util.Map.Entry<K, V> entry : entrySet()) {
				Ion.writeMark(out, Ion.ENTRY);
				Ion.writeObject(out, entry.getKey());
				Ion.writeObject(out, entry.getValue());
			}
			Ion.writeMark(out, Ion.END);
			writeCustomData(out);
		} catch (final IOException e) {
			throw new IOException("Error writing ion map", e);
		}
	}
	
	
	/**
	 * Read custom data of this AbstractIonMap implementation
	 * 
	 * @param in input stream
	 */
	@DefaultImpl
	public void readCustomData(InputStream in)
	{
	}
	
	
	/**
	 * Write custom data of this AbstractIonMap implementation
	 * 
	 * @param out output stream
	 */
	@DefaultImpl
	public void writeCustomData(OutputStream out)
	{
	}
	
	
	@Override
	public short getIonMark()
	{
		return Ion.DATA_BUNDLE;
	}
	
}
