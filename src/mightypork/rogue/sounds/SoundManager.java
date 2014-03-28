package mightypork.rogue.sounds;


import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import mightypork.utils.math.Calc.Buffers;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.objects.Mutable;
import mightypork.utils.time.Updateable;

import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.SoundStore;


/**
 * Preloaded sounds.
 * 
 * @author MightyPork
 */
public class SoundManager implements Updateable {

	private static SoundManager inst = new SoundManager();
	public Mutable<Float> masterVolume = new Mutable<Float>(1F);
	@SuppressWarnings("unchecked")
	public Mutable<Float> effectsVolume = new JointVolume(masterVolume);
	@SuppressWarnings("unchecked")
	public Mutable<Float> loopsVolume = new JointVolume(masterVolume);
	public Coord listener = new Coord(Coord.ZERO);
	private Map<String, EffectPlayer> effects = new HashMap<String, EffectPlayer>();
	private Map<String, LoopPlayer> loops = new HashMap<String, LoopPlayer>();


	/**
	 * Singleton constructor
	 */
	private SoundManager() {
		SoundStore.get().setMaxSources(256);
		SoundStore.get().init();
	}


	public static SoundManager get()
	{
		return inst;
	}


	public void addEffect(String key, String resource, float pitch, float gain)
	{
		EffectPlayer p = new EffectPlayer(new AudioX(resource), pitch, gain, effectsVolume);
		effects.put(key, p);
	}


	public void addLoop(String key, String resource, float pitch, float gain, double fadeIn, double fadeOut)
	{
		LoopPlayer p = new LoopPlayer(new AudioX(resource), pitch, gain, loopsVolume);
		p.setFadeTimes(fadeIn, fadeOut);
		loops.put(key, p);
	}


	public LoopPlayer getLoop(String key)
	{
		LoopPlayer p = loops.get(key);
		if (p == null) {
			throw new IllegalArgumentException("Requesting unknown sound loop \"" + key + "\".");
		}
		return p;
	}


	public EffectPlayer getEffect(String key)
	{
		EffectPlayer p = effects.get(key);
		if (p == null) {
			throw new IllegalArgumentException("Requesting unknown sound effect \"" + key + "\".");
		}
		return p;
	}


	public void fadeOutAllLoops()
	{
		for (LoopPlayer p : loops.values()) {
			p.fadeOut();
		}
	}


	public void fadeInLoop(String key)
	{
		getLoop(key).fadeIn();
	}


	public void fadeInLoop(String key, double seconds)
	{
		getLoop(key).fadeIn(seconds);
	}


	public void pauseLoop(String key)
	{
		getLoop(key).pause();
	}


	public void pauseAllLoops()
	{
		for (LoopPlayer p : loops.values()) {
			p.pause();
		}
	}


	public void resumeLoop(String key)
	{
		getLoop(key).resume();
	}


	/**
	 * Set listener pos
	 * 
	 * @param pos
	 */
	public void setListener(Coord pos)
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


	@Override
	public void update(double delta)
	{
		for (LoopPlayer lp : loops.values()) {
			lp.update(delta);
		}
	}


	public void setMasterVolume(float f)
	{
		masterVolume.set(f);
	}


	public void setEffectsVolume(float f)
	{
		effectsVolume.set(f);
	}


	public void setMusicVolume(float f)
	{
		loopsVolume.set(f);
	}


	public void destroy()
	{
		SoundStore.get().clear();
	}
}
