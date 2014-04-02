package mightypork.rogue;


import mightypork.rogue.display.DisplaySystem;
import mightypork.rogue.input.InputSystem;
import mightypork.rogue.sounds.SoundSystem;
import mightypork.utils.control.bus.EventBus;


/**
 * App interface visible to subsystems
 * 
 * @author MightyPork
 */
public interface AppAccess {
	
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
	 * @return event bus
	 */
	abstract EventBus bus();
	
	
	/**
	 * Quit to OS<br>
	 * Destroy app & exit VM
	 */
	abstract void shutdown();
	
}
