package mightypork.gamecore.backends.lwjgl.audio;


import java.io.IOException;
import java.io.InputStream;

import mightypork.gamecore.audio.DeferredAudio;
import mightypork.utils.files.FileUtil;

import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.SoundStore;


/**
 * SlickUtil-based deferred audio resource.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class SlickAudio extends DeferredAudio {
	
	private double pauseLoopPosition = 0;
	private boolean looping = false;
	private boolean paused = false;
	private double lastPlayPitch = 1;
	private double lastPlayGain = 1;
	
	/** Audio resource */
	private Audio backingAudio = null;
	private int sourceID;
	
	
	public SlickAudio(String resourceName) {
		super(resourceName);
	}
	
	
	@Override
	protected void loadResource(String resource) throws IOException
	{
		final String ext = FileUtil.getExtension(resource);
		
		try (final InputStream stream = FileUtil.getResource(resource)) {
			
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
	
	
	@Override
	public void pauseLoop()
	{
		if (!ensureLoaded()) return;
		
		if (isPlaying() && looping) {
			pauseLoopPosition = backingAudio.getPosition();
			stop();
			paused = true;
		}
	}
	
	
	@Override
	public void resumeLoop()
	{
		if (!ensureLoaded()) return;
		
		if (looping && paused) {
			sourceID = backingAudio.playAsSoundEffect((float) lastPlayPitch, (float) lastPlayGain, true);
			backingAudio.setPosition((float) pauseLoopPosition);
			paused = false;
		}
	}
	
	
	@Override
	public void adjustGain(double gain)
	{
		AL10.alSourcef(sourceID, AL10.AL_GAIN, (float) gain);
	}
	
	
	@Override
	public void stop()
	{
		if (!isLoaded()) return;
		
		backingAudio.stop();
		paused = false;
	}
	
	
	@Override
	public boolean isPlaying()
	{
		if (!isLoaded()) return false;
		
		return backingAudio.isPlaying();
	}
	
	
	@Override
	public boolean isPaused()
	{
		if (!isLoaded()) return false;
		
		return backingAudio.isPaused();
	}
	
	
	@Override
	public void play(double pitch, double gain, boolean loop, double x, double y, double z)
	{
		if (!ensureLoaded()) return;
		
		this.lastPlayPitch = pitch;
		this.lastPlayGain = gain;
		looping = loop;
		
		sourceID = backingAudio.playAsSoundEffect((float) pitch, (float) gain, loop, (float) x, (float) y, (float) z);
	}
	
	
	@Override
	public void destroy()
	{
		if (!isLoaded() || backingAudio == null) return;
		
		backingAudio.release();
		backingAudio = null;
	}
}
