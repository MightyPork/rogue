package mightypork.gamecore.app;


import mightypork.gamecore.eventbus.BusAccess;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.resources.audio.SoundSystem;


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
