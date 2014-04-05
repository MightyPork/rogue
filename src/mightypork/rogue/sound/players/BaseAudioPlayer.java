package mightypork.rogue.sound.players;


import mightypork.rogue.sound.DeferredAudio;
import mightypork.utils.control.interf.Destroyable;
import mightypork.utils.objects.Mutable;


public abstract class BaseAudioPlayer implements Destroyable {
	
	/** the track */
	private final DeferredAudio audio;
	
	/** base gain for sfx */
	private final double baseGain;
	
	/** base pitch for sfx */
	private final double basePitch;
	
	/** dedicated volume control */
	private final Mutable<Double> gainMultiplier;
	
	
	public BaseAudioPlayer(DeferredAudio track, double baseGain, Mutable<Double> gainMultiplier) {
		this(track, 1, baseGain, gainMultiplier);
	}
	
	
	public BaseAudioPlayer(DeferredAudio track, double basePitch, double baseGain, Mutable<Double> gainMultiplier) {
		this.audio = track;
		
		this.baseGain = baseGain;
		this.basePitch = basePitch;
		
		if (gainMultiplier == null) gainMultiplier = new Mutable<Double>(1D);
		
		this.gainMultiplier = gainMultiplier;
	}
	
	
	@Override
	public void destroy()
	{
		audio.destroy();
	}
	
	
	protected DeferredAudio getAudio()
	{
		return audio;
	}
	
	
	protected double getGain(double multiplier)
	{
		return baseGain * gainMultiplier.get() * multiplier;
	}
	
	
	protected double getPitch(double multiplier)
	{
		return basePitch * multiplier;
	}
	
	
	/**
	 * Get if audio is valid
	 * 
	 * @return is valid
	 */
	protected boolean hasAudio()
	{
		return (audio != null);
	}
	
	
	public void load()
	{
		if (hasAudio()) audio.load();
	}
}
