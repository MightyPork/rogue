package mightypork.util.files.ion;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


/**
 * Ionizable Arraylist
 * 
 * @author MightyPork
 * @param <T>
 */
public abstract class IonList<T> extends ArrayList<T> implements Ionizable {
	
	@Override
	public void loadFrom(InputStream in) throws IOException
	{
		try {
			while (true) {
				final short mark = Ion.readMark(in);
				
				if (mark == Ion.ENTRY) {
					final T value = (T) Ion.readObject(in);
					add(value);
				} else if (mark == Ion.END) {
					break;
				} else {
					throw new IOException("Unexpected mark in IonList: " + mark);
				}
			}
			readCustomData(in);
		} catch (final IOException e) {
			throw new IOException("Error reading ion list", e);
		}
	}
	
	
	@Override
	public void saveTo(OutputStream out) throws IOException
	{
		try {
			for (final T entry : this) {
				Ion.writeMark(out, Ion.ENTRY);
				Ion.writeObject(out, entry);
			}
			Ion.writeMark(out, Ion.END);
			writeCustomData(out);
		} catch (final IOException e) {
			throw new IOException("Error reading ion map", e);
		}
	}
	
	
	/**
	 * Read custom data of this AbstractIonList implementation
	 * 
	 * @param in input stream
	 */
	public void readCustomData(InputStream in)
	{
	}
	
	
	/**
	 * Write custom data of this AbstractIonList implementation
	 * 
	 * @param out output stream
	 */
	public void writeCustomData(OutputStream out)
	{
	}
	
	
	@Override
	public abstract short getIonMark();
	
}
