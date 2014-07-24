package mightypork.gamecore.resources.audio;


import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.SoundStore;

import mightypork.gamecore.backend.BackendModule;
import mightypork.gamecore.backend.lwjgl.SlickAudio;
import mightypork.gamecore.core.modules.App;
import mightypork.gamecore.resources.ResourceLoadRequest;
import mightypork.gamecore.resources.audio.players.EffectPlayer;
import mightypork.gamecore.resources.audio.players.LoopPlayer;
import mightypork.utils.interfaces.Updateable;
import mightypork.utils.logging.Log;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Abstract audio backend module
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class AudioModule extends BackendModule implements Updateable {
	
	/**
	 * Set listener position
	 * 
	 * @param pos listener position
	 */
	public abstract void setListenerPos(Vect pos);
	
	
	/**
	 * Get current listener position
	 * 
	 * @return listener position
	 */
	public abstract Vect getListenerPos();
	
	// -- instance --
	
	private final Volume masterVolume = new Volume(1D);
	private final Volume effectsVolume = new JointVolume(masterVolume);
	private final Volume loopsVolume = new JointVolume(masterVolume);
	
	private final List<LoopPlayer> loopPlayers = new ArrayList<>();
	private final List<DeferredAudio> resources = new ArrayList<>();
	
	
	@Override
	public void destroy()
	{
		for (final DeferredAudio r : resources) {
			r.destroy();
		}
		
		deinitSoundSystem();
	}
	
	
	protected abstract void deinitSoundSystem();
	
	
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
		return new EffectPlayer(createResource(resource), pitch, gain, effectsVolume);
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
		final LoopPlayer p = new LoopPlayer(createResource(resource), pitch, gain, loopsVolume);
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
	protected DeferredAudio createResource(String res)
	{
		final DeferredAudio a = doCreateResource(res);;
		App.bus().send(new ResourceLoadRequest(a));
		resources.add(a);
		return a;
	}
	
	
	/**
	 * Create a backend-specific deferred audio resource instance.
	 * 
	 * @param res resource path
	 * @return Deferred Audio
	 */
	protected abstract DeferredAudio doCreateResource(String res);
	
	
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
