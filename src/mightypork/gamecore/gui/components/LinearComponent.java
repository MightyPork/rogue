package mightypork.gamecore.gui.components;


import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.RectBound;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.proxy.VectAdapter;


public abstract class LinearComponent extends BaseComponent implements DynamicWidthComponent {
	
	private final Rect rect = new Rect() {
		
		@Override
		public Vect size()
		{
			return new Vect() {
				
				@Override
				public double x()
				{
					return computeWidth(y());
				}
				
				
				@Override
				public double y()
				{
					return height.value();
				}
				
			};
		}
		
		
		@Override
		public Vect origin()
		{
			return new VectAdapter() {
				
				@Override
				protected Vect getSource()
				{
					return origin;
				}
			};
		}
	};
	
	private Vect origin;
	private Num height;
	
	
	public LinearComponent()
	{
		super.setRect(rect);
	}
	
	
	@Override
	public void setRect(RectBound rect)
	{
		throw new RuntimeException("Cannot assign a rect to a linear component. Set origin and height instead.");
	}
	
	
	public void setHeight(Num height)
	{
		this.height = height;
	}
	
	
	public void setOrigin(Vect origin)
	{
		this.origin = origin;
	}
}
