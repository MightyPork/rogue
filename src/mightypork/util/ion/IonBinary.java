package mightypork.util.ion;


import java.io.IOException;


/**
 * Binary ion object
 * 
 * @author MightyPork
 */
public interface IonBinary {
	
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
	
	
	/**
	 * Get Ion mark byte.
	 * 
	 * @return Ion mark byte.
	 */
	public short getIonMark();
}
