package mightypork.rogue.world.item.render;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.rect.mutable.RectVar;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemRenderer;


public class QuadItemRenderer extends ItemRenderer {
	
	private final TxQuad txq;
	
	private final RectVar rrect = Rect.makeVar();
	private final Rect usesRect = rrect.topLeft().startRect().grow(Num.ZERO, rrect.width().perc(40), Num.ZERO, rrect.height().perc(8));
	private final Num hAlpha = Num.make(0.7);
	
	
	public QuadItemRenderer(Item item, TxQuad txq) {
		super(item);
		this.txq = txq;
	}
	
	
	@Override
	public void render(Rect r)
	{
		Render.quadTextured(r, txq);
		
		if (item.isDamageable()) {
			Color.pushAlpha(hAlpha);
			
			rrect.setTo(r);
			
			Render.quadColor(usesRect, RGB.BLACK);
			
			double useRatio = (item.getRemainingUses() / (double)item.getMaxUses());
			
			Color barColor = (useRatio > 0.6 ? RGB.GREEN : useRatio > 0.2 ? RGB.ORANGE : RGB.RED);
			
			Render.quadColor(usesRect.shrinkRight(usesRect.width().value() * (1 - useRatio)), barColor);
			
			Color.popAlpha();
		}
		
	}
	
}
