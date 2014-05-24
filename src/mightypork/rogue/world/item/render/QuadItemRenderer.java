package mightypork.rogue.world.item.render;


import mightypork.dynmath.rect.Rect;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemRenderer;


public class QuadItemRenderer extends ItemRenderer {
	
	private final TxQuad txq;
	
	
	public QuadItemRenderer(Item item, TxQuad txq)
	{
		super(item);
		this.txq = txq;
	}
	
	
	@Override
	public void render(Rect r)
	{
		Render.quadTextured(r, txq);
	}
	
}
