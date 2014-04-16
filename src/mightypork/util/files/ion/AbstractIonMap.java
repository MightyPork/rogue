package mightypork.util.files.ion;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;


/**
 * Ionizable HashMap
 * 
 * @author MightyPork
 * @param <V>
 */
public abstract class AbstractIonMap<V> extends LinkedHashMap<String, V> implements Ionizable {
	
	@Override
	public V get(Object key)
	{
		return super.get(key);
	}
	
	
	@Override
	public V put(String key, V value)
	{
		return super.put(key, value);
	}
	
	
	@Override
	public void ionRead(InputStream in) throws IonException
	{
		try {
			while (true) {
				final byte b = BinaryUtils.readByte(in);
				if (b == IonMarks.ENTRY) {
					final String key = BinaryUtils.readString(in);
					
					final V value = (V) Ion.readObject(in);
					put(key, value);
					
				} else if (b == IonMarks.END) {
					break;
				} else {
					throw new RuntimeException("Unexpected mark in IonMap: " + Integer.toHexString(b));
				}
			}
			ionReadCustomData(in);
		} catch (final IOException e) {
			throw new IonException("Error reading ion map", e);
		}
	}
	
	
	@Override
	public void ionWrite(OutputStream out) throws IonException
	{
		try {
			for (final java.util.Map.Entry<String, V> entry : entrySet()) {
				BinaryUtils.writeByte(out, IonMarks.ENTRY);
				BinaryUtils.writeString(out, entry.getKey());
				Ion.writeObject(out, entry.getValue());
			}
			BinaryUtils.writeByte(out, IonMarks.END);
			ionWriteCustomData(out);
		} catch (final IOException e) {
			throw new IonException("Error reading ion map", e);
		}
	}
	
	
	/**
	 * Read custom data of this AbstractIonMap implementation
	 * 
	 * @param in input stream
	 */
	public void ionReadCustomData(InputStream in)
	{
	}
	
	
	/**
	 * Write custom data of this AbstractIonMap implementation
	 * 
	 * @param out output stream
	 */
	public void ionWriteCustomData(OutputStream out)
	{
	}
	
	
	@Override
	public byte ionMark()
	{
		return IonMarks.MAP;
	}
	
}
