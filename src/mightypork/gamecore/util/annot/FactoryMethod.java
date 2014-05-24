package mightypork.gamecore.util.annot;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Marks a static factory method. This is a description annotation and has no
 * other function.
 * 
 * @author Ondřej Hruška
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@Documented
public @interface FactoryMethod {
	
}
