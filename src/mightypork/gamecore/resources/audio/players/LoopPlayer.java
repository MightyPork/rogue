package mightypork.gamecore.resources.audio.players;


import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.resources.audio.LazyAudio;
import mightypork.gamecore.resources.audio.Volume;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;
import mightypork.gamecore.util.math.timing.Pauseable;

import org.lwjgl.openal.AL10;


/**
 * Audio loop player (with fading, good for music)
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class LoopPlayer extends BaseAudioPlayer implements Updateable, Pauseable {
	
	private int sourceID = -1;
	
	/** animator for fade in and fade out */
	private final NumAnimated fadeAnim = new NumAnimated(0);
	
	private double lastUpdateGain = 0;
	
	/** flag that track is paused */
	private boolean paused = true;
	
	/** Default fadeIn time */
	private double inTime = 1;
	
	/** Default fadeOut time */
	private double outTime = 1;
	
	
	/**
	 * @param track audio resource
	 * @param basePitch base pitch (pitch multiplier)
	 * @param baseGain base gain (volume multiplier)
	 * @param volume volume control
	 */
	public LoopPlayer(LazyAudio track, double basePitch, double baseGain, Volume volume)
	{
		super(track, (float) basePitch, (float) baseGain, volume);
		
		paused = true;
	}
	
	
	/**
	 * Set fading duration (seconds)
	 * 
	 * @param in duration of fade-in
	 * @param out duration of fade-out
	 */
	public void setFadeTimes(double in, double out)
	{
		inTime = in;
		outTime = out;
	}
	
	
	private void initLoop()
	{
		if (hasAudio() && sourceID == -1) {
			sourceID = getAudio().playAsEffect(getPitch(1), getGain(1), true);
			getAudio().pauseLoop();
		}
	}
	
	
	@Override
	public void pause()
	{
		if (!hasAudio() || paused) return;
		
		initLoop();
		
		getAudio().pauseLoop();
		paused = true;
	}
	
	
	@Override
	public boolean isPaused()
	{
		return paused;
	}
	
	
	@Override
	public void resume()
	{
		if (!hasAudio() || !paused) return;
		
		initLoop();
		
		sourceID = getAudio().resumeLoop();
		paused = false;
		
		adjustGain(getGain(fadeAnim.value()));
	}
	
	
	@Override
	public void update(double delta)
	{
		if (!hasAudio() || paused) return;
		
		initLoop();
		
		fadeAnim.update(delta);
		
		final double gain = getGain(fadeAnim.value());
		if (!paused && gain != lastUpdateGain) {
			adjustGain(gain);
			lastUpdateGain = gain;
		}
		
		if (gain == 0 && !paused) pause(); // pause on zero volume
	}
	
	
	private void adjustGain(double gain)
	{
		AL10.alSourcef(sourceID, AL10.AL_GAIN, (float) gain);
	}
	
	
	/**
	 * Resume if paused, and fade in (pick up from current volume).
	 * 
	 * @param secs
	 */
	public void fadeIn(double secs)
	{
		if (!hasAudio()) return;
		
		if (isPaused()) fadeAnim.setTo(0);
		resume();
		fadeAnim.fadeIn(secs);
	}
	
	
	/**
	 * Fade out and pause when reached zero volume
	 * 
	 * @param secs fade duration
	 */
	public void fadeOut(double secs)
	{
		if (!hasAudio()) return;
		if (isPaused()) return;
		fadeAnim.fadeOut(secs);
	}
	
	
	/**
	 * Fade in with default duration
	 */
	public void fadeIn()
	{
		fadeIn(inTime);
	}
	
	
	/**
	 * Fade out with default duration
	 */
	public void fadeOut()
	{
		fadeOut(outTime);
	}
	
}
