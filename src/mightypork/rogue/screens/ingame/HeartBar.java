package mightypork.rogue.screens.ingame;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.VisualComponent;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxQuad;
import mightypork.util.constraints.num.Num;
import mightypork.util.constraints.num.mutable.NumVar;
import mightypork.util.constraints.rect.Rect;


public class HeartBar extends VisualComponent {
	
	private TxQuad img_on;
	private TxQuad img_off;
	private int total;
	private int active;
	
	NumVar index = new NumVar(0);
	Rect heart;
	
	
	/**
	 * @param total
	 * @param active
	 * @param img_on
	 * @param img_off
	 * @param align 
	 */
	public HeartBar(int total, int active, TxQuad img_on, TxQuad img_off, AlignX align) {
		super();
		this.total = total;
		this.active = active;
		this.img_on = img_on;
		this.img_off = img_off;
		
		Num h = height();
		Num w = width();
		
		switch (align) {
			case LEFT:
				heart = leftEdge().growRight(h).moveX(index.mul(h));
				break;
			case RIGHT:
				heart = rightEdge().growLeft(h).moveX(h.mul(-total+1).add(index.mul(h)));
				break;
			case CENTER:
				heart = leftEdge().moveX(w.half().add(h.mul(-total/2D))).growRight(h).moveX(index.mul(h));
				break;
		}
		
	}
	
	
	@Override
	protected void renderComponent()
	{
		for (int i = 0; i < total; i++) {
			index.setTo(i);
			
			Render.quadTextured(heart, (i < active ? img_on : img_off));
		}
	}
	
}
