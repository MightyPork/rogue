package mightypork.rogue.input;


import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Vec;


/**
 * Input event handler
 * 
 * @author MightyPork
 */
public interface InputHandler {

	/**
	 * Called each update tick, if the mouse position was changed.
	 * 
	 * @param pos mouse position
	 * @param move mouse motion
	 * @param wheelDelta mouse wheel delta
	 */
	public void onMouseMove(Coord pos, Vec move, int wheelDelta);


	/**
	 * Mouse event handler.
	 * 
	 * @param button button which caused this event
	 * @param down true = down, false = up
	 * @param wheelDelta number of steps the wheel turned since last event
	 * @param pos mouse position
	 * @param deltaPos delta mouse position
	 */
	public void onMouseButton(int button, boolean down, int wheelDelta, Coord pos, Coord deltaPos);


	/**
	 * Key event handler.
	 * 
	 * @param key key index, constant Keyboard.KEY_???
	 * @param c character typed, if any
	 * @param down true = down, false = up
	 */
	public void onKey(int key, char c, boolean down);


	/**
	 * In this method screen can handle static inputs, that is:
	 * Keyboard.isKeyDown, Mouse.isButtonDown etc.
	 */
	public void handleKeyStates();
}
