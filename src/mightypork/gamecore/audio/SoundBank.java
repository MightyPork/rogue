package mightypork.gamecore.audio;


import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.audio.players.EffectPlayer;
import mightypork.gamecore.audio.players.LoopPlayer;
import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppAdapter;
import mightypork.util.logging.Log;


/**
 * Audio resource storage
 * 
 * @author MightyPork
 */
public class SoundBank extends AppAdapter {
	
	private static final DeferredAudio NO_SOUND = new NullAudio();
	private static final LoopPlayer NULL_LOOP = new LoopPlayer(NO_SOUND, 0, 0, null);
	private static final EffectPlayer NULL_EFFECT = new EffectPlayer(NO_SOUND, 0, 0, null);
	
	private final Map<String, EffectPlayer> effects = new HashMap<>();
	private final Map<String, LoopPlayer> loops = new HashMap<>();
	
	
	/**
	 * @param app app access
	 */
	public SoundBank(AppAccess app)
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
			Log.w("Requesting unknown sound loop \"" + key + "\".");
			return NULL_LOOP;
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
			Log.w("Requesting unknown sound effect \"" + key + "\".");
			return NULL_EFFECT;
		}
		return p;
	}
}
