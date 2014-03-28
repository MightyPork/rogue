package mightypork.rogue.sounds;


import mightypork.utils.objects.Mutable;


public abstract class AudioPlayer {

	/** base gain for sfx */
	private double baseGain = 1;

	/** the track */
	private AudioX track;

	/** base pitch for sfx */
	private double basePitch = 1;

	/** dedicated volume control */
	private Mutable<Float> gainMultiplier = null;


	public AudioPlayer(AudioX track, double baseGain, Mutable<Float> gainMultiplier) {
		this(track, 1, baseGain, gainMultiplier);
	}


	public AudioPlayer(AudioX track, double basePitch, double baseGain, Mutable<Float> gainMultiplier) {
		this.track = track;

		this.baseGain = baseGain;
		this.basePitch = basePitch;

		this.gainMultiplier = gainMultiplier;
	}


	public void destroy()
	{
		track.release();
		track = null;
	}


	protected AudioX getAudio()
	{
		return track;
	}


	protected float getGain(double multiplier)
	{
		return (float) (baseGain * gainMultiplier.get() * multiplier);
	}


	protected float getPitch(double multiplier)
	{
		return (float) (basePitch * multiplier);
	}


	/**
	 * Get if audio is valid
	 * 
	 * @return is valid
	 */
	protected boolean canPlay()
	{
		return (track != null);
	}


	public void load()
	{
		if (canPlay()) track.load();
	}
}
