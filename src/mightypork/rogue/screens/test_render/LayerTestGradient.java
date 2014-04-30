package mightypork.rogue.screens.test_render;


import mightypork.gamecore.gui.screens.BaseScreen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;


public class LayerTestGradient extends ScreenLayer {
	
	private final RectBound pos1;
	private final RectBound pos2;
	
	
	public LayerTestGradient(BaseScreen screen)
	{
		super(screen);
		
		pos1 = root.topEdge().growDown(64);
		pos2 = root.leftEdge().growUp(-64).growRight(64);
	}
	
	
	@Override
	public void render()
	{
		Render.quadColor(root, Color.WHITE, Color.BLUE, Color.BLACK, Color.MAGENTA);
		Render.quadGradH(pos1.getRect(), Color.GREEN, Color.RED);
		Render.quadGradV(pos2.getRect(), Color.WHITE, Color.MAGENTA);
	}
	
	
	@Override
	public int getZIndex()
	{
		return 0;
	}
	
}
