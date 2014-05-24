package mightypork.rogue.world.tile;


import java.util.Collection;

import mightypork.dynmath.num.Num;
import mightypork.dynmath.num.proxy.NumBoundAdapter;
import mightypork.dynmath.rect.Rect;
import mightypork.dynmath.rect.proxy.RectBoundAdapter;
import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.timing.animation.Animator;
import mightypork.gamecore.util.math.timing.animation.AnimatorBounce;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.render.TileRenderContext;


public class DroppedItemRenderer {
	
	private final Animator itemAnim = new AnimatorBounce(2, Easing.SINE_BOTH);
	
	// prepared constraints, to avoid re-building each frame
	private final RectBoundAdapter tileRectAdapter = new RectBoundAdapter();
	private final NumBoundAdapter offsAdapter = new NumBoundAdapter();
	private final Rect itemRect = tileRectAdapter.shrink(tileRectAdapter.height().perc(12)).moveY(offsAdapter.neg().mul(tileRectAdapter.height().mul(0.2)));
	
	
	public void render(Collection<Item> items, TileRenderContext context)
	{
		tileRectAdapter.setRect(context);
		int cnt = 0;
		for (final Item i : items) {
			
			offsAdapter.setNum(Num.make((itemAnim.value() + (cnt % 3) * 0.1)));
			
			i.render(itemRect);
			
			cnt++;
		}
	}
	
	
	public void update(double delta)
	{
		itemAnim.update(delta);
	}
}
