package mightypork.gamecore.audio;


import mightypork.gamecore.loading.NullResource;
import mightypork.utils.logging.LoggedName;


/**
 * Placeholder for cases where no matching audio is found and
 * {@link NullPointerException} has to be avoided.
 * 
 * @author MightyPork
 */
@LoggedName(name = "NullAudio")
public class NullAudio extends DeferredAudio implements NullResource {
	
	/**
	 * new null audio
	 */
	public NullAudio() {
		super(null);
	}
}
