package mightypork.gamecore.util.ion;


import java.io.IOException;


/**
 * Binary ion object, with no mark = cannot be loaded on it's own
 * 
 * @author MightyPork
 */
public interface IonObjBlob {
	
	/**
	 * Load data from the input stream.
	 * 
	 * @param in input stream
	 * @throws IOException
	 */
	void load(IonInput in) throws IOException;
	
	
	/**
	 * Store data to output stream (in such way that the load method will later
	 * be able to read it).
	 * 
	 * @param out Output stream
	 * @throws IOException
	 */
	void save(IonOutput out) throws IOException;
}
