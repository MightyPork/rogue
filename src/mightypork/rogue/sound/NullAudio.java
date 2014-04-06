package mightypork.rogue.sound;


import mightypork.rogue.loading.NullResource;


/**
 * Placeholder for cases where no matching audio is found and
 * {@link NullPointerException} has to be avoided.
 * 
 * @author MightyPork
 */
public class NullAudio extends DeferredAudio implements NullResource {
	
	public NullAudio() {
		super(null);
	}
}
