package mightypork.rogue.audio;


import mightypork.utils.objects.Mutable;


public abstract class BaseAudioPlayer {
	
	/** the track */
	private DeferredAudio audio;
	
	/** base gain for sfx */
	private double baseGain = 1;
	
	/** base pitch for sfx */
	private double basePitch = 1;
	
	/** dedicated volume control */
	private Mutable<Double> gainMultiplier = null;
	
	
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
	
	
	public void destroy()
	{
		audio.destroy();
		audio = null;
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
