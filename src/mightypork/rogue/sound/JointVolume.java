package mightypork.rogue.sound;


import mightypork.utils.math.Calc;
import mightypork.utils.objects.Mutable;


/**
 * Volume combined of multiple volumes, combining them (multiplication).
 * 
 * @author MightyPork
 */
public class JointVolume extends Mutable<Double> {
	
	private final Mutable<Double>[] volumes;
	
	
	/**
	 * Create joint volume with master gain of 1
	 * 
	 * @param volumes individual volumes to join
	 */
	public JointVolume(Mutable<Double>... volumes) {
		super(1D);
		this.volumes = volumes;
	}
	
	
	/**
	 * Get combined gain (multiplied)
	 */
	@Override
	public Double get()
	{
		double d = super.get();
		for (Mutable<Double> v : volumes)
			d *= v.get();
		
		return Calc.clampd(d, 0, 1);
	}
	
	
	/**
	 * Set master gain
	 */
	@Override
	public void set(Double o)
	{
		super.set(o);
	}
}
