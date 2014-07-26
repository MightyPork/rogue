package mightypork.gamecore.audio.players;


import mightypork.gamecore.audio.DeferredAudio;
import mightypork.gamecore.audio.Volume;
import mightypork.utils.math.constraints.vect.Vect;


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
	public EffectPlayer(DeferredAudio track, double basePitch, double baseGain, Volume volume) {
		super(track, (float) basePitch, (float) baseGain, volume);
	}
	
	
	/**
	 * Play at listener
	 * 
	 * @param pitch play pitch
	 * @param gain play gain
	 */
	public void play(double pitch, double gain)
	{
		if (!hasAudio()) return;
		
		getAudio().play(computePitch(pitch), computeGain(gain), false);
	}
	
	
	/**
	 * Play at listener
	 * 
	 * @param gain play gain
	 */
	public void play(double gain)
	{
		play(1, gain);
	}
	
	
	/**
	 * Play at given position
	 * 
	 * @param pitch play pitch
	 * @param gain play gain
	 * @param pos play position
	 */
	public void play(double pitch, double gain, Vect pos)
	{
		if (!hasAudio()) return;
		
		getAudio().play(computePitch(pitch), computeGain(gain), false, pos);
	}
	
}
