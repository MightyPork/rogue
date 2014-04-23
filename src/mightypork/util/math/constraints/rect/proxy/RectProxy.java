package mightypork.util.math.constraints.rect.proxy;


import mightypork.util.math.constraints.rect.Rect;


public class RectProxy extends RectAdapter {
	
	private final Rect source;
	
	
	public RectProxy(Rect source)
	{
		this.source = source;
	}
	
	
	@Override
	protected Rect getSource()
	{
		return source;
	}
	
}
