package mightypork.utils.math.coord;

import mightypork.utils.math.Calc;
import mightypork.utils.time.Updateable;


/**
 * TODO revise
 * 
 * @author MightyPork
 */
public class CoordAnimated extends Coord implements Updateable {
	
	private double animTime = 0;
	private Coord offs;
	private Coord start;
	private double time = 0;
	
	
	/**
	 * Update delta timing
	 * 
	 * @param delta
	 *            delta time to add
	 */
	@Override
	public void update(double delta)
	{
		if (start == null) start = new Coord();
		if (offs == null) offs = new Coord();
		animTime = Calc.clampd(animTime + delta, 0, time);
		if (animIsFinished()) {
			time = 0;
			animTime = 0;
			start.setTo(this);
		}
	}
	
	
	/**
	 * Remember position (other changes will be for animation)
	 */
	public void animRemember()
	{
		if (start == null) start = new Coord();
		if (offs == null) offs = new Coord();
		start.setTo(this);
		offs = Coord.zero();
	}
	
	
	/**
	 * Start animation
	 * 
	 * @param time
	 *            anim length
	 */
	public void animStart(double time)
	{
		if (start == null) start = new Coord();
		if (offs == null) offs = new Coord();
		this.time = time;
		animTime = 0;
		offs = start.vecTo(this);
	}
	
	
	/**
	 * Stop animation, assign to current value
	 */
	public void animStop()
	{
		setTo(animGetCurrent());
		animRemember();
		animTime = 0;
	}
	
	
	/**
	 * Get if animation is finished
	 * 
	 * @return is finished
	 */
	public boolean animIsFinished()
	{
		return animTime >= time;
	}
	
	
	/**
	 * Get current value (animated)
	 * 
	 * @return curent value
	 */
	public Coord animGetCurrent()
	{
		if (time == 0) return copy(); // avoid zero division
			
		if (start == null) start = new Coord();
		if (offs == null) offs = new Coord();
		
		if (animIsFinished()) return this;
		
		return start.add(offs.mul(animTime / time));
	}
	
}
