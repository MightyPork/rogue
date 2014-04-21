package mightypork.util.files.ion;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * 
 * Saveable to a stream.
 * 
 * 
 * @author MightyPork
 */
public interface Streamable {
	
	/**
	 * Load data from the input stream. Must be compatible with the
	 * <code>save</code> method.
	 * 
	 * @param in input stream
	 * @throws IOException
	 */
	void load(InputStream in) throws IOException;
	
	
	/**
	 * Store data to output stream.
	 * 
	 * @param out Output stream
	 * @throws IOException
	 */
	void save(OutputStream out) throws IOException;
	
}
