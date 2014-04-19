package mightypork.gamecore.render.textures;


import org.newdawn.slick.opengl.Texture;

import mightypork.util.constraints.rect.Rect;


/**
 * Texture with filter and wrap mode
 * 
 * @author MightyPork
 */
public interface GLTexture extends Texture {
	
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
	 * Bind without adjusting parameters
	 */
	void bindRaw();
	
	
	/**
	 * Get a grid for given number of tiles
	 * 
	 * @param x horizontal tile count
	 * @param y vertical tile count
	 * @return grid
	 */
	QuadGrid grid(int x, int y);
}
