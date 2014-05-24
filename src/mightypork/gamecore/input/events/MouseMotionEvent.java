package mightypork.gamecore.input.events;


import mightypork.dynmath.vect.Vect;
import mightypork.dynmath.vect.VectConst;
import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.NotLoggedEvent;


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
	public void handleBy(MouseMotionHandler keh)
	{
		keh.receive(this);
	}
	
}
