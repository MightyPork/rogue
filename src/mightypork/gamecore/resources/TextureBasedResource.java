package mightypork.gamecore.resources;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Resource that is texture-based and therefore needs to be loaded in the main
 * thread (ie. main loop).
 * 
 * @author Ondřej Hruška (MightyPork)
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface TextureBasedResource {}
