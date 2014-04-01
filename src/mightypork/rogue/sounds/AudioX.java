package mightypork.rogue.sounds;

import mightypork.utils.files.FileUtils;
import mightypork.utils.logging.Log;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.patterns.Destroyable;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.SoundStore;


/**
 * Wrapper class for slick audio
 * 
 * @author MightyPork
 */
public class AudioX implements Destroyable {
	
	private enum PlayMode
	{
		EFFECT, MUSIC;
	};
	
	private Audio audio = null;
	private double pauseLoopPosition = 0;
	private boolean looping = false;
	private boolean paused = false;
	private PlayMode mode = PlayMode.EFFECT;
	private double lastPlayPitch = 1;
	private double lastPlayGain = 1;
	
	private final String resourcePath;
	private boolean loadFailed = false;
	
	
	/**
	 * Create deferred primitive audio player
	 * 
	 * @param resourceName resource to load when needed
	 */
	public AudioX(String resourceName) {
		this.audio = null;
		this.resourcePath = resourceName;
	}
	
	
	/**
	 * Pause loop (remember position and stop playing) - if was looping
	 */
	public void pauseLoop()
	{
		if (!load()) return;
		
		if (isPlaying() && looping) {
			pauseLoopPosition = audio.getPosition();
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
		if (!load()) return -1;
		
		int source = -1;
		if (looping && paused) {
			if (mode == PlayMode.MUSIC) {
				source = audio.playAsMusic((float) lastPlayPitch, (float) lastPlayGain, true);
			} else {
				source = audio.playAsSoundEffect((float) lastPlayPitch, (float) lastPlayGain, true);
			}
			audio.setPosition((float) pauseLoopPosition);
			paused = false;
		}
		return source;
	}
	
	
	/**
	 * Check if resource is loaded
	 * 
	 * @return resource is loaded
	 */
	private boolean isLoaded()
	{
		return audio != null;
	}
	
	
	/**
	 * Try to load if not loaded already
	 * 
	 * @return is loaded
	 */
	public boolean load()
	{
		if (isLoaded()) return true; // already loaded
		if (loadFailed || resourcePath == null) return false; // not loaded, but
																// can't load
																// anyway
			
		loadFailed = false;
		try {
			String ext = FileUtils.getExtension(resourcePath);
			
			// java 6 can't use String switch :(
			if (ext.equalsIgnoreCase("ogg")) {
				audio = SoundStore.get().getOgg(resourcePath);
			} else if (ext.equalsIgnoreCase("wav")) {
				audio = SoundStore.get().getWAV(resourcePath);
			} else if (ext.equalsIgnoreCase("aif")) {
				audio = SoundStore.get().getAIF(resourcePath);
			} else if (ext.equalsIgnoreCase("mod")) {
				audio = SoundStore.get().getMOD(resourcePath);
			} else {
				Log.e("Invalid audio file extension: " + resourcePath);
				loadFailed = true; // don't try next time
			}
			
		} catch (Exception e) {
			Log.e("Could not load " + resourcePath, e);
			loadFailed = true; // don't try next time
		}
		
		return isLoaded();
	}
	
	
	public void stop()
	{
		if (!isLoaded()) return;
		
		audio.stop();
		paused = false;
	}
	
	
	public boolean isPlaying()
	{
		if (!isLoaded()) return false;
		
		return audio.isPlaying();
	}
	
	
	public boolean isPaused()
	{
		if (!isLoaded()) return false;
		
		return audio.isPaused();
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
		return playAsEffect(pitch, gain, loop, x, y, SoundSystem.getListener().z);
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
		if (!load()) return -1;
		
		this.lastPlayPitch = pitch;
		this.lastPlayGain = gain;
		looping = loop;
		mode = PlayMode.EFFECT;
		return audio.playAsSoundEffect((float) pitch, (float) gain, loop, (float) x, (float) y, (float) z);
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
	public int playAsEffect(double pitch, double gain, boolean loop, Coord pos)
	{
		if (!load()) return -1;
		
		return playAsEffect(pitch, gain, loop, pos.x, pos.y, pos.z);
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
		if (!load()) return -1;
		
		this.lastPlayPitch = (float) pitch;
		this.lastPlayGain = (float) gain;
		looping = loop;
		mode = PlayMode.MUSIC;
		return audio.playAsMusic((float) pitch, (float) gain, loop);
	}
	
	
	@Override
	public void destroy()
	{
		if (!isLoaded()) return;
		
		audio.release();
		audio = null;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resourcePath == null) ? 0 : resourcePath.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof AudioX)) return false;
		AudioX other = (AudioX) obj;
		if (resourcePath == null) {
			if (other.resourcePath != null) return false;
		} else if (!resourcePath.equals(other.resourcePath)) {
			return false;
		}
		return true;
	}
	
}
