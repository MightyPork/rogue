package mightypork.rogue.screens.game;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.LayoutComponent;
import mightypork.gamecore.gui.components.layout.HorizontalFixedFlowLayout;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;
import mightypork.rogue.Res;


public class IngameNav extends LayoutComponent {
	
	private final HorizontalFixedFlowLayout leftFlow;
	private final HorizontalFixedFlowLayout rightFlow;
	private final Rect paintHelper;
	
	private final TxQuad bg;
	
	
	public IngameNav(AppAccess app)
	{
		this(app, null);
	}
	
	
	public IngameNav(AppAccess app, RectBound context)
	{
		super(app, context);
		
		final Rect shr = this.shrink(height().perc(5));
		leftFlow = new HorizontalFixedFlowLayout(app, context, shr.height(), AlignX.LEFT);
		rightFlow = new HorizontalFixedFlowLayout(app, context, shr.height(), AlignX.RIGHT);
		
		
		leftFlow.setRect(shr);
		rightFlow.setRect(shr);
		attach(leftFlow);
		attach(rightFlow);
		
		paintHelper = leftEdge().growRight(height().mul(4));
		
		bg = Res.txq("nav.bg");
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
			Render.quadTextured(paintHelper.moveX(paintHelper.width().value() * i), bg);
		}
		
		super.renderComponent();
	}
	
	
}