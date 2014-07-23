package mightypork.gamecore.render;


import mightypork.utils.math.color.Color;


/**
 * Linear horizontal gradient
 * 
 * @author MightyPork
 */
public class GradH extends Grad {
	
	public GradH(Color left, Color right) {
		super(left, right, right, left);
	}
}
