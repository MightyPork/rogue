package mightypork.rogue.sounds;


import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.Set;

import mightypork.rogue.AppAccess;
import mightypork.rogue.bus.Subsystem;
import mightypork.rogue.bus.events.ResourceLoadRequest;
import mightypork.rogue.sounds.players.EffectPlayer;
import mightypork.rogue.sounds.players.LoopPlayer;
import mightypork.utils.control.interf.Updateable;
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
public class SoundSystem extends Subsystem implements Updateable {
	
	private static final Coord INITIAL_LISTENER_POS = new Coord(0, 0, 0);
	private static final int MAX_SOURCES = 256;
	
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
	
	
	public static Coord getListener()
	{
		return listener;
	}
	
	// -- instance --
	
	public final Mutable<Double> masterVolume = new Mutable<Double>(1D);
	public final Mutable<Double> effectsVolume = new JointVolume(masterVolume);
	public final Mutable<Double> loopsVolume = new JointVolume(masterVolume);
	
	private final Set<LoopPlayer> loopPlayers = new HashSet<LoopPlayer>();
	private final Set<DeferredAudio> resources = new HashSet<DeferredAudio>();
	
	
	public SoundSystem(AppAccess app) {
		super(app);
	}
	
	
	@Override
	public final void deinit()
	{
		for (final DeferredAudio r : resources) {
			r.destroy();
		}
		
		SoundStore.get().clear();
		AL.destroy();
	}
	
	
	@Override
	public void update(double delta)
	{
		for (final Updateable lp : loopPlayers) {
			lp.update(delta);
		}
	}
	
	
	/**
	 * Create effect resource
	 * 
	 * @param resource resource path
	 * @param pitch default pitch (1 = unchanged)
	 * @param gain default gain (0-1)
	 * @return player
	 */
	public EffectPlayer createEffect(String resource, double pitch, double gain)
	{
		return new EffectPlayer(getResource(resource), pitch, gain, effectsVolume);
	}
	
	
	/**
	 * Register loop resource (music / effect loop)
	 * 
	 * @param resource resource path
	 * @param pitch default pitch (1 = unchanged)
	 * @param gain default gain (0-1)
	 * @param fadeIn default time for fadeIn
	 * @param fadeOut default time for fadeOut
	 * @return player
	 */
	public LoopPlayer createLoop(String resource, double pitch, double gain, double fadeIn, double fadeOut)
	{
		final LoopPlayer p = new LoopPlayer(getResource(resource), pitch, gain, loopsVolume);
		p.setFadeTimes(fadeIn, fadeOut);
		loopPlayers.add(p);
		return p;
	}
	
	
	/**
	 * Create {@link DeferredAudio} for a resource
	 * 
	 * @param res a resource name
	 * @return the resource
	 * @throws IllegalArgumentException if resource is already registered
	 */
	private DeferredAudio getResource(String res)
	{
		final DeferredAudio a = new DeferredAudio(res);
		bus().send(new ResourceLoadRequest(a));
		
		if (resources.contains(a)) throw new IllegalArgumentException("Sound resource " + res + " is already registered.");
		resources.add(a);
		return a;
	}
	
	
	/**
	 * Fade out all loops (ie. for screen transitions)
	 */
	public void fadeOutAllLoops()
	{
		for (final LoopPlayer p : loopPlayers) {
			p.fadeOut();
		}
	}
	
	
	/**
	 * Pause all loops (leave volume unchanged)
	 */
	public void pauseAllLoops()
	{
		for (final LoopPlayer p : loopPlayers) {
			p.pause();
		}
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
