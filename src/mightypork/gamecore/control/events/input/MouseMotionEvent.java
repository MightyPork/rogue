package mightypork.gamecore.control.events.input;


import mightypork.util.control.eventbus.BusEvent;
import mightypork.util.control.eventbus.event_flags.UnloggedEvent;
import mightypork.util.math.constraints.vect.Vect;
import mightypork.util.math.constraints.vect.VectConst;


/**
 * Mouse moved
 * 
 * @author MightyPork
 */
@UnloggedEvent
public class MouseMotionEvent extends BusEvent<MouseMotionListener> {
	
	private final VectConst move;
	private final VectConst pos;
	
	
	/**
	 * @param pos end pos
	 * @param move move vector
	 */
	public MouseMotionEvent(Vect pos, Vect move)
	{
		this.move = move.freeze();
		this.pos = pos.freeze();
	}
	
	
	/**
	 * @return movement since last {@link MouseMotionEvent}
	 */
	public VectConst getMove()
	{
		return move;
	}
	
	
	/**
	 * @return current mouse position
	 */
	public VectConst getPos()
	{
		return pos;
	}
	
	
	@Override
	public void handleBy(MouseMotionListener keh)
	{
		keh.receive(this);
	}
	
}