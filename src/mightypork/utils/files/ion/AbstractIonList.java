package mightypork.utils.files.ion;


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
public abstract class AbstractIonList<T> extends ArrayList<T> implements Ionizable {
	
	@Override
	public void ionRead(InputStream in) throws IonException
	{
		try {
			while (true) {
				byte b = BinaryUtils.readByte(in);
				
				if (b == IonMarks.ENTRY) {
					T value = (T) Ion.readObject(in);
					add(value);
				} else if (b == IonMarks.END) {
					break;
				} else {
					throw new IonException("Unexpected mark in IonList: " + Integer.toHexString(b));
				}
			}
			ionReadCustomData(in);
		} catch (IOException e) {
			throw new IonException("Error reading ion list", e);
		}
	}
	
	
	@Override
	public void ionWrite(OutputStream out) throws IonException
	{
		try {
			for (T entry : this) {
				if (entry instanceof IonizableOptional && !((IonizableOptional) entry).ionShouldSave()) continue;
				BinaryUtils.writeByte(out, IonMarks.ENTRY);
				Ion.writeObject(out, entry);
			}
			BinaryUtils.writeByte(out, IonMarks.END);
			ionWriteCustomData(out);
		} catch (IOException e) {
			throw new IonException("Error reading ion map", e);
		}
	}
	
	
	/**
	 * Read custom data of this AbstractIonList implementation
	 * 
	 * @param in input stream
	 */
	public void ionReadCustomData(InputStream in)
	{
	}
	
	
	/**
	 * Write custom data of this AbstractIonList implementation
	 * 
	 * @param out output stream
	 */
	public void ionWriteCustomData(OutputStream out)
	{
	}
	
	
	@Override
	public abstract byte ionMark();
	
}
