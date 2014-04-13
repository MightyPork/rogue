package mightypork.utils.math.vect;


import mightypork.utils.math.constraints.NumBound;
import mightypork.utils.math.num.Num;
import mightypork.utils.math.num.NumVal;
import mightypork.utils.math.num.NumView;


/**
 * Dynamic vector math functions, to be used with a view.
 * 
 * @author MightyPork
 */
abstract class VectMathDynamic extends VectMath<VectView, NumBound> {
	
	private NumView size;
	private VectView neg;
	private VectView half;
	private VectView abs;
	private NumView xc;
	private NumView yc;
	private NumView zc;
	
	
	/**
	 * @return X constraint
	 */
	@Override
	public final NumView xn()
	{
		if (xc == null) xc = new NumView() {
			
			@Override
			public double value()
			{
				return x();
			}
		};
		
		return xc;
	}
	
	
	/**
	 * @return Y constraint
	 */
	@Override
	public final NumView yn()
	{
		if (yc == null) yc = new NumView() {
			
			@Override
			public double value()
			{
				return y();
			}
		};
		
		return yc;
	}
	
	
	/**
	 * @return Z constraint
	 */
	@Override
	public final NumView zn()
	{
		if (zc == null) zc = new NumView() {
			
			@Override
			public double value()
			{
				return z();
			}
		};
		
		return zc;
	}
	
	
	@Override
	public VectView abs()
	{
		if (abs == null) abs = new VectView() {
			
			final VectMathDynamic t = VectMathDynamic.this;
			
			
			@Override
			public double x()
			{
				return Math.abs(t.x());
			}
			
			
			@Override
			public double y()
			{
				return Math.abs(t.y());
			}
			
			
			@Override
			public double z()
			{
				return Math.abs(t.z());
			}
		};
		
		return abs;
	}
	
	
	@Override
	public VectView add(Vect vec)
	{
		return add(vec.view().xn(), vec.view().yn(), vec.view().zn());
	}
	
	
	@Override
	public VectView add(double x, double y)
	{
		return add(x, y, 0);
	}
	
	
	@Override
	public VectView add(final double x, final double y, final double z)
	{
		return new VectView() {
			
			final VectMathDynamic t = VectMathDynamic.this;
			
			
			@Override
			public double x()
			{
				return t.x() + x;
			}
			
			
			@Override
			public double y()
			{
				return t.y() + y;
			}
			
			
			@Override
			public double z()
			{
				return t.z() + z;
			}
		};
	}
	
	
	@Override
	public VectView add(Num x, Num y)
	{
		return add(x, y, Num.ZERO);
	}
	
	
	@Override
	public VectView add(final Num x, final Num y, final Num z)
	{
		return new VectView() {
			
			final Vect t = VectMathDynamic.this;
			
			
			@Override
			public double x()
			{
				return t.x() + x.value();
			}
			
			
			@Override
			public double y()
			{
				return t.y() + y.value();
			}
			
			
			@Override
			public double z()
			{
				return t.z() + z.value();
			}
		};
	}
	
	
	@Override
	public VectView half()
	{
		if (half == null) half = mul(0.5);
		return half;
	}
	
	
	@Override
	public VectView mul(double d)
	{
		return mul(d, d, d);
	}
	
	
	@Override
	public VectView mul(Vect vec)
	{
		return mul(vec.view().xn(), vec.view().yn(), vec.view().zn());
	}
	
	
	@Override
	public VectView mul(double x, double y)
	{
		return mul(x, y, 1);
	}
	
	
	@Override
	public VectView mul(final double x, final double y, final double z)
	{
		return new VectView() {
			
			final VectMathDynamic t = VectMathDynamic.this;
			
			
			@Override
			public double x()
			{
				return t.x() * x;
			}
			
			
			@Override
			public double y()
			{
				return t.y() * y;
			}
			
			
			@Override
			public double z()
			{
				return t.z() * z;
			}
		};
	}
	
	
	@Override
	public VectView mul(final Num d)
	{
		return mul(d, d, d);
	}
	
	
	@Override
	public VectView mul(final Num x, final Num y)
	{
		return mul(x, y, Num.ONE);
	}
	
	
	@Override
	public VectView mul(final Num x, final Num y, final Num z)
	{
		return new VectView() {
			
			final Vect t = VectMathDynamic.this;
			
			
			@Override
			public double x()
			{
				return t.x() * x.value();
			}
			
			
			@Override
			public double y()
			{
				return t.y() * y.value();
			}
			
			
			@Override
			public double z()
			{
				return t.z() * z.value();
			}
		};
	}
	
	
	@Override
	public VectView round()
	{
		return new VectView() {
			
			final VectMathDynamic t = VectMathDynamic.this;
			
			
			@Override
			public double x()
			{
				return Math.round(t.x());
			}
			
			
			@Override
			public double y()
			{
				return Math.round(t.y());
			}
			
			
			@Override
			public double z()
			{
				return Math.round(t.z());
			}
		};
	}
	
	
	@Override
	public VectView floor()
	{
		return new VectView() {
			
			final VectMathDynamic t = VectMathDynamic.this;
			
			
			@Override
			public double x()
			{
				return Math.floor(t.x());
			}
			
			
			@Override
			public double y()
			{
				return Math.floor(t.y());
			}
			
			
			@Override
			public double z()
			{
				return Math.floor(t.z());
			}
		};
	}
	
	
	@Override
	public VectView ceil()
	{
		return new VectView() {
			
			final VectMathDynamic t = VectMathDynamic.this;
			
			
			@Override
			public double x()
			{
				return Math.ceil(t.x());
			}
			
			
			@Override
			public double y()
			{
				return Math.ceil(t.y());
			}
			
			
			@Override
			public double z()
			{
				return Math.ceil(t.z());
			}
		};
	}
	
	
	@Override
	public VectView sub(Vect vec)
	{
		return sub(vec.view().xn(), vec.view().yn(), vec.view().zn());
	}
	
	
	@Override
	public VectView sub(double x, double y)
	{
		return add(-x, -y, 0);
	}
	
	
	@Override
	public VectView sub(double x, double y, double z)
	{
		return add(-x, -y, -z);
	}
	
	
	@Override
	public VectView sub(Num x, Num y)
	{
		return sub(x, y, Num.ZERO);
	}
	
	
	@Override
	public VectView sub(final Num x, final Num y, final Num z)
	{
		return new VectView() {
			
			final Vect t = VectMathDynamic.this;
			
			
			@Override
			public double x()
			{
				return t.x() - x.value();
			}
			
			
			@Override
			public double y()
			{
				return t.y() - y.value();
			}
			
			
			@Override
			public double z()
			{
				return t.z() - z.value();
			}
		};
	}
	
	
	@Override
	public VectView neg()
	{
		if (neg == null) neg = mul(-1);
		return neg;
	}
	
	
	public VectView norm(final Num size)
	{
		return new VectView() {
			
			final VectMathDynamic t = VectMathDynamic.this;
			
			
			@Override
			public double x()
			{
				final double tSize = t.size().value();
				final double nSize = size.value();
				
				if (tSize == 0 || nSize == 0) return 0;
				
				return x() / (nSize / tSize);
			}
			
			
			@Override
			public double y()
			{
				final double tSize = t.size().value();
				final double nSize = size.value();
				
				if (tSize == 0 || nSize == 0) return 0;
				
				return y() / (nSize / tSize);
			}
			
			
			@Override
			public double z()
			{
				final double tSize = t.size().value();
				final double nSize = size.value();
				
				if (tSize == 0 || nSize == 0) return 0;
				
				return z() / (nSize / tSize);
			}
		};
	}
	
	
	@Override
	public VectView norm(final double size)
	{
		return norm(NumVal.make(size));
	}
	
	
	@Override
	public NumView distTo(final Vect point)
	{
		return new NumView() {
			
			final VectMathDynamic t = VectMathDynamic.this;
			
			
			@Override
			public double value()
			{
				final double dx = t.x() - point.x();
				final double dy = t.y() - point.y();
				final double dz = t.z() - point.z();
				
				return Math.sqrt(dx * dx + dy * dy + dz * dz);
			}
		};
	}
	
	
	@Override
	public final VectView midTo(final Vect point)
	{
		return new VectView() {
			
			final VectMathDynamic t = VectMathDynamic.this;
			
			
			@Override
			public double x()
			{
				return (point.x() + t.x()) * 0.5;
			}
			
			
			@Override
			public double y()
			{
				return (point.y() + t.y()) * 0.5;
			}
			
			
			@Override
			public double z()
			{
				return (point.z() + t.z()) * 0.5;
			}
		};
	}
	
	
	@Override
	public final VectView vectTo(final Vect point)
	{
		return new VectView() {
			
			final VectMathDynamic t = VectMathDynamic.this;
			
			
			@Override
			public double x()
			{
				return (point.x() - t.x());
			}
			
			
			@Override
			public double y()
			{
				return (point.y() - t.y());
			}
			
			
			@Override
			public double z()
			{
				return (point.z() - t.z());
			}
		};
	}
	
	
	@Override
	public final VectView cross(final Vect vec)
	{
		return new VectView() {
			
			final VectMathDynamic t = VectMathDynamic.this;
			
			
			@Override
			public double x()
			{
				return t.y() * vec.z() - t.z() * vec.y();
			}
			
			
			@Override
			public double y()
			{
				return t.z() * vec.x() - t.x() * vec.z();
			}
			
			
			@Override
			public double z()
			{
				return t.x() * vec.y() - t.y() * vec.x();
			}
		};
	}
	
	
	@Override
	public NumView dot(final Vect vec)
	{
		return new NumView() {
			
			final VectMathDynamic t = VectMathDynamic.this;
			
			
			@Override
			public double value()
			{
				return t.x() * vec.x() + t.y() * vec.y() + t.z() * vec.z();
			}
		};
	}
	
	
	@Override
	public Num size()
	{
		if (size == null) size = new NumView() {
			
			final VectMathDynamic t = VectMathDynamic.this;
			
			
			@Override
			public double value()
			{
				final double x = t.x(), y = t.y(), z = t.z();
				return Math.sqrt(x * x + y * y + z * z);
			}
		};
		
		return size;
	}
}
