package mightypork.gamecore.resources.loading;


import java.lang.annotation.*;


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
public @interface MustLoadInRenderingContext {}
