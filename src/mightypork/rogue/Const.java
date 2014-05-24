package mightypork.rogue;


/**
 * Application constants
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public final class Const {
	
	// STRINGS
	public static final int VERSION = 6;
	
	public static final String APP_NAME = "Rogue: Savage Rats";
	public static final String TITLEBAR = APP_NAME + ", v" + VERSION;
	
	// AUDIO
	public static final int FPS_RENDER = 120; // max
	
	// INITIAL WINDOW SIZE
	public static final int WINDOW_W = 640;
	public static final int WINDOW_H = 480;
	
	/** Render dark in unknown area & skip invisible stuff */
	public static boolean RENDER_UFOG = true;
}
