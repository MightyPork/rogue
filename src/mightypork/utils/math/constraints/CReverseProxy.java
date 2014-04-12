package mightypork.utils.math.constraints;


import mightypork.utils.math.coord.VecView;


public class CReverseProxy extends VecView {
	
	private final VecConstraint constraint;
	
	
	public CReverseProxy(VecConstraint wrapped) {
		this.constraint = wrapped;
	}
	
	
	@Override
	public double x()
	{
		return constraint.getVec().x();
	}
	
	
	@Override
	public double y()
	{
		return constraint.getVec().y();
	}
	
	
	@Override
	public double z()
	{
		return constraint.getVec().z();
	}
}
