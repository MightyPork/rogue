package mightypork.gamecore.core.modules;


import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.resources.audio.SoundSystem;
import mightypork.utils.eventbus.BusAccess;


/**
 * App interface visible to subsystems
 * 
 * @author Ondřej Hruška (MightyPork)
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
	 * Quit to OS<br>
	 * Destroy app & exit VM
	 */
	abstract void shutdown();
	
}
