package mightypork.gamecore.eventbus.event_flags;


import java.lang.annotation.*;


/**
 * Handled only by the first client, then discarded.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface SingleReceiverEvent {}
