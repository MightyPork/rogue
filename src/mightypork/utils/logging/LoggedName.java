package mightypork.utils.logging;


import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Specify pretty name to be used when logging (<code>Log.str()</code>)
 * 
 * @author MightyPork
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LoggedName {
	
	String name();
}
