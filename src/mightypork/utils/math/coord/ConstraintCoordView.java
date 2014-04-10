package mightypork.utils.math.coord;


import mightypork.gamecore.gui.constraints.NumberConstraint;


public class ConstraintCoordView extends CoordView {
	
	private final NumberConstraint xc;
	private final NumberConstraint yc;
	private final NumberConstraint zc;
	
	
	public ConstraintCoordView(NumberConstraint x, NumberConstraint y, NumberConstraint z) {
		super(null);
		this.xc = x;
		this.yc = y;
		this.zc = z;
	}
	
	
	@Override
	public double x()
	{
		return xc == null ? 0 : xc.getValue();
	}
	
	
	@Override
	public double y()
	{
		return yc == null ? 0 : yc.getValue();
	}
	
	
	@Override
	public double z()
	{
		return zc == null ? 0 : zc.getValue();
	}
	
}
