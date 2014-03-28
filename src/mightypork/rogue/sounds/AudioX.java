package mightypork.rogue.sounds;


import mightypork.utils.files.FileUtils;
import mightypork.utils.logging.Log;
import mightypork.utils.math.coord.Coord;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.SoundStore;


/**
 * Wrapper class for slick audio
 * 
 * @author MightyPork
 */
public class AudioX implements Audio {

	private enum PlayMode
	{
		EFFECT, MUSIC;
	};

	private Audio audio = null;
	private float pauseLoopPosition = 0;
	private boolean looping = false;
	private boolean paused = false;
	private PlayMode mode = PlayMode.EFFECT;
	private float pitch = 1;
	private float gain = 1;

	private String resourcePath;


	/**
	 * Pause loop (remember position and stop playing) - if was looping
	 */
	public void pauseLoop()
	{
		if (!ensureLoaded()) return;

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
		if (!ensureLoaded()) return -1;

		int source = -1;
		if (looping && paused) {
			if (mode == PlayMode.MUSIC) {
				source = audio.playAsMusic(pitch, gain, true);
			} else {
				source = audio.playAsSoundEffect(pitch, gain, true);
			}
			audio.setPosition(pauseLoopPosition);
			paused = false;
		}
		return source;
	}


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
	 * Check if can play, if not, try to load sound.
	 * 
	 * @return can now play
	 */
	private boolean ensureLoaded()
	{
		load();

		return audio != null;
	}


	public void load()
	{
		if (audio != null) return; // already loaded
		if (resourcePath == null) return; // not loaded, but can't load anyway

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
				resourcePath = null; // don't try next time
				Log.e("Invalid audio file extension: " + resourcePath);
			}

		} catch (Exception e) {
			Log.e("Could not load " + resourcePath, e);
			resourcePath = null; // don't try next time
		}
	}


	@Override
	public void stop()
	{
		if (!ensureLoaded()) return;

		audio.stop();
		paused = false;
	}


	@Override
	public int getBufferID()
	{
		if (!ensureLoaded()) return -1;

		return audio.getBufferID();
	}


	@Override
	public boolean isPlaying()
	{
		if (!ensureLoaded()) return false;

		return audio.isPlaying();
	}


	@Override
	public boolean isPaused()
	{
		if (!ensureLoaded()) return false;

		return audio.isPaused();
	}


	@Override
	public int playAsSoundEffect(float pitch, float gain, boolean loop)
	{
		return playAsSoundEffect(pitch, gain, loop, SoundManager.get().listener);
	}


	@Override
	public int playAsSoundEffect(float pitch, float gain, boolean loop, float x, float y, float z)
	{
		if (!ensureLoaded()) return -1;

		this.pitch = pitch;
		this.gain = gain;
		looping = loop;
		mode = PlayMode.EFFECT;
		return audio.playAsSoundEffect(pitch, gain, loop, x, y, z);
	}


	/**
	 * Play this sound as a sound effect
	 * 
	 * @param pitch The pitch of the play back
	 * @param gain The gain of the play back
	 * @param loop True if we should loop
	 * @param pos The position of the sound
	 * @return The ID of the source playing the sound
	 */
	public int playAsSoundEffect(float pitch, float gain, boolean loop, Coord pos)
	{
		return playAsSoundEffect(pitch, gain, loop, (float) pos.x, (float) pos.y, (float) pos.z);
	}


	@Override
	public int playAsMusic(float pitch, float gain, boolean loop)
	{
		this.pitch = pitch;
		this.gain = gain;
		looping = loop;
		mode = PlayMode.MUSIC;
		return audio.playAsMusic(pitch, gain, loop);
	}


	@Override
	public boolean setPosition(float position)
	{
		return audio.setPosition(position);
	}


	@Override
	public float getPosition()
	{
		return audio.getPosition();
	}


	@Override
	public void release()
	{
		audio.release();
		audio = null;
	}

}
