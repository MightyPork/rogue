package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.gui.components.SimplePainter;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxQuad;


/**
 * Draws image in given rect
 * 
 * @author MightyPork
 */
public class ImagePainter extends SimplePainter {
	
	private TxQuad texture;
	
	
	/**
	 * @param texture drawn image
	 */
	public ImagePainter(TxQuad texture) {
		this.texture = texture;
	}
	
	
	/**
	 * @param texture texture to use
	 */
	public void setTexture(TxQuad texture)
	{
		this.texture = texture;
	}
	
	
	@Override
	public void render()
	{
		Render.quadTextured(getRect(), texture);
	}
	
}
