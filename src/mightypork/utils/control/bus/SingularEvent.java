package mightypork.utils.control.bus;


import java.lang.annotation.*;


/**
 * Event that is handled by only single client, and then discarded (ie. only one
 * client receives it when it's broadcasted).
 * 
 * @author MightyPork
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface SingularEvent {}
