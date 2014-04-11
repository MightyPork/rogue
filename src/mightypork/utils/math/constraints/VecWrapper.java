package mightypork.utils.math.constraints;


import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecView;


public class VecWrapper implements VecConstraint {
	
	private final Vec wrapped;
	
	
	public VecWrapper(Vec wrapped) {
		this.wrapped = wrapped;
	}
	
	
	@Override
	public VecView getVec()
	{
		return wrapped.view();
	}
}
