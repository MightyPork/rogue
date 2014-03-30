package mightypork.rogue.input.events;


import mightypork.utils.math.coord.Coord;
import mightypork.utils.patterns.subscription.Handleable;


public class MouseMotionEvent implements Handleable<MouseMotionEvent.Listener> {

	private Coord move;
	private Coord pos;


	public MouseMotionEvent(Coord pos, Coord move) {
		this.move = move;
		this.pos = pos;
	}


	/**
	 * @return movement since last {@link MouseMotionEvent}
	 */
	public Coord getPosDelta()
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
		public void receive(MouseMotionEvent event);
	}

}
