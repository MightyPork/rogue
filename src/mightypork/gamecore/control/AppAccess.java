package mightypork.gamecore.control;


import mightypork.gamecore.audio.SoundSystem;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.util.control.eventbus.BusAccess;


/**
 * App interface visible to subsystems
 * 
 * @author MightyPork
 */
public interface AppAccess extends BusAccess {
	
	/**
	 * @return sound system
	 */
	abstract SoundSystem getSoundSystem();
	
	
	/**
	 * @return input system
	 */
	abstract InputSystem getInput();
	
	
	/**
	 * @return display system
	 */
	abstract DisplaySystem getDisplay();
	
	
	/**
	 * Quit to OS<br>
	 * Destroy app & exit VM
	 */
	abstract void shutdown();
	
}
