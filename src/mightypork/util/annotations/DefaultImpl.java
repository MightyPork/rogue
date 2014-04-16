package mightypork.util.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Marked method can be safely overriden; it's left blank (or with default
 * implementation) as a convenience.
 * 
 * @author MightyPork
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(value = { ElementType.METHOD })
public @interface DefaultImpl {
	//
}
