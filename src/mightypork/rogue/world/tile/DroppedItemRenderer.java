package mightypork.rogue.world.tile;


import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.map.TileRenderContext;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.proxy.RectBoundAdapter;
import mightypork.util.control.timing.Animator;
import mightypork.util.control.timing.AnimatorBounce;
import mightypork.util.control.timing.Updateable;
import mightypork.util.math.Easing;


public class DroppedItemRenderer {
	
	private Animator itemAnim = new AnimatorBounce(2, Easing.SINE_BOTH);
	
	// prepared constraints, to avoid re-building each frame
	private final RectBoundAdapter tileRectAdapter = new RectBoundAdapter();
	private final Rect itemRect = tileRectAdapter.shrink(tileRectAdapter.height().perc(10)).moveY(itemAnim.neg());
	
	
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
