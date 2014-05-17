package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.gui.components.BaseComponent;
import mightypork.gamecore.gui.components.DynamicWidthComponent;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;


/**
 * Draws image in given rect
 * 
 * @author MightyPork
 */
public class ImagePainter extends BaseComponent implements DynamicWidthComponent {
	
	private final TxQuad txQuad;
	
	
	/**
	 * @param txQuad drawn image
	 */
	public ImagePainter(TxQuad txQuad)
	{
		this.txQuad = txQuad;
	}
	
	
	@Override
	public void renderComponent()
	{
		Render.quadTextured(this, txQuad);
	}
	
	
	@Override
	public double computeWidth(double height)
	{
		return (height / txQuad.uvs.height().value()) * txQuad.uvs.width().value();
	}
}
