package mightypork.gamecore.resources.audio;


import java.io.IOException;
import java.io.InputStream;

import mightypork.gamecore.logging.LogAlias;
import mightypork.gamecore.resources.BaseLazyResource;
import mightypork.gamecore.util.files.FileUtils;
import mightypork.gamecore.util.math.constraints.vect.Vect;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.SoundStore;


/**
 * Wrapper class for slick audio
 * 
 * @author Ondřej Hruška
 */
@LogAlias(name = "Audio")
public class LazyAudio extends BaseLazyResource {
	
	private enum PlayMode
	{
		EFFECT, MUSIC;
	}
	
	/** Audio resource */
	private Audio backingAudio = null;
	
	// last play options
	private PlayMode mode = PlayMode.EFFECT;
	private double pauseLoopPosition = 0;
	private boolean looping = false;
	private boolean paused = false;
	private double lastPlayPitch = 1;
	private double lastPlayGain = 1;
	
	
	/**
	 * Create deferred primitive audio player
	 * 
	 * @param resourceName resource to load when needed
	 */
	public LazyAudio(String resourceName)
	{
		super(resourceName);
	}
	
	
	/**
	 * Pause loop (remember position and stop playing) - if was looping
	 */
	public void pauseLoop()
	{
		if (!ensureLoaded()) return;
		
		if (isPlaying() && looping) {
			pauseLoopPosition = backingAudio.getPosition();
			stop();
			paused = true;
		}
	}
	
	
	/**
	 * Resume loop (if was paused)
	 * 
	 * @return source ID
	 */
	public int resumeLoop()
	{
		if (!ensureLoaded()) return -1;
		
		int source = -1;
		if (looping && paused) {
			if (mode == PlayMode.MUSIC) {
				source = backingAudio.playAsMusic((float) lastPlayPitch, (float) lastPlayGain, true);
			} else {
				source = backingAudio.playAsSoundEffect((float) lastPlayPitch, (float) lastPlayGain, true);
			}
			backingAudio.setPosition((float) pauseLoopPosition);
			paused = false;
		}
		return source;
	}
	
	
	@Override
	protected void loadResource(String resource) throws IOException
	{
		final String ext = FileUtils.getExtension(resource);
		
		try(final InputStream stream = FileUtils.getResource(resource)) {
			
			if (ext.equalsIgnoreCase("ogg")) {
				backingAudio = SoundStore.get().getOgg(resource, stream);
				
			} else if (ext.equalsIgnoreCase("wav")) {
				backingAudio = SoundStore.get().getWAV(resource, stream);
				
			} else if (ext.equalsIgnoreCase("aif")) {
				backingAudio = SoundStore.get().getAIF(resource, stream);
				
			} else if (ext.equalsIgnoreCase("mod")) {
				backingAudio = SoundStore.get().getMOD(resource, stream);
				
			} else {
				throw new RuntimeException("Invalid audio file extension.");
			}
		}
	}
	
	
	/**
	 * Stop playing
	 */
	public void stop()
	{
		if (!isLoaded()) return;
		
		backingAudio.stop();
		paused = false;
	}
	
	
	/**
	 * @return true if the audio is playing
	 */
	public boolean isPlaying()
	{
		if (!isLoaded()) return false;
		
		return backingAudio.isPlaying();
	}
	
	
	/**
	 * @return trie if the audio is paused
	 */
	public boolean isPaused()
	{
		if (!isLoaded()) return false;
		
		return backingAudio.isPaused();
	}
	
	
	/**
	 * Play as sound effect at listener position
	 * 
	 * @param pitch pitch (1 = default)
	 * @param gain gain (0-1)
	 * @param loop looping
	 * @return source id
	 */
	public int playAsEffect(double pitch, double gain, boolean loop)
	{
		return playAsEffect(pitch, gain, loop, SoundSystem.getListener());
	}
	
	
	/**
	 * Play as sound effect at given X-Y position
	 * 
	 * @param pitch pitch (1 = default)
	 * @param gain gain (0-1)
	 * @param loop looping
	 * @param x
	 * @param y
	 * @return source id
	 */
	public int playAsEffect(double pitch, double gain, boolean loop, double x, double y)
	{
		return playAsEffect(pitch, gain, loop, x, y, SoundSystem.getListener().z());
	}
	
	
	/**
	 * Play as sound effect at given position
	 * 
	 * @param pitch pitch (1 = default)
	 * @param gain gain (0-1)
	 * @param loop looping
	 * @param x
	 * @param y
	 * @param z
	 * @return source id
	 */
	public int playAsEffect(double pitch, double gain, boolean loop, double x, double y, double z)
	{
		if (!ensureLoaded()) return -1;
		
		this.lastPlayPitch = pitch;
		this.lastPlayGain = gain;
		looping = loop;
		mode = PlayMode.EFFECT;
		return backingAudio.playAsSoundEffect((float) pitch, (float) gain, loop, (float) x, (float) y, (float) z);
	}
	
	
	/**
	 * Play as sound effect at given position
	 * 
	 * @param pitch pitch (1 = default)
	 * @param gain gain (0-1)
	 * @param loop looping
	 * @param pos coord
	 * @return source id
	 */
	public int playAsEffect(double pitch, double gain, boolean loop, Vect pos)
	{
		if (!ensureLoaded()) return -1;
		
		return playAsEffect(pitch, gain, loop, pos.x(), pos.y(), pos.z());
	}
	
	
	/**
	 * Play as music using source 0.<br>
	 * Discouraged, since this does not allow cross-fading.
	 * 
	 * @param pitch play pitch
	 * @param gain play gain
	 * @param loop looping
	 * @return source
	 */
	public int playAsMusic(double pitch, double gain, boolean loop)
	{
		if (!ensureLoaded()) return -1;
		
		this.lastPlayPitch = (float) pitch;
		this.lastPlayGain = (float) gain;
		looping = loop;
		mode = PlayMode.MUSIC;
		return backingAudio.playAsMusic((float) pitch, (float) gain, loop);
	}
	
	
	@Override
	public void destroy()
	{
		if (!isLoaded() || backingAudio == null) return;
		
		backingAudio.release();
		backingAudio = null;
	}
	
}
