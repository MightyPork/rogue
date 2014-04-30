package mightypork.gamecore.util.math.timing;


import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;


/**
 * Animator that upon reaching max, animates back down and then up again
 * 
 * @author MightyPork
 */
public class AnimatorBounce extends Animator {
	
	private final boolean wasUp = false;
	
	
	public AnimatorBounce(double start, double end, double period, Easing easing)
	{
		super(start, end, period, easing);
	}
	
	
	public AnimatorBounce(double start, double end, double period)
	{
		super(start, end, period);
	}
	
	
	public AnimatorBounce(double period, Easing easing)
	{
		super(period, easing);
	}
	
	
	public AnimatorBounce(double period)
	{
		super(period);
	}
	
	
	@Override
	protected void nextCycle(NumAnimated anim)
	{
		if (wasUp) {
			anim.fadeOut();
		} else {
			anim.fadeIn();
		}
	}
	
}
