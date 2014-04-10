package mightypork.utils.math.coord;


import mightypork.gamecore.gui.constraints.NumberConstraint;


/**
 * Coord values provider
 * 
 * @author MightyPork
 */
public interface CoordValue {
	
	double x();
	
	
	double y();
	
	
	double z();
	
	
	NumberConstraint xc();
	
	
	NumberConstraint zc();
	
	
	NumberConstraint yc();
	
}
