package mightypork.gamecore.control.bus.events;


import mightypork.gamecore.control.bus.events.types.UnloggedEvent;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVal;


/**
 * Mouse moved
 * 
 * @author MightyPork
 */
@UnloggedEvent
public class MouseMotionEvent implements Event<MouseMotionEvent.Listener> {
	
	private final VectVal move;
	private final VectVal pos;
	
	
	/**
	 * @param pos end pos
	 * @param move move vector
	 */
	public MouseMotionEvent(Vect pos, Vect move) {
		this.move = move.copy();
		this.pos = pos.copy();
	}
	
	
	/**
	 * @return movement since last {@link MouseMotionEvent}
	 */
	public VectVal getMove()
	{
		return move;
	}
	
	
	/**
	 * @return current mouse position
	 */
	public VectVal getPos()
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
