package mightypork.gamecore.util.ion;


import java.io.IOException;


/**
 * Bundled ion object
 * 
 * @author MightyPork
 */
public interface IonObjBundled {
	
	void load(IonBundle bundle) throws IOException;
	
	
	void save(IonBundle bundle) throws IOException;
}
