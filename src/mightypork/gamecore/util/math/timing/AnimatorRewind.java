package mightypork.gamecore.util.math.timing;


import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;


/**
 * Animator that upon reaching top, jumps straight to zero and continues another
 * cycle.
 * 
 * @author Ondřej Hruška
 */
public class AnimatorRewind extends Animator {
	
	public AnimatorRewind(double start, double end, double period, Easing easing)
	{
		super(start, end, period, easing);
	}
	
	
	public AnimatorRewind(double start, double end, double period)
	{
		super(start, end, period);
	}
	
	
	public AnimatorRewind(double period, Easing easing)
	{
		super(period, easing);
	}
	
	
	public AnimatorRewind(double period)
	{
		super(period);
	}
	
	
	@Override
	protected void nextCycle(NumAnimated anim)
	{
		anim.reset();
		anim.fadeIn();
	}
	
}
