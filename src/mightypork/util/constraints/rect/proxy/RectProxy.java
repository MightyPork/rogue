package mightypork.util.constraints.rect.proxy;


import mightypork.util.constraints.rect.Rect;


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
