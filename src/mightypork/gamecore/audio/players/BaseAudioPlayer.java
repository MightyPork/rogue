package mightypork.gamecore.audio.players;


import mightypork.gamecore.audio.DeferredAudio;
import mightypork.gamecore.audio.Volume;
import mightypork.utils.interfaces.Destroyable;


/**
 * Basic abstract player
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class BaseAudioPlayer implements Destroyable {
	
	/** the track */
	private final DeferredAudio audio;
	
	/** base gain for sfx */
	private final double baseGain;
	
	/** base pitch for sfx */
	private final double basePitch;
	
	/** dedicated volume control */
	private final Volume gainMultiplier;
	
	
	/**
	 * @param track audio resource
	 * @param basePitch base pitch (pitch multiplier)
	 * @param baseGain base gain (volume multiplier)
	 * @param volume colume control
	 */
	public BaseAudioPlayer(DeferredAudio track, double basePitch, double baseGain, Volume volume) {
		this.audio = track;
		
		this.baseGain = baseGain;
		this.basePitch = basePitch;
		
		if (volume == null) volume = new Volume(1D);
		
		this.gainMultiplier = volume;
	}
	
	
	@Override
	public void destroy()
	{
		audio.destroy();
	}
	
	
	/**
	 * @return audio resource
	 */
	protected DeferredAudio getAudio()
	{
		return audio;
	}
	
	
	/**
	 * Get play gain, computed based on volume and given multiplier
	 * 
	 * @param multiplier extra volume adjustment
	 * @return computed gain
	 */
	protected double computeGain(double multiplier)
	{
		return baseGain * gainMultiplier.get() * multiplier;
	}
	
	
	/**
	 * Get pitch
	 * 
	 * @param multiplier pitch adjustment
	 * @return computed pitch
	 */
	protected double computePitch(double multiplier)
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
	
	
	/**
	 * force load the resource
	 */
	public void load()
	{
		if (hasAudio()) audio.load();
	}
}
