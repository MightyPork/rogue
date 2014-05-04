package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.gui.components.VisualComponent;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;


/**
 * Draws image in given rect
 * 
 * @author MightyPork
 */
public class ImagePainter extends VisualComponent {
	
	private final TxQuad txQuad;
	private boolean aspratio = false;
	private Rect asprRect;
	
	
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
