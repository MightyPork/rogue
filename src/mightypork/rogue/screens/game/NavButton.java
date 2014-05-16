package mightypork.rogue.screens.game;


import mightypork.gamecore.gui.components.ClickableComponent;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.rogue.Res;


/**
 * Button in the ingame nav
 * 
 * @author MightyPork
 */
public class NavButton extends ClickableComponent {
	
	private final TxQuad base, hover, down, fg;
	
	
	public NavButton(TxQuad fg)
	{
		super();
		this.base = Res.getTxQuad("nav.button.bg.base");
		this.hover = Res.getTxQuad("nav.button.bg.hover");
		this.down = Res.getTxQuad("nav.button.bg.down");
		this.fg = fg;
	}
	
	
	@Override
	protected void renderComponent()
	{
		TxQuad bg;
		
		if (btnDownOver) {
			bg = down;
		} else if (isMouseOver()) {
			bg = hover;
		} else {
			bg = base;
		}
		
		if (!isEnabled()) bg = base; // override effects
		
		Render.quadTextured(this, bg);
		Render.quadTextured(this, fg);
	}
	
}
