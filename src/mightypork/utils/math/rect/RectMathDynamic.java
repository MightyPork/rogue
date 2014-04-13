package mightypork.utils.math.rect;


import mightypork.utils.math.num.Num;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectView;


abstract class RectMathDynamic extends RectMath<RectView> {
	
	@Override
	public abstract VectView origin();
	
	
	@Override
	public abstract VectView size();
	
	
	@Override
	public RectView move(final Vect move)
	{
		return new RectView() {
			
			private final RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.size();
			}
			
			
			@Override
			public VectView origin()
			{
				return t.origin().add(move);
			}
			
		};
	}
	
	
	@Override
	public RectView move(final double x, final double y)
	{
		return new RectView() {
			
			private final RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.size();
			}
			
			
			@Override
			public VectView origin()
			{
				return t.origin().add(x, y);
			}
			
		};
	}
	
	
	public RectView move(final Num x, final Num y)
	{
		return new RectView() {
			
			private final RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.size();
			}
			
			
			@Override
			public VectView origin()
			{
				return t.origin().add(x, y);
			}
			
		};
	}
	
	
	@Override
	public RectView shrink(final double left, final double right, final double top, final double bottom)
	{
		return new RectView() {
			
			private final RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.size().sub(left + right, top + bottom);
			}
			
			
			@Override
			public VectView origin()
			{
				return t.origin().add(left, top);
			}
			
		};
	}
	
	
	@Override
	public RectView grow(final double left, final double right, final double top, final double bottom)
	{
		return new RectView() {
			
			private final RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.size().add(left + right, top + bottom);
			}
			
			
			@Override
			public VectView origin()
			{
				return t.origin().sub(left, top);
			}
			
		};
	}
	
	
	public RectView shrink(final Num left, final Num right, final Num top, final Num bottom)
	{
		return new RectView() {
			
			private final RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.size().sub(left.view().add(right), top.view().add(bottom));
			}
			
			
			@Override
			public VectView origin()
			{
				return t.origin().add(left, top);
			}
			
		};
	}
	
	
	public RectView grow(final Num left, final Num right, final Num top, final Num bottom)
	{
		
		return new RectView() {
			
			private final RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.size().add(left.view().add(right), top.view().add(bottom));
			}
			
			
			@Override
			public VectView origin()
			{
				return t.origin().sub(left, top);
			}
			
		};
	}
	
	
	@Override
	public RectView round()
	{
		
		return new RectView() {
			
			private final RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.size().round();
			}
			
			
			@Override
			public VectView origin()
			{
				return t.origin().round();
			}
			
		};
	}
	
	
	@Override
	public Num x()
	{
		return origin().xn();
	}
	
	
	@Override
	public Num y()
	{
		return origin().yn();
	}
	
	
	@Override
	public Num width()
	{
		return size().xn();
	}
	
	
	@Override
	public Num height()
	{
		return size().yn();
	}
	
	
	@Override
	public Num left()
	{
		return origin().yn();
	}
	
	
	@Override
	public Num right()
	{
		return origin().xn().add(size().xn());
	}
	
	
	@Override
	public Num top()
	{
		return origin().yn();
	}
	
	
	@Override
	public Num bottom()
	{
		return origin().yn().add(size().yn());
	}
	
	
	@Override
	public VectView topLeft()
	{
		return origin();
	}
	
	
	@Override
	public VectView topCenter()
	{
		return origin().add(size().xn().half(), Num.ZERO);
	}
	
	
	@Override
	public VectView topRight()
	{
		return origin().add(size().xn(), Num.ZERO);
	}
	
	
	@Override
	public VectView centerLeft()
	{
		return origin().add(Num.ZERO, size().yn().half());
	}
	
	
	@Override
	public VectView center()
	{
		return origin().add(size().half());
	}
	
	
	@Override
	public VectView centerRight()
	{
		return origin().add(size().xn(), size().yn().half());
	}
	
	
	@Override
	public VectView bottomLeft()
	{
		return origin().add(Num.ZERO, size().yn());
	}
	
	
	@Override
	public VectView bottomCenter()
	{
		return origin().add(size().xn().half(), size().yn());
	}
	
	
	@Override
	public VectView bottomRight()
	{
		return origin().add(size().xn(), size().yn());
	}
	
	
	@Override
	public RectView centerTo(final Vect point)
	{
		return new RectView() {
			
			RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.size();
			}
			
			
			@Override
			public VectView origin()
			{
				return point.view().sub(t.size().half());
			}
		};
	}
}
