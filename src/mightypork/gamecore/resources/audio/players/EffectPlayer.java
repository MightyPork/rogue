package mightypork.gamecore.resources.audio.players;


import mightypork.dynmath.vect.Vect;
import mightypork.gamecore.resources.audio.LazyAudio;
import mightypork.gamecore.resources.audio.Volume;


/**
 * Player for one-off effects
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class EffectPlayer extends BaseAudioPlayer {
	
	/**
	 * @param track audio resource
	 * @param basePitch base pitch (pitch multiplier)
	 * @param baseGain base gain (volume multiplier)
	 * @param volume volume control
	 */
	public EffectPlayer(LazyAudio track, double basePitch, double baseGain, Volume volume)
	{
		super(track, (float) basePitch, (float) baseGain, volume);
	}
	
	
	/**
	 * Play at listener
	 * 
	 * @param pitch play pitch
	 * @param gain play gain
	 * @return source id
	 */
	public int play(double pitch, double gain)
	{
		if (!hasAudio()) return -1;
		
		return getAudio().playAsEffect(getPitch(pitch), getGain(gain), false);
	}
	
	
	/**
	 * Play at listener
	 * 
	 * @param gain play gain
	 * @return source id
	 */
	public int play(double gain)
	{
		return play(1, gain);
	}
	
	
	/**
	 * Play at given position
	 * 
	 * @param pitch play pitch
	 * @param gain play gain
	 * @param pos play position
	 * @return source id
	 */
	public int play(double pitch, double gain, Vect pos)
	{
		if (!hasAudio()) return -1;
		
		return getAudio().playAsEffect(getPitch(pitch), getGain(gain), false, pos);
	}
	
}
