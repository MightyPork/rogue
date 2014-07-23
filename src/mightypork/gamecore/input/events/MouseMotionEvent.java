package mightypork.gamecore.input.events;


import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.NotLoggedEvent;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.VectConst;


/**
 * Mouse moved
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@NotLoggedEvent
public class MouseMotionEvent extends BusEvent<MouseMotionHandler> {
	
	private final VectConst move;
	private final VectConst pos;
	
	
	/**
	 * @param pos end pos
	 * @param move move vector
	 */
	public MouseMotionEvent(Vect pos, Vect move) {
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
	public void handleBy(MouseMotionHandler keh)
	{
		keh.receive(this);
	}
	
}
