package mightypork.rogue.screens.game;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.VisualComponent;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.num.mutable.NumVar;
import mightypork.gamecore.util.math.constraints.rect.Rect;


public class HeartBar extends VisualComponent {
	
	private final TxQuad img_on;
	private final TxQuad img_off;
	private final TxQuad img_half;
	private Num total;
	private Num active;
	
	NumVar index = new NumVar(0);
	Rect heart;
	

	/**
	 * @param total
	 * @param active
	 * @param img_on
	 * @param img_half
	 * @param img_off
	 * @param align
	 */
	public HeartBar(Num total, Num active, TxQuad img_on, TxQuad img_half, TxQuad img_off, AlignX align)
	{
		super();
		this.total = total;
		this.active = active;
		this.img_on = img_on;
		this.img_off = img_off;
		this.img_half = img_half;
		
		final Num h = height();
		final Num w = width();
		
		switch (align) {
			case LEFT:
				heart = leftEdge().growRight(h).moveX(index.mul(h));
				break;
			case RIGHT:
				heart = rightEdge().growLeft(h).moveX(h.mul(Num.ONE.sub(total)).add(index.mul(h)));
				break;
			case CENTER:
				heart = leftEdge().moveX(w.half().add(h.mul(total.half().neg()))).growRight(h).moveX(index.mul(h));
				break;
		}
		
	}
	
	
	@Override
	protected void renderComponent()
	{
		for (int i = 0; i < total.value(); i++) {
			index.setTo(i);
			
			double rem = active.value()-i;
			
			Render.quadTextured(heart, (rem>0.6 ? img_on : rem>0.25 ? img_half: img_off));
		}
	}
	
}
