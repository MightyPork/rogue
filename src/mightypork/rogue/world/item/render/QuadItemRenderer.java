package mightypork.rogue.world.item.render;


import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.textures.TxQuad;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.utils.math.constraints.rect.Rect;


public class QuadItemRenderer extends ItemRenderer {
	
	private final TxQuad txq;
	
	
	public QuadItemRenderer(Item item, TxQuad txq) {
		super(item);
		this.txq = txq;
	}
	
	
	@Override
	public void render(Rect r)
	{
		App.gfx().quad(r, txq);
	}
	
}
