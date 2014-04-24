package mightypork.util.control.eventbus.event_flags;


import java.lang.annotation.*;


/**
 * Event that should be queued with given delay (default: 0);
 * 
 * @author MightyPork
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface DelayedEvent {
	
	/**
	 * @return event dispatch delay [seconds]
	 */
	double delay() default 0;
}