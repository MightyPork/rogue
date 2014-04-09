package mightypork.gamecore.control.interf;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Indicates that the marked method is not implemented and can be safely
 * overriden.
 * 
 * @author MightyPork
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(value = { ElementType.METHOD })
public @interface NoImpl {
	//
}
