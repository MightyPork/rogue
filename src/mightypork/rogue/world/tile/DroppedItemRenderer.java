package mightypork.rogue.world.tile;


import java.util.Collection;

import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.utils.math.animation.Animator;
import mightypork.utils.math.animation.AnimatorBounce;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.num.proxy.NumProxy;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.proxy.RectProxy;


public class DroppedItemRenderer {

	private final Animator itemAnim = new AnimatorBounce(2, Easing.SINE_BOTH);

	// prepared constraints, to avoid re-building each frame
	private final RectProxy tileRectAdapter = new RectProxy();
	private final NumProxy offsAdapter = new NumProxy();
	private final Rect itemRect = tileRectAdapter.shrink(tileRectAdapter.height().perc(12)).moveY(offsAdapter.neg().mul(tileRectAdapter.height().mul(0.2)));


	public void render(Collection<Item> items, TileRenderContext context)
	{
		tileRectAdapter.setRect(context);
		int cnt = 0;
		for (final Item i : items) {

			offsAdapter.setNum(Num.make((itemAnim.getValue() + (cnt % 3) * 0.1)));

			i.render(itemRect);

			cnt++;
		}
	}


	public void update(double delta)
	{
		itemAnim.update(delta);
	}
}
