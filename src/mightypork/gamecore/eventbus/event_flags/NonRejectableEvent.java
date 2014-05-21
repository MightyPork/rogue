package mightypork.gamecore.eventbus.event_flags;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Event that is forcibly delivered to all clients (bypass Toggleable etc)
 * 
 * @author MightyPork
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface NonRejectableEvent {
	
}
