package mightypork.rogue.sounds;


import mightypork.utils.math.Calc;
import mightypork.utils.objects.Mutable;


/**
 * Volume multiplex
 * 
 * @author MightyPork
 */
public class JointVolume extends Mutable<Float> {

	private Mutable<Float>[] volumes;


	/**
	 * CReate joint volume with master gain of 1
	 * 
	 * @param volumes individual volumes to join
	 */
	public JointVolume(Mutable<Float>... volumes) {
		super(1F);
		this.volumes = volumes;
	}


	/**
	 * Get combined gain (multiplied)
	 */
	@Override
	public Float get()
	{
		float f = super.get();
		for (Mutable<Float> v : volumes)
			f *= v.get();

		return Calc.clampf(f, 0, 1);
	}


	/**
	 * Set master gain
	 */
	@Override
	public void set(Float o)
	{
		super.set(o);
	}
}
