package mightypork.utils.math.constraints;


import mightypork.utils.math.coord.VecView;


public abstract class VecConstraintSynth implements VecConstraint {
	
	@Override
	public abstract VecView getVec();
	
	
	@Override
	public NumberConstraint xc()
	{
		return Constraints.cX(getVec());
	}
	
	
	@Override
	public NumberConstraint yc()
	{
		return Constraints.cY(getVec());
	}
	
	
	@Override
	public NumberConstraint zc()
	{
		return Constraints.cZ(getVec());
	}
	
}
