package mightypork.rogue.sounds;


import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mightypork.rogue.AppAccess;
import mightypork.rogue.bus.DelegatingBusClient;
import mightypork.utils.logging.Log;
import mightypork.utils.math.Calc.Buffers;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.objects.Mutable;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.SoundStore;


/**
 * Sound system class (only one instance should be made per application)
 * 
 * @author MightyPork
 */
@SuppressWarnings("unchecked")
public class SoundSystem extends DelegatingBusClient {
	
	private static final Coord INITIAL_LISTENER_POS = new Coord(0, 0, 0);
	private static final int MAX_SOURCES = 256;
	private static final AudioX NO_SOUND = new NullAudio();
	private static final LoopPlayer NULL_LOOP = new LoopPlayer(NO_SOUND, 0, 0, null);
	private static final EffectPlayer NULL_EFFECT = new EffectPlayer(NO_SOUND, 0, 0, null);
	
	private static Coord listener = new Coord();
	
	static {
		// initialize sound system
		SoundStore.get().setMaxSources(MAX_SOURCES);
		SoundStore.get().init();
		
		setListener(INITIAL_LISTENER_POS);
	}
	
	
	/**
	 * Set listener pos
	 * 
	 * @param pos
	 */
	public static void setListener(Coord pos)
	{
		listener.setTo(pos);
		FloatBuffer buf3 = Buffers.alloc(3);
		FloatBuffer buf6 = Buffers.alloc(6);
		buf3.clear();
		Buffers.fill(buf3, (float) pos.x, (float) pos.y, (float) pos.z);
		AL10.alListener(AL10.AL_POSITION, buf3);
		buf3.clear();
		Buffers.fill(buf3, 0, 0, 0);
		AL10.alListener(AL10.AL_VELOCITY, buf3);
		buf6.clear();
		Buffers.fill(buf6, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f);
		AL10.alListener(AL10.AL_ORIENTATION, buf6);
		buf3 = buf6 = null;
	}
	
	// -- instance --
	
	public Mutable<Double> masterVolume = new Mutable<Double>(1D);
	public Mutable<Double> effectsVolume = new JointVolume(masterVolume);
	public Mutable<Double> loopsVolume = new JointVolume(masterVolume);
	
	private Map<String, EffectPlayer> effects = new HashMap<String, EffectPlayer>();
	private Map<String, LoopPlayer> loops = new HashMap<String, LoopPlayer>();
	private Set<AudioX> resources = new HashSet<AudioX>();
	
	
	public SoundSystem(AppAccess app) {
		super(app, true);
	}
	
	
	@Override
	protected void init()
	{
		// empty
	}
	
	
	@Override
	public void deinit()
	{
		for (AudioX r : resources) {
			r.destroy();
		}
		
		SoundStore.get().clear();
		AL.destroy();
	}
	
	
	@Override
	public void update(double delta)
	{
		for (LoopPlayer lp : loops.values()) {
			lp.update(delta);
		}
	}
	
	
	public static Coord getListener()
	{
		return listener;
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
		EffectPlayer p = new EffectPlayer(getResource(resource), pitch, gain, effectsVolume);
		effects.put(key, p);
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
		LoopPlayer p = new LoopPlayer(getResource(resource), pitch, gain, loopsVolume);
		p.setFadeTimes(fadeIn, fadeOut);
		loops.put(key, p);
	}
	
	
	/**
	 * Create {@link AudioX} for a resource
	 * 
	 * @param res a resource name
	 * @return the resource
	 * @throws IllegalArgumentException if resource is already registered
	 */
	private AudioX getResource(String res)
	{
		AudioX a = new AudioX(res);
		if (resources.contains(a)) throw new IllegalArgumentException("Sound resource " + res + " is already registered.");
		resources.add(a);
		return a;
	}
	
	
	/**
	 * Get a loop player for key
	 * 
	 * @param key sound key
	 * @return loop player
	 */
	public LoopPlayer getLoop(String key)
	{
		LoopPlayer p = loops.get(key);
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
		EffectPlayer p = effects.get(key);
		if (p == null) {
			Log.w("Requesting unknown sound effect \"" + key + "\".");
			return NULL_EFFECT;
		}
		return p;
	}
	
	
	/**
	 * Fade out all loops (ie. for screen transitions)
	 */
	public void fadeOutAllLoops()
	{
		for (LoopPlayer p : loops.values()) {
			p.fadeOut();
		}
	}
	
	
	/**
	 * Fade in a loop (with default time)
	 * 
	 * @param key sound key
	 */
	public void fadeInLoop(String key)
	{
		getLoop(key).fadeIn();
	}
	
	
	/**
	 * Fade in a loop
	 * 
	 * @param key sound key
	 * @param seconds fade-in duration
	 */
	public void fadeInLoop(String key, double seconds)
	{
		getLoop(key).fadeIn(seconds);
	}
	
	
	/**
	 * Fade out a loop (with default time)
	 * 
	 * @param key sound key
	 */
	public void fadeOutLoop(String key)
	{
		getLoop(key).fadeOut();
	}
	
	
	/**
	 * Fade out a loop
	 * 
	 * @param key sound key
	 * @param seconds fade-out duration
	 */
	public void fadeOutLoop(String key, double seconds)
	{
		getLoop(key).fadeOut(seconds);
	}
	
	
	/**
	 * Pause a loop
	 * 
	 * @param key sound key
	 */
	public void pauseLoop(String key)
	{
		getLoop(key).pause();
	}
	
	
	/**
	 * Pause all loops (leave volume unchanged)
	 */
	public void pauseAllLoops()
	{
		for (LoopPlayer p : loops.values()) {
			p.pause();
		}
	}
	
	
	/**
	 * Resume a loop
	 * 
	 * @param key sound key
	 */
	public void resumeLoop(String key)
	{
		getLoop(key).resume();
	}
	
	
	/**
	 * Set level of master volume
	 * 
	 * @param d level
	 */
	public void setMasterVolume(double d)
	{
		masterVolume.set(d);
	}
	
	
	/**
	 * Set level of effects volume
	 * 
	 * @param d level
	 */
	public void setEffectsVolume(double d)
	{
		effectsVolume.set(d);
	}
	
	
	/**
	 * Set level of music volume
	 * 
	 * @param d level
	 */
	public void setMusicVolume(double d)
	{
		loopsVolume.set(d);
	}
}
