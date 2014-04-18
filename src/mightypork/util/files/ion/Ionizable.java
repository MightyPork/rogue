package mightypork.util.files.ion;


import java.io.IOException;
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
	 * @throws IOException
	 */
	void loadFrom(InputStream in) throws IOException;
	
	
	/**
	 * Store data to output stream. Mark has already been written, begin right
	 * after it.
	 * 
	 * @param out Output stream
	 * @throws IOException
	 */
	void saveTo(OutputStream out) throws IOException;
	
	
	/**
	 * Get Ion mark byte.
	 * 
	 * @return Ion mark byte.
	 */
	public short getIonMark();
}
