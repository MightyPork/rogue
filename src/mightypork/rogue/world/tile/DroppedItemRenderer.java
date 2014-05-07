package mightypork.rogue.world.tile;


import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBoundAdapter;
import mightypork.gamecore.util.math.timing.Animator;
import mightypork.gamecore.util.math.timing.AnimatorBounce;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.render.TileRenderContext;


public class DroppedItemRenderer {
	
	private Animator itemAnim = new AnimatorBounce(2, Easing.SINE_BOTH);
	
	// prepared constraints, to avoid re-building each frame
	private final RectBoundAdapter tileRectAdapter = new RectBoundAdapter();
	private final Rect itemRect = tileRectAdapter
			.shrink(tileRectAdapter.height().perc(10))
			.moveY(itemAnim.neg().mul(tileRectAdapter.height().mul(0.2)));
	
	public Animator getItemAnim()
	{
		if (itemAnim == null) {
			itemAnim = new AnimatorBounce(2, Easing.SINE_BOTH);
		}
		
		return itemAnim;
	}
	
	
	public void render(Item item, TileRenderContext context)
	{
		tileRectAdapter.setRect(context);
		item.render(itemRect);
	}
	
	
	public void update(double delta)
	{
		itemAnim.update(delta);
	}
}
