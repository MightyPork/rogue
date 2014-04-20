package mightypork.util.files.ion;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>
 * Implicit constructor marked like this is intended to be solely used for ION
 * de-serialization.
 * </p>
 * <p>
 * Constructors marked like this should create a functional instance with
 * default values.
 * </p>
 * <p>
 * This is a descriptive annotation and has no other function.
 * </p>
 * 
 * @author MightyPork
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.CONSTRUCTOR)
@Documented
public @interface IonConstructor {
	
}
