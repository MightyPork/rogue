package mightypork.utils.math.num;


import mightypork.utils.math.constraints.NumBound;


public interface Num extends NumBound {
	
	Num ZERO = NumVal.make(0);
	Num ONE = NumVal.make(1);
	
	
	double value();
	
	
	NumView view();
	
	
	NumVal copy();
}
