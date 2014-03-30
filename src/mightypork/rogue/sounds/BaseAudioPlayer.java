package mightypork.rogue.sounds;


import mightypork.utils.objects.Mutable;


public abstract class BaseAudioPlayer {

	/** the track */
	private AudioX audio;

	/** base gain for sfx */
	private double baseGain = 1;

	/** base pitch for sfx */
	private double basePitch = 1;

	/** dedicated volume control */
	private Mutable<Double> gainMultiplier = null;


	public BaseAudioPlayer(AudioX track, double baseGain, Mutable<Double> gainMultiplier) {
		this(track, 1, baseGain, gainMultiplier);
	}


	public BaseAudioPlayer(AudioX track, double basePitch, double baseGain, Mutable<Double> gainMultiplier) {
		this.audio = track;

		this.baseGain = baseGain;
		this.basePitch = basePitch;

		this.gainMultiplier = gainMultiplier;
	}


	public void destroy()
	{
		audio.destroy();
		audio = null;
	}


	protected AudioX getAudio()
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
	protected boolean canPlay()
	{
		return (audio != null);
	}


	public void load()
	{
		if (canPlay()) audio.load();
	}
}
