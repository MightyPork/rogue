package mightypork.gamecore.control.bus.events;


import mightypork.gamecore.control.bus.events.types.UnloggedEvent;
import mightypork.utils.math.coord.Coord;


/**
 * Mouse moved
 * 
 * @author MightyPork
 */
@UnloggedEvent
public class MouseMotionEvent implements Event<MouseMotionEvent.Listener> {
	
	private final Coord move;
	private final Coord pos;
	
	
	public MouseMotionEvent(Coord pos, Coord move) {
		this.move = move;
		this.pos = pos;
	}
	
	
	/**
	 * @return movement since last {@link MouseMotionEvent}
	 */
	public Coord getMove()
	{
		return move;
	}
	
	
	/**
	 * @return current mouse position
	 */
	public Coord getPos()
	{
		return pos;
	}
	
	
	@Override
	public void handleBy(Listener keh)
	{
		keh.receive(this);
	}
	
	public interface Listener {
		
		/**
		 * Handle an event
		 * 
		 * @param event event
		 */
		void receive(MouseMotionEvent event);
	}
	
}
