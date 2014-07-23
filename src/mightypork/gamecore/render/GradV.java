package mightypork.gamecore.render;


import mightypork.utils.math.color.Color;


/**
 * Linear vertical gradient
 * 
 * @author MightyPork
 */
public class GradV extends Grad {
	
	public GradV(Color top, Color bottom) {
		super(top, top, bottom, bottom);
	}
}
