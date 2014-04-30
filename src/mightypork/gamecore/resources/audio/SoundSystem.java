package mightypork.gamecore.resources.audio;


import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.Set;

import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.eventbus.clients.RootBusNode;
import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.resources.audio.players.EffectPlayer;
import mightypork.gamecore.resources.audio.players.LoopPlayer;
import mightypork.gamecore.resources.events.ResourceLoadRequest;
import mightypork.gamecore.util.math.Calc.Buffers;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.gamecore.util.math.constraints.vect.mutable.VectVar;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.SoundStore;


/**
 * Sound system class (only one instance should be made per application)
 * 
 * @author MightyPork
 */
public class SoundSystem extends RootBusNode implements Updateable {
	
	private static final Vect INITIAL_LISTENER_POS = Vect.ZERO;
	private static final int MAX_SOURCES = 256;
	
	private static VectVar listener = Vect.makeVar();
	private static boolean soundSystemInited = false;
	
	
	/**
	 * Set listener pos
	 * 
	 * @param pos
	 */
	public static void setListener(Vect pos)
	{
		listener.setTo(pos);
		final FloatBuffer buf3 = Buffers.alloc(3);
		final FloatBuffer buf6 = Buffers.alloc(6);
		buf3.clear();
		Buffers.fill(buf3, (float) pos.x(), (float) pos.y(), (float) pos.z());
		AL10.alListener(AL10.AL_POSITION, buf3);
		buf3.clear();
		Buffers.fill(buf3, 0, 0, 0);
		AL10.alListener(AL10.AL_VELOCITY, buf3);
		buf6.clear();
		Buffers.fill(buf6, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f);
		AL10.alListener(AL10.AL_ORIENTATION, buf6);
	}
	
	
	/**
	 * @return listener coordinate
	 */
	public static Vect getListener()
	{
		return listener;
	}
	
	// -- instance --
	
	private final Volume masterVolume = new Volume(1D);
	private final Volume effectsVolume = new JointVolume(masterVolume);
	private final Volume loopsVolume = new JointVolume(masterVolume);
	
	private final Set<LoopPlayer> loopPlayers = new HashSet<>();
	private final Set<DeferredAudio> resources = new HashSet<>();
	
	
	/**
	 * @param app app access
	 */
	public SoundSystem(AppAccess app)
	{
		super(app);
		
		if (!soundSystemInited) {
			SoundStore.get().setMaxSources(MAX_SOURCES);
			SoundStore.get().init();
			setListener(INITIAL_LISTENER_POS);
			
			soundSystemInited = true;
		}
	}
	
	
	@Override
	public void deinit()
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
		getEventBus().send(new ResourceLoadRequest(a));
		
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
	
	
	/**
	 * Get level of master volume
	 * 
	 * @return level
	 */
	public double getMasterVolume()
	{
		return masterVolume.get();
	}
	
	
	/**
	 * Get level of effects volume
	 * 
	 * @return level
	 */
	public double getEffectsVolume()
	{
		return effectsVolume.get();
	}
	
	
	/**
	 * Get level of music volume
	 * 
	 * @return level
	 */
	public double getMusicVolume()
	{
		return loopsVolume.get();
	}
}