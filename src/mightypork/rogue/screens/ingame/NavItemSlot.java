package mightypork.rogue.screens.ingame;


import mightypork.gamecore.gui.components.ClickableComponent;
import mightypork.gamecore.gui.components.InputComponent;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxQuad;
import mightypork.rogue.Res;
import mightypork.util.constraints.num.Num;
import mightypork.util.constraints.num.mutable.NumAnimated;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.caching.RectCache;
import mightypork.util.constraints.vect.Vect;
import mightypork.util.control.timing.Updateable;
import mightypork.util.math.Easing;
import mightypork.gamecore.control.events.MouseMotionEvent;


public class NavItemSlot extends ClickableComponent implements MouseMotionEvent.Listener, Updateable {
	
	private TxQuad image;
	private TxQuad frame;
	private RectCache paintBox;
	private NumAnimated yOffset;
	private boolean wasInside = false;
	
	
	public NavItemSlot(TxQuad image) {
		this.image = image;
		this.frame = Res.getTxQuad("item_frame");
		
		Rect ref = shrink(height().perc(8));
		yOffset = new NumAnimated(0, Easing.LINEAR);
		yOffset.setDefaultDuration(0.05);
		
		Num h = ref.width().min(ref.height());
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
