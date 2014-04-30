package mightypork.gamecore.resources.textures;


import mightypork.gamecore.eventbus.events.Destroyable;
import mightypork.gamecore.util.math.constraints.rect.Rect;


/**
 * Texture with filter and wrap mode
 * 
 * @author MightyPork
 */
public interface GLTexture extends Destroyable {
	
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
	 * @return OpenGL texture ID
	 */
	int getTextureID();
	
	
	/**
	 * Get the height of the texture, 0..1.<br>
	 * 
	 * @return height 0..1
	 */
	float getHeight01();
	
	
	/**
	 * Get the width of the texture, 0..1.<br>
	 * 
	 * @return width 0..1
	 */
	float getWidth01();
	
	
	/**
	 * @return source image width (corresponding to width01)
	 */
	int getImageWidth();
	
	
	/**
	 * @return source image height (corresponding to height01)
	 */
	int getImageHeight();
	
	
	/**
	 * Bind to GL context, applying the filters prescribed.
	 */
	void bind();
	
	
	/**
	 * @return true if the image is RGBA
	 */
	boolean hasAlpha();
}
