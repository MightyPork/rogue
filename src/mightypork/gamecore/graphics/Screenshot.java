package mightypork.gamecore.graphics;


import java.io.File;
import java.io.IOException;


/**
 * <p>
 * Screenshot object used to save screenshot to a file. The Screenshot object is
 * created by the Graphics module.
 * </p>
 * <p>
 * Screenshot typically takes a byte buffer and converts it to image before
 * saving to file. This image can be cached to speed up repeated saving.
 * </p>
 * <p>
 * Once created (passing byte buffer in constructor), the Screenshot should be
 * safe to process (call the save() method) in separate thread.
 * </p>
 * 
 * @author MightyPork
 */
public interface Screenshot {
	
	/**
	 * Process byte buffer and write image to a file.<br>
	 * Image can be cached for future save.
	 * 
	 * @param file target file
	 * @throws IOException on error writing to file
	 */
	void save(File file) throws IOException;
}
