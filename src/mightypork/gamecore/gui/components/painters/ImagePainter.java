package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.gui.components.VisualComponent;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;


/**
 * Draws image in given rect
 * 
 * @author MightyPork
 */
public class ImagePainter extends VisualComponent {
	
	private TxQuad texture;
	
	
	/**
	 * @param texture drawn image
	 */
	public ImagePainter(TxQuad texture)
	{
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
	public void renderComponent()
	{
		Render.quadTextured(getRect(), texture);
	}
	
}
