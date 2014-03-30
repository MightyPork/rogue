package mightypork.rogue.input.events;


import mightypork.utils.math.coord.Coord;
import mightypork.utils.patterns.subscription.Handleable;


/**
 * Mouse button / wheel event
 * 
 * @author MightyPork
 */
public class MouseButtonEvent implements Handleable<MouseButtonEvent.Listener> {

	public static final int BUTTON_LEFT = 0;
	public static final int BUTTON_MIDDLE = 1;
	public static final int BUTTON_RIGHT = 2;

	private int button;
	private int wheeld;
	private Coord pos;
	private boolean down;


	/**
	 * Mouse button event
	 * 
	 * @param pos event position
	 * @param button button id
	 * @param down button pressed
	 * @param wheeld wheel change
	 */
	public MouseButtonEvent(Coord pos, int button, boolean down, int wheeld) {
		this.button = button;
		this.down = down;
		this.pos = pos;
		this.wheeld = wheeld;
	}


	/**
	 * @return true if the event was caused by a button state change
	 */
	public boolean isButtonEvent()
	{
		return button != -1;
	}


	/**
	 * @return true if the event was caused by a wheel change
	 */
	public boolean isWheelEvent()
	{
		return wheeld != 0;
	}


	/**
	 * @return button id or -1 if none was pressed
	 */
	public int getButton()
	{
		return button;
	}


	/**
	 * @return number of steps the wheel changed since last event
	 */
	public int getWheelDelta()
	{
		return wheeld;
	}


	/**
	 * @return mouse position when the event occurred
	 */
	public Coord getPos()
	{
		return pos;
	}


	/**
	 * @return true if button was just pressed
	 */
	public boolean isDown()
	{
		return button != -1 && down;
	}


	/**
	 * @return true if button was just released
	 */
	public boolean isUp()
	{
		return button != -1 && !down;
	}


	@Override
	public void handleBy(Listener handler)
	{
		handler.receive(this);
	}

	public interface Listener {

		/**
		 * Handle an event
		 * 
		 * @param event event
		 */
		public void receive(MouseButtonEvent event);
	}
}
