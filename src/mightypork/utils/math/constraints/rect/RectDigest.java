package mightypork.utils.math.constraints.rect;


import mightypork.utils.math.constraints.vect.VectConst;


public class RectDigest {
	
	public final RectConst source;
	public final VectConst origin;
	public final VectConst size;
	
	public final double x;
	public final double y;
	public final double width;
	public final double height;
	
	public final double left;
	public final double right;
	public final double top;
	public final double bottom;
	
	
	public RectDigest(Rect rect) {
		this.source = rect.freeze();
		
		this.origin = rect.origin().freeze();
		this.size = rect.size().freeze();
		
		this.x = rect.x().value();
		this.y = rect.y().value();
		
		this.width = rect.width().value();
		this.height = rect.height().value();
		
		this.left = rect.left().value();
		this.right = rect.right().value();
		this.top = rect.top().value();
		this.bottom = rect.bottom().value();
	}
}
