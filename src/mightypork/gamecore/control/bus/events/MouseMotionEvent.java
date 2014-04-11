package mightypork.gamecore.control.bus.events;


import mightypork.gamecore.control.bus.events.types.UnloggedEvent;
import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecView;


/**
 * Mouse moved
 * 
 * @author MightyPork
 */
@UnloggedEvent
public class MouseMotionEvent implements Event<MouseMotionEvent.Listener> {
	
	private final Vec move;
	private final Vec pos;
	
	
	/**
	 * @param pos end pos
	 * @param move move vector
	 */
	public MouseMotionEvent(Vec pos, Vec move) {
		this.move = move;
		this.pos = pos;
	}
	
	
	/**
	 * @return movement since last {@link MouseMotionEvent}
	 */
	public VecView getMove()
	{
		return move.view();
	}
	
	
	/**
	 * @return current mouse position
	 */
	public VecView getPos()
	{
		return pos.view();
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
