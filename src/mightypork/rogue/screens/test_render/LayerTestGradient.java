package mightypork.rogue.screens.test_render;


import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.render.Render;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.rect.proxy.RectBound;


public class LayerTestGradient extends ScreenLayer {
	
	private final RectBound pos1;
	private final RectBound pos2;
	
	
	public LayerTestGradient(Screen screen) {
		super(screen);
		
		pos1 = bounds().topEdge().growDown(64);
		pos2 = bounds().leftEdge().growUp(-64).growRight(64);
	}
	
	
	@Override
	public void render()
	{
		Render.quadColor(getRect(), RGB.WHITE, RGB.BLUE, RGB.BLACK, RGB.PURPLE);
		Render.quadGradH(pos1.getRect(), RGB.GREEN, RGB.RED);
		Render.quadGradV(pos2.getRect(), RGB.WHITE, RGB.PURPLE);
	}
	
	
	@Override
	public int getPriority()
	{
		return 5;
	}
	
}
