package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.gui.components.BaseComponent;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;


/**
 * Draws image in given rect
 * 
 * @author MightyPork
 */
public class ImagePainter extends BaseComponent {
	
	private final TxQuad txQuad;
	private boolean aspratio = false;
	private final Rect asprRect;
	
	
	/**
	 * @param txQuad drawn image
	 */
	public ImagePainter(TxQuad txQuad)
	{
		this.txQuad = txQuad;
		this.asprRect = ((Rect) this).axisV().grow(height().div(txQuad.uvs.height()).mul(txQuad.uvs.width()).half(), Num.ZERO);;
	}
	
	
	public void keepAspectRatio()
	{
		aspratio = true;
	}
	
	
	@Override
	public void renderComponent()
	{
		Render.quadTextured(aspratio ? asprRect : this, txQuad);
	}
	
}
