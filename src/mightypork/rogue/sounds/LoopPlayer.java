package mightypork.rogue.sounds;


import mightypork.utils.objects.Mutable;
import mightypork.utils.time.Pauseable;
import mightypork.utils.time.Updateable;
import mightypork.utils.time.animation.AnimDouble;

import org.lwjgl.openal.AL10;


public class LoopPlayer extends BaseAudioPlayer implements Updateable, Pauseable {
	
	private int sourceID = -1;
	
	/** animator for fade in and fade out */
	private AnimDouble fadeAnim = new AnimDouble(0);
	
	private double lastUpdateGain = 0;
	
	/** flag that track is paused */
	private boolean paused = true;
	
	/** Default fadeIn time */
	private double inTime = 1;
	
	/** Default fadeOut time */
	private double outTime = 1;
	
	
	public LoopPlayer(AudioX track, double pitch, double baseGain, Mutable<Double> gainMultiplier) {
		super(track, (float) pitch, (float) baseGain, gainMultiplier);
		
		paused = true;
	}
	
	
	public void setFadeTimes(double in, double out)
	{
		inTime = in;
		outTime = out;
	}
	
	
	private void initLoop()
	{
		if (!canPlay() && sourceID == -1) {
			sourceID = getAudio().playAsEffect(getPitch(1), getGain(1), true);
			getAudio().pauseLoop();
		}
	}
	
	
	@Override
	public void pause()
	{
		if (!canPlay() || paused) return;
		
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
		if (!canPlay() || paused) return;
		
		initLoop();
		
		sourceID = getAudio().resumeLoop();
		paused = false;
	}
	
	
	@Override
	public void update(double delta)
	{
		if (!canPlay() || paused) return;
		
		initLoop();
		
		fadeAnim.update(delta);
		
		double gain = getGain(fadeAnim.getCurrentValue());
		if (!paused && gain != lastUpdateGain) {
			AL10.alSourcef(sourceID, AL10.AL_GAIN, (float) gain);
			lastUpdateGain = gain;
		}
		
		if (gain == 0 && !paused) pause(); // pause on zero volume
	}
	
	
	public void fadeIn(double secs)
	{
		if (!canPlay()) return;
		
		resume();
		fadeAnim.fadeIn(secs);
	}
	
	
	public void fadeOut(double secs)
	{
		if (!canPlay()) return;
		
		fadeAnim.fadeOut(secs);
	}
	
	
	public void fadeIn()
	{
		fadeIn(inTime);
	}
	
	
	public void fadeOut()
	{
		fadeOut(outTime);
	}
	
}
