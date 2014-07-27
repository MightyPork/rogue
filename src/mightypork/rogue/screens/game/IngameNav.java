package mightypork.rogue.screens.game;


import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.textures.TxQuad;
import mightypork.gamecore.gui.components.LayoutComponent;
import mightypork.gamecore.gui.components.layout.FlowColumnLayout;
import mightypork.gamecore.resources.Res;
import mightypork.utils.math.AlignX;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.RectBound;


public class IngameNav extends LayoutComponent {
	
	private final FlowColumnLayout leftFlow;
	private final FlowColumnLayout rightFlow;
	private final Rect paintHelper;
	
	private final TxQuad bg;
	
	
	public IngameNav() {
		this(null);
	}
	
	
	public IngameNav(RectBound context) {
		super(context);
		
		final Rect shr = this.shrink(height().perc(5));
		leftFlow = new FlowColumnLayout(context, shr.height(), AlignX.LEFT);
		rightFlow = new FlowColumnLayout(context, shr.height(), AlignX.RIGHT);
		
		leftFlow.setRect(shr);
		rightFlow.setRect(shr);
		attach(leftFlow);
		attach(rightFlow);
		
		paintHelper = leftEdge().growRight(height().mul(4));
		
		bg = Res.getTxQuad("nav.bg");
	}
	
	
	public void addLeft(NavButton comp)
	{
		leftFlow.add(comp);
	}
	
	
	public void addRight(NavButton comp)
	{
		rightFlow.add(comp);
	}
	
	
	@Override
	public void renderComponent()
	{
		// draw BG (manually repeat)
		for (int i = 0; i < Math.ceil(width().value() / paintHelper.width().value()); i++) {
			App.gfx().quad(paintHelper.moveX(paintHelper.width().value() * i), bg);
		}
		
		super.renderComponent();
	}
	
}
