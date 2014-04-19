package mightypork.util.files.ion;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Implicit constructor marked like this is intended to be solely used for ION
 * de-serialization. This is a description annotation and has no other function.
 * 
 * @author MightyPork
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.CONSTRUCTOR)
@Documented
public @interface IonConstructor {
	
}
