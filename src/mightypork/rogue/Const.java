package mightypork.rogue;


import mightypork.utils.math.coord.Coord;


/**
 * Application constants
 * 
 * @author MightyPork
 */
public class Const {

	// STRINGS
	public static final int VERSION = 1;

	public static final String APP_NAME = "Rogue";
	public static final String TITLEBAR = APP_NAME + " v." + VERSION;

	// AUDIO
	public static final Coord LISTENER_POS = Coord.ZERO;

	public static final int FPS_RENDER = 200; // max
	public static final long FPS_GUI_UPDATE = 60;

	// INITIAL WINDOW SIZE
	public static final int WINDOW_SIZE_X = 1024;
	public static final int WINDOW_SIZE_Y = 768;
}
