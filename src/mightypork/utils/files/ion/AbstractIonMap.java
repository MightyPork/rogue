package mightypork.utils.files.ion;


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
				byte b = StreamUtils.readByte(in);
				if (b == IonMarks.ENTRY) {
					String key = StreamUtils.readStringBytes(in);
					V value;
					try {
						value = (V) Ion.readObject(in);
						put(key, value);
					} catch (IonException e) {
						e.printStackTrace();
					}
				} else if (b == IonMarks.END) {
					break;
				} else {
					throw new RuntimeException("Unexpected mark in IonMap: " + Integer.toHexString(b));
				}
			}
			ionReadCustomData(in);
		} catch (IOException e) {
			throw new IonException("Error reading ion map", e);
		}
	}


	@Override
	public void ionWrite(OutputStream out) throws IonException
	{
		try {
			for (java.util.Map.Entry<String, V> entry : entrySet()) {
				StreamUtils.writeByte(out, IonMarks.ENTRY);
				StreamUtils.writeStringBytes(out, entry.getKey());
				Ion.writeObject(out, entry.getValue());
			}
			StreamUtils.writeByte(out, IonMarks.END);
			ionWriteCustomData(out);
		} catch (IOException e) {
			throw new IonException("Error reading ion map", e);
		}
	}


	/**
	 * Read custom data of this AbstractIonMap implementation
	 * 
	 * @param in input stream
	 */
	public void ionReadCustomData(InputStream in)
	{}


	/**
	 * Write custom data of this AbstractIonMap implementation
	 * 
	 * @param out output stream
	 */
	public void ionWriteCustomData(OutputStream out)
	{}


	@Override
	public byte ionMark()
	{
		return IonMarks.MAP;
	}

}
