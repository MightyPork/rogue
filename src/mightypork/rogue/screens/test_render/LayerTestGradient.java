package mightypork.rogue.screens.test_render;


import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.render.Render;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.math.color.Color;


public class LayerTestGradient extends ScreenLayer {
	
	private final RectBound pos1;
	private final RectBound pos2;
	
	
	public LayerTestGradient(Screen screen) {
		super(screen);
		
		pos1 = root.topEdge().growDown(64);
		pos2 = root.leftEdge().growUp(-64).growRight(64);
	}
	
	
	@Override
	protected void renderLayer()
	{
		Render.quadColor(root, Color.WHITE, Color.BLUE, Color.BLACK, Color.MAGENTA);
		Render.quadGradH(pos1.getRect(), Color.GREEN, Color.RED);
		Render.quadGradV(pos2.getRect(), Color.WHITE, Color.MAGENTA);
	}
	
	
	@Override
	public int getPriority()
	{
		return 0;
	}
	
}
