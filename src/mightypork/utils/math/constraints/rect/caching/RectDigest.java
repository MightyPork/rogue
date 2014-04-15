package mightypork.utils.math.constraints.rect.caching;

import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.RectConst;


public class RectDigest {
	
	public final double x;
	public final double y;
	public final double width;
	public final double height;
	
	public final double left;
	public final double right;
	public final double top;
	public final double bottom;
	
	
	public RectDigest(Rect rect) {
		
		final RectConst frozen = rect.freeze();
		
		this.x = frozen.x().value();
		this.y = frozen.y().value();
		
		this.width = frozen.width().value();
		this.height = frozen.height().value();
		
		this.left = frozen.left().value();
		this.right = frozen.right().value();
		this.top = frozen.top().value();
		this.bottom = frozen.bottom().value();
	}
	
	
	@Override
	public String toString()
	{
		return String
				.format("Rect{ at: (%.1f, %.1f), size: (%.1f, %.1f), bounds: L %.1f R %.1f T %.1f B %.1f }", x, y, width, height, left, right, top, bottom);
	}
}
