package mightypork.gamecore.core;


import java.lang.annotation.*;


/**
 * Indicates that an {@link InitTask} can safely be ignored if it's dependencies
 * are not satisfied.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface OptionalInitTask {
	
}
