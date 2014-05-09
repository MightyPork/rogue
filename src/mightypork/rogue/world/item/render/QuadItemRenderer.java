package mightypork.rogue.world.item.render;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.world.item.ItemRenderer;


public class QuadItemRenderer extends ItemRenderer {
	
	private final TxQuad txq;
	
	
	public QuadItemRenderer(TxQuad txq)
	{
		this.txq = txq;
	}
	
	
	public QuadItemRenderer(TxSheet txs)
	{
		this.txq = txs.getQuad(0);
	}
	
	
	@Override
	public void render(Rect r)
	{
		Render.quadTextured(r, txq);
	}
	
}
