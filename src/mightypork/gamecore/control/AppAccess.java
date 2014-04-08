package mightypork.gamecore.control;


import mightypork.gamecore.audio.SoundSystem;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;


/**
 * App interface visible to subsystems
 * 
 * @author MightyPork
 */
public interface AppAccess extends BusAccess {
	
	/**
	 * @return sound system
	 */
	abstract SoundSystem snd();
	
	
	/**
	 * @return input system
	 */
	abstract InputSystem input();
	
	
	/**
	 * @return display system
	 */
	abstract DisplaySystem disp();
	
	
	/**
	 * Quit to OS<br>
	 * Destroy app & exit VM
	 */
	abstract void shutdown();
	
}
