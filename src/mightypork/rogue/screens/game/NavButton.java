package mightypork.rogue.screens.game;


import mightypork.gamecore.core.App;
import mightypork.gamecore.gui.components.input.ClickableComponent;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.textures.TxQuad;


/**
 * Button in the ingame nav
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class NavButton extends ClickableComponent {
	
	private final TxQuad base, hover, down, fg;
	
	
	public NavButton(TxQuad fg) {
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
			
		App.gfx().quad(this, bg);
		App.gfx().quad(this, fg);
	}
	
}
