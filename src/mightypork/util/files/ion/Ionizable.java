package mightypork.util.files.ion;


import java.io.InputStream;
import java.io.OutputStream;


/**
 * Object that can be saved to and loaded from Ion file.<br>
 * All classes implementing Ionizable must be registered to {@link Ion} using
 * Ion.registerIonizable(obj.class).
 * 
 * @author MightyPork
 */
public interface Ionizable {
	
	/**
	 * Load data from the input stream. Mark has already been read, begin
	 * reading right after it.
	 * 
	 * @param in input stream
	 * @throws IonException
	 */
	public void ionRead(InputStream in) throws IonException;
	
	
	/**
	 * Store data to output stream. mark has already been written, begin right
	 * after it.
	 * 
	 * @param out Output stream
	 * @throws IonException
	 */
	public void ionWrite(OutputStream out) throws IonException;
	
	
	/**
	 * Get Ion mark byte.
	 * 
	 * @return Ion mark byte.
	 */
	public byte ionMark();
}
