package mightypork.utils.math.coord;


import mightypork.utils.math.constraints.NumberConstraint;


public abstract class AbstractVec implements Vec {
	
	private AbstractVecProxy view;
	private NumberConstraint constraintX;
	private NumberConstraint constraintY;
	private NumberConstraint constraintZ;
	
	
	@Override
	public VecView getVec()
	{
		return view();
	}
	
	
	@Override
	public abstract double x();
	
	
	@Override
	public abstract double y();
	
	
	@Override
	public abstract double z();
	
	
	@Override
	public int xi()
	{
		return (int) Math.round(x());
	}
	
	
	@Override
	public int yi()
	{
		return (int) Math.round(y());
	}
	
	
	@Override
	public int zi()
	{
		return (int) Math.round(z());
	}
	
	
	@Override
	public NumberConstraint xc()
	{
		if (constraintX == null) constraintX = new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return x();
			}
		};
		
		return constraintX;
	}
	
	
	@Override
	public NumberConstraint yc()
	{
		if (constraintY == null) constraintY = new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return y();
			}
		};
		
		return constraintY;
	}
	
	
	@Override
	public NumberConstraint zc()
	{
		if (constraintZ == null) constraintZ = new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return z();
			}
		};
		
		return constraintZ;
	}
	
	
	@Override
	public double size()
	{
		final double x = x(), y = y(), z = z();
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	
	@Override
	public boolean isZero()
	{
		return x() == 0 && y() == 0 && z() == 0;
	}
	
	
	@Override
	public VecView value()
	{
		return new ConstVec(this);
	}
	
	
	@Override
	public double distTo(Vec point)
	{
		final double dx = x() - point.x();
		final double dy = y() - point.y();
		final double dz = z() - point.z();
		
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	
	
	@Override
	public VecView midTo(Vec point)
	{
		final double dx = (point.x() - x()) * 0.5;
		final double dy = (point.y() - y()) * 0.5;
		final double dz = (point.z() - z()) * 0.5;
		
		return VecView.make(dx, dy, dz);
	}
	
	
	@Override
	public VecView vecTo(Vec point)
	{
		return VecView.make(point.x() - x(), point.y() - y(), point.z() - z());
	}
	
	
	@Override
	public VecView cross(Vec vec)
	{
		//@formatter:off
		return VecView.make(
				y() * vec.z() - z() * vec.y(),
				z() * vec.x() - x() * vec.z(),
				x() * vec.y() - y() * vec.x());
		//@formatter:on
	}
	
	
	@Override
	public double dot(Vec vec)
	{
		return x() * vec.x() + y() * vec.y() + z() * vec.z();
	}
	
	
	@Override
	public VecMutable mutable()
	{
		return VecMutable.make(this);
	}
	
	
	@Override
	public VecView view()
	{
		if (view == null) view = new VecProxy(this);
		
		return view;
	}
	
}
