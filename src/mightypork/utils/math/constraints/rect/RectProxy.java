package mightypork.utils.math.constraints.rect;


public class RectProxy extends RectAdapter {
	
	private Rect source;

	public RectProxy(Rect source) {
		this.source = source;
	}

	@Override
	protected Rect getSource()
	{
		return source;
	}
	
}
