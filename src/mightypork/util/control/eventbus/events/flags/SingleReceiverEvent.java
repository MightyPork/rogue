package mightypork.util.control.eventbus.events.flags;


import java.lang.annotation.*;


/**
 * Handled only by the first client, then discarded.
 * 
 * @author MightyPork
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface SingleReceiverEvent {}
