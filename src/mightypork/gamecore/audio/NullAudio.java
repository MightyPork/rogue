package mightypork.gamecore.audio;


import mightypork.gamecore.loading.NullResource;
import mightypork.util.logging.LogAlias;


/**
 * Placeholder for cases where no matching audio is found and
 * {@link NullPointerException} has to be avoided.
 * 
 * @author MightyPork
 */
@LogAlias(name = "NullAudio")
public class NullAudio extends DeferredAudio implements NullResource {
	
	/**
	 * new null audio
	 */
	public NullAudio() {
		super(null);
	}
}
