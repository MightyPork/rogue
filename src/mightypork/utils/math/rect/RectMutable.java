package mightypork.utils.math.rect;


import mightypork.utils.math.coord.Vec;


public interface RectMutable extends Rect {
	
	/**
	 * Set to other rect's coordinates
	 * 
	 * @param rect other rect
	 */
	void setTo(Rect rect);
	
	
	void setTo(Vec origin, Vec size);
	
	
	void setTo(Vec origin, double width, double height);
	
	
	void setOrigin(Vec origin);
	
	
	void setSize(Vec size);
	
}
