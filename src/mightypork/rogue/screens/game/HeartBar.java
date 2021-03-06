package mightypork.rogue.screens.game;


import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.textures.TxQuad;
import mightypork.gamecore.gui.components.BaseComponent;
import mightypork.utils.math.AlignX;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.num.var.NumVar;
import mightypork.utils.math.constraints.rect.Rect;


public class HeartBar extends BaseComponent {
	
	private final TxQuad img_on;
	private final TxQuad img_off;
	private final TxQuad img_half;
	private final Num total;
	private final Num active;
	
	private final NumVar index = new NumVar(0);
	private final Rect heart;
	
	
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
			default:
				heart = null; // impossible
		}
		
	}
	
	
	@Override
	protected void renderComponent()
	{
		for (int i = 0; i < total.value(); i++) {
			index.setTo(i);
			
			final double rem = active.value() - i;
			App.gfx().quad(heart, (rem > 0.6 ? img_on : rem > 0.25 ? img_half : img_off));
		}
	}
	
}
