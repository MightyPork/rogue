package mightypork.gamecore.graphics.textures;


import mightypork.utils.interfaces.Destroyable;
import mightypork.utils.math.constraints.rect.Rect;


/**
 * Texture interface, backend independent
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface ITexture extends Destroyable {
	
	/**
	 * Set filter for scaling
	 * 
	 * @param filter filter
	 */
	void setFilter(FilterMode filter);
	
	
	/**
	 * @param wrapping wrap mode
	 */
	void setWrap(WrapMode wrapping);
	
	
	/**
	 * Get a quad from this texture of given position/size
	 * 
	 * @param uvs quad rect
	 * @return the quad
	 */
	TxQuad makeQuad(Rect uvs);
	
	
	/**
	 * Get a grid for given number of tiles
	 * 
	 * @param x horizontal tile count
	 * @param y vertical tile count
	 * @return grid
	 */
	QuadGrid grid(int x, int y);
	
	
	/**
	 * @return source image width (corresponding to width01)
	 */
	int getImageWidth();
	
	
	/**
	 * @return source image height (corresponding to height01)
	 */
	int getImageHeight();
	
	
	/**
	 * @return true if the image is RGBA
	 */
	boolean hasAlpha();
}
