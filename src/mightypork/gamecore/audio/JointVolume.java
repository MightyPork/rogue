package mightypork.gamecore.audio;


import mightypork.utils.math.Calc;


/**
 * Volume combined of multiple volumes, combining them (multiplication).
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class JointVolume extends Volume {
	
	private final Volume[] volumes;
	
	
	/**
	 * Create joint volume with master gain of 1
	 * 
	 * @param volumes individual volumes to join
	 */
	@SafeVarargs
	public JointVolume(Volume... volumes) {
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
		for (final Volume v : volumes)
			d *= v.get();
		
		return Calc.clamp(d, 0, 1);
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
