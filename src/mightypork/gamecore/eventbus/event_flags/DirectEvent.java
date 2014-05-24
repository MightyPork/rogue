package mightypork.gamecore.eventbus.event_flags;


import java.lang.annotation.*;


/**
 * Event that should not be queued.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface DirectEvent {}
