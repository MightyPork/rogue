package mightypork.gamecore.control.bus.events;


import mightypork.gamecore.control.bus.events.types.UnloggedEvent;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.VectConst;


/**
 * Mouse moved
 * 
 * @author MightyPork
 */
@UnloggedEvent
public class MouseMotionEvent implements Event<MouseMotionEvent.Listener> {
	
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
	public void handleBy(Listener keh)
	{
		keh.receive(this);
	}
	
	/**
	 * {@link MouseMotionEvent} listener
	 * 
	 * @author MightyPork
	 */
	public interface Listener {
		
		/**
		 * Handle an event
		 * 
		 * @param event event
		 */
		void receive(MouseMotionEvent event);
	}
	
}
