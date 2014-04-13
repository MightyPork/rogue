package mightypork.utils.math.rect;


import mightypork.utils.math.num.Num;
import mightypork.utils.math.num.NumView;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectView;


public abstract class RectMathDynamic extends RectMath<RectView> {
	
	@Override
	public abstract VectView origin();
	
	
	@Override
	public abstract VectView size();
	
	
	@Override
	public RectView move(final Vect move)
	{
		return new RectView() {
			
			private RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.p_size;
			}
			
			
			@Override
			public VectView origin()
			{
				return t.p_origin.add(move);
			}
			
		};
	}
	
	
	@Override
	public RectView move(final double xd, final double yd)
	{
		return new RectView() {
			
			private RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.p_size;
			}
			
			
			@Override
			public VectView origin()
			{
				return t.p_origin.add(xd, yd);
			}
			
		};
	}
	
	
	public RectView move(final Num xd, final Num yd)
	{
		return new RectView() {
			
			private RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.p_size;
			}
			
			
			@Override
			public VectView origin()
			{
				return t.p_origin.add(xd, yd);
			}
			
		};
	}
	
	
	@Override
	public RectView shrink(final double leftd, final double rightd, final double topd, final double bottomd)
	{
		return new RectView() {
			
			private RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.p_size.sub(leftd + rightd, topd + bottomd);
			}
			
			
			@Override
			public VectView origin()
			{
				return t.p_origin.add(leftd, topd);
			}
			
		};
	}
	
	
	@Override
	public RectView grow(final double leftd, final double rightd, final double topd, final double bottomd)
	{
		return new RectView() {
			
			private RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.p_size.add(leftd + rightd, topd + bottomd);
			}
			
			
			@Override
			public VectView origin()
			{
				return t.p_origin.sub(leftd, topd);
			}
			
		};
	}
	
	
	public RectView shrink(final Num leftd, final Num rightd, final Num topd, final Num bottomd)
	{
		return new RectView() {
			
			private RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.p_size.sub(leftd.view().add(rightd), topd.view().add(bottomd));
			}
			
			
			@Override
			public VectView origin()
			{
				return t.p_origin.add(leftd, topd);
			}
			
		};
	}
	
	
	public RectView grow(final Num leftd, final Num rightd, final Num topd, final Num bottomd)
	{
		
		return new RectView() {
			
			private RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.p_size.add(leftd.view().add(rightd), topd.view().add(bottomd));
			}
			
			
			@Override
			public VectView origin()
			{
				return t.p_origin.sub(leftd, topd);
			}
			
		};
	}
	
	
	@Override
	public RectView round()
	{
		
		return new RectView() {
			
			private RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.p_size.round();
			}
			
			
			@Override
			public VectView origin()
			{
				return t.p_origin.round();
			}
			
		};
	}
	
	
	@Override
	public NumView x()
	{
		return p_x;
	}
	
	
	@Override
	public NumView y()
	{
		return p_y;
	}
	
	
	@Override
	public NumView width()
	{
		return p_width;
	}
	
	
	@Override
	public NumView height()
	{
		return p_height;
	}
	
	
	@Override
	public NumView left()
	{
		return p_left;
	}
	
	
	@Override
	public NumView right()
	{
		return p_right;
	}
	
	
	@Override
	public NumView top()
	{
		return p_top;
	}
	
	
	@Override
	public NumView bottom()
	{
		return p_bottom;
	}
	
	
	@Override
	public VectView topLeft()
	{
		return p_tl;
	}
	
	
	@Override
	public VectView topCenter()
	{
		return p_tc;
	}
	
	
	@Override
	public VectView topRight()
	{
		return p_tr;
	}
	
	
	@Override
	public VectView centerLeft()
	{
		return p_cl;
	}
	
	
	@Override
	public VectView center()
	{
		return p_cc;
	}
	
	
	@Override
	public VectView centerRight()
	{
		return p_cr;
	}
	
	
	@Override
	public VectView bottomLeft()
	{
		return p_bl;
	}
	
	
	@Override
	public VectView bottomCenter()
	{
		return p_bc;
	}
	
	
	@Override
	public VectView bottomRight()
	{
		return p_br;
	}
	
	
	@Override
	public RectView centerTo(final Vect point)
	{
		return new RectView() {
			
			RectMathDynamic t = RectMathDynamic.this;
			
			
			@Override
			public VectView size()
			{
				return t.p_size;
			}
			
			
			@Override
			public VectView origin()
			{
				return point.view().sub(t.p_size.half());
			}
		};
	}
}
