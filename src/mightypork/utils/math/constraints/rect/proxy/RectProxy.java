package mightypork.utils.math.constraints.rect.proxy;

import mightypork.utils.math.constraints.rect.Rect;


public class RectProxy extends RectAdapter {
	
	private final Rect source;
	
	
	public RectProxy(Rect source) {
		this.source = source;
	}
	
	
	@Override
	protected Rect getSource()
	{
		return source;
	}
	
}
