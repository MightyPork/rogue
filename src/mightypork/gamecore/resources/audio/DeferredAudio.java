package mightypork.gamecore.resources.audio;


import mightypork.gamecore.resources.BaseDeferredResource;
import mightypork.utils.annotations.Alias;


/**
 * Base deferred audio
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@Alias(name = "Audio")
public abstract class DeferredAudio extends BaseDeferredResource implements IAudio {
	
	/**
	 * Create deferred primitive audio player
	 * 
	 * @param resourceName resource to load when needed
	 */
	public DeferredAudio(String resourceName) {
		super(resourceName);
	}
}
