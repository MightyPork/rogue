package mightypork.gamecore.control.bus.events;


import mightypork.gamecore.control.bus.events.types.UnloggedEvent;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectView;


/**
 * Mouse moved
 * 
 * @author MightyPork
 */
@UnloggedEvent
public class MouseMotionEvent implements Event<MouseMotionEvent.Listener> {
	
	private final VectView move;
	private final VectView pos;
	
	
	/**
	 * @param pos end pos
	 * @param move move vector
	 */
	public MouseMotionEvent(Vect pos, Vect move) {
		this.move = move.getValue();
		this.pos = pos.getValue();
	}
	
	
	/**
	 * @return movement since last {@link MouseMotionEvent}
	 */
	public VectView getMove()
	{
		return move;
	}
	
	
	/**
	 * @return current mouse position
	 */
	public VectView getPos()
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
