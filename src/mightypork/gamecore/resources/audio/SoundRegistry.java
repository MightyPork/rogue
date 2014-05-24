package mightypork.gamecore.resources.audio;


import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.core.modules.AppAccess;
import mightypork.gamecore.core.modules.AppAccessAdapter;
import mightypork.gamecore.resources.audio.players.EffectPlayer;
import mightypork.gamecore.resources.audio.players.LoopPlayer;


/**
 * Audio resource storage
 * 
 * @author Ondřej Hruška
 */
public class SoundRegistry extends AppAccessAdapter {
	
	private final Map<String, EffectPlayer> effects = new HashMap<>();
	private final Map<String, LoopPlayer> loops = new HashMap<>();
	
	
	/**
	 * @param app app access
	 */
	public SoundRegistry(AppAccess app)
	{
		super(app);
		if (getSoundSystem() == null) throw new NullPointerException("SoundSystem cannot be null.");
	}
	
	
	/**
	 * Register effect resource
	 * 
	 * @param key sound key
	 * @param resource resource path
	 * @param pitch default pitch (1 = unchanged)
	 * @param gain default gain (0-1)
	 */
	public void addEffect(String key, String resource, double pitch, double gain)
	{
		effects.put(key, getSoundSystem().createEffect(resource, pitch, gain));
	}
	
	
	/**
	 * Register loop resource (music / effect loop)
	 * 
	 * @param key sound key
	 * @param resource resource path
	 * @param pitch default pitch (1 = unchanged)
	 * @param gain default gain (0-1)
	 * @param fadeIn default time for fadeIn
	 * @param fadeOut default time for fadeOut
	 */
	public void addLoop(String key, String resource, double pitch, double gain, double fadeIn, double fadeOut)
	{
		loops.put(key, getSoundSystem().createLoop(resource, pitch, gain, fadeIn, fadeOut));
	}
	
	
	/**
	 * Get a loop player for key
	 * 
	 * @param key sound key
	 * @return loop player
	 */
	public LoopPlayer getLoop(String key)
	{
		final LoopPlayer p = loops.get(key);
		if (p == null) {
			throw new RuntimeException("Unknown sound loop \"" + key + "\".");
		}
		return p;
	}
	
	
	/**
	 * Get a effect player for key
	 * 
	 * @param key sound key
	 * @return effect player
	 */
	public EffectPlayer getEffect(String key)
	{
		final EffectPlayer p = effects.get(key);
		if (p == null) {
			throw new RuntimeException("Unknown sound effect \"" + key + "\".");
		}
		return p;
	}
}
