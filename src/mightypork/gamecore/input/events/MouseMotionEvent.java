package mightypork.gamecore.input.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.NotLoggedEvent;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.gamecore.util.math.constraints.vect.VectConst;


/**
 * Mouse moved
 * 
 * @author MightyPork
 */
@NotLoggedEvent
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
