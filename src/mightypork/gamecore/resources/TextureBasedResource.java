package mightypork.gamecore.resources;


import java.lang.annotation.*;


/**
 * Resource that is texture-based and therefore needs to be loaded in the main
 * thread (ie. main loop).
 * 
 * @author Ondřej Hruška
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface TextureBasedResource {}
