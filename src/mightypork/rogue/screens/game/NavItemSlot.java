package mightypork.rogue.screens.game;


import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.gui.components.ClickableComponent;
import mightypork.gamecore.input.events.MouseMotionEvent;
import mightypork.gamecore.input.events.MouseMotionListener;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.rect.caching.RectCache;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.Res;


public class NavItemSlot extends ClickableComponent implements MouseMotionListener, Updateable {
	
	private final TxQuad image;
	private final TxQuad frame;
	private final RectCache paintBox;
	private final NumAnimated yOffset;
	private boolean wasInside = false;
	
	
	public NavItemSlot(TxQuad image)
	{
		this.image = image;
		this.frame = Res.getTxQuad("item_frame");
		
		final Rect ref = shrink(height().perc(8));
		yOffset = new NumAnimated(0, Easing.LINEAR);
		yOffset.setDefaultDuration(0.05);
		
		final Num h = ref.width().min(ref.height());
		this.paintBox = ref.bottomLeft().startRect().grow(Num.ZERO, h, h, Num.ZERO).moveY(yOffset.mul(h.perc(-5))).cached();
	}
	
	
	@Override
	protected void renderComponent()
	{
		Render.quadTextured(paintBox, frame);
		
		Render.pushMatrix();
		Render.translateXY(paintBox.center());
		Render.scaleXY(0.7);
		Render.rotateZ(45);
		Render.quadTextured(Rect.make(paintBox.height()).centerTo(Vect.ZERO), image);
		Render.popMatrix();
	}
	
	
	@Override
	public void updateLayout()
	{
		paintBox.poll();
	}
	
	
	@Override
	public void receive(MouseMotionEvent event)
	{
		if (event.getPos().isInside(this) != wasInside) {
			if (wasInside) {
				// left
				yOffset.fadeOut();
			} else {
				// entered
				yOffset.fadeIn();
			}
			
			wasInside = !wasInside;
		}
	}
	
	
	@Override
	public void update(double delta)
	{
		if (yOffset.isInProgress()) {
			yOffset.update(delta);
			paintBox.poll();
		}
	}
	
}
