package mightypork.rogue.sounds;


import mightypork.utils.math.coord.Coord;
import mightypork.utils.objects.Mutable;


public class EffectPlayer extends AudioPlayer {

	public EffectPlayer(AudioX track, double basePitch, double baseGain, Mutable<Float> gainMultiplier) {
		super(track, (float) basePitch, (float) baseGain, gainMultiplier);
	}


	public int play(double pitch, double gain)
	{
		if (!canPlay()) return -1;

		return getAudio().playAsSoundEffect(getPitch(pitch), getGain(gain), false);
	}


	public int play(double gain)
	{
		return play(1, gain);
	}


	public int play(double pitch, double gain, Coord pos)
	{
		if (!canPlay()) return -1;

		return getAudio().playAsSoundEffect(getPitch(pitch), getGain(gain), false, pos);
	}

}
