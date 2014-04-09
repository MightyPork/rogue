package mightypork.gamecore.render.textures;


import org.newdawn.slick.opengl.Texture;


/**
 * Texture with filter and wrap mode
 * 
 * @author MightyPork
 */
public interface FilteredTexture extends Texture {
	
	/**
	 * Set filter for scaling
	 * 
	 * @param filterMin downscale filter
	 * @param filterMag upscale filter
	 */
	void setFilter(FilterMode filterMin, FilterMode filterMag);
	
	
	/**
	 * Set filter for scaling (both up and down)
	 * 
	 * @param filter filter
	 */
	void setFilter(FilterMode filter);
	
	
	/**
	 * @param wrapping wrap mode
	 */
	void setWrap(WrapMode wrapping);
}
