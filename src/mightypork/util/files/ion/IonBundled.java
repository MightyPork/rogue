package mightypork.util.files.ion;


import java.io.IOException;


/**
 * Bundled ion object
 * 
 * @author MightyPork
 */
public interface IonBundled {
	
	void load(IonBundle bundle) throws IOException;
	
	
	void save(IonBundle bundle) throws IOException;
}
