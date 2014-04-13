package mightypork.gamecore.audio;


import mightypork.gamecore.loading.NullResource;
import mightypork.utils.annotations.Alias;


/**
 * Placeholder for cases where no matching audio is found and
 * {@link NullPointerException} has to be avoided.
 * 
 * @author MightyPork
 */
@Alias(name = "NullAudio")
public class NullAudio extends DeferredAudio implements NullResource {
	
	/**
	 * new null audio
	 */
	public NullAudio() {
		super(null);
	}
}
