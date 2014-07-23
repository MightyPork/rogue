package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.core.modules.App;
import mightypork.gamecore.gui.components.BaseComponent;
import mightypork.gamecore.gui.components.DynamicWidthComponent;
import mightypork.gamecore.resources.textures.TxQuad;


/**
 * Draws image in given rect
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class ImagePainter extends BaseComponent implements DynamicWidthComponent {
	
	private TxQuad txQuad;
	
	
	/**
	 * @param txQuad drawn image
	 */
	public ImagePainter(TxQuad txQuad) {
		this.txQuad = txQuad;
	}
	
	
	@Override
	public void renderComponent()
	{
		App.gfx().quad(this, txQuad);
	}
	
	
	@Override
	public double computeWidth(double height)
	{
		return (height / txQuad.uvs.height().value()) * txQuad.uvs.width().value();
	}
	
	
	public void setTxQuad(TxQuad txQuad)
	{
		this.txQuad = txQuad;
	}
}
