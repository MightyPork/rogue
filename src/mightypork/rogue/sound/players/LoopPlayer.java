package mightypork.rogue.sound.players;


import mightypork.rogue.sound.DeferredAudio;
import mightypork.utils.control.interf.Updateable;
import mightypork.utils.control.timing.Pauseable;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.objects.Mutable;

import org.lwjgl.openal.AL10;


public class LoopPlayer extends BaseAudioPlayer implements Updateable, Pauseable {
	
	private int sourceID = -1;
	
	/** animator for fade in and fade out */
	private final AnimDouble fadeAnim = new AnimDouble(0);
	
	private double lastUpdateGain = 0;
	
	/** flag that track is paused */
	private boolean paused = true;
	
	/** Default fadeIn time */
	private double inTime = 1;
	
	/** Default fadeOut time */
	private double outTime = 1;
	
	
	public LoopPlayer(DeferredAudio track, double pitch, double baseGain, Mutable<Double> gainMultiplier) {
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
	}
	
	
	@Override
	public void update(double delta)
	{
		if (!hasAudio() || paused) return;
		
		initLoop();
		
		fadeAnim.update(delta);
		
		double gain = getGain(fadeAnim.now());
		if (!paused && gain != lastUpdateGain) {
			AL10.alSourcef(sourceID, AL10.AL_GAIN, (float) gain);
			lastUpdateGain = gain;
		}
		
		if (gain == 0 && !paused) pause(); // pause on zero volume
	}
	
	
	public void fadeIn(double secs)
	{
		if (!hasAudio()) return;
		
		resume();
		fadeAnim.fadeIn(secs);
	}
	
	
	public void fadeOut(double secs)
	{
		if (!hasAudio()) return;
		
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
