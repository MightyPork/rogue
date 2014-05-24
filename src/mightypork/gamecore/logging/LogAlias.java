package mightypork.gamecore.logging;


import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Specify pretty name to be used when logging (eg. <code>Log.str()</code>)
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogAlias {
	
	String name();
}
