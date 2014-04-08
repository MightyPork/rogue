package mightypork.rogue.gui.screens.test_bouncyboxes;


import static mightypork.utils.math.constraints.ConstraintFactory.*;

import java.util.ArrayList;
import java.util.List;

import mightypork.rogue.Res;
import mightypork.rogue.gui.renderers.RowHolder;
import mightypork.rogue.gui.renderers.TextRenderer;
import mightypork.rogue.gui.renderers.TextRenderer.Align;
import mightypork.rogue.gui.screens.Screen;
import mightypork.rogue.gui.screens.ScreenLayer;
import mightypork.rogue.input.KeyStroke;
import mightypork.rogue.input.Keys;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.RectEvaluable;


public class LayerBouncyBoxes extends ScreenLayer {
	
	List<BouncyBox> boxes = new ArrayList<BouncyBox>();
	private RowHolder layout;
	
	
	public LayerBouncyBoxes(Screen screen) {
		super(screen);
		
		bindKeyStroke(new KeyStroke(true, Keys.KEY_RIGHT), new Runnable() {
			
			@Override
			public void run()
			{
				goRight();
			}
		});
		
		bindKeyStroke(new KeyStroke(true, Keys.KEY_LEFT), new Runnable() {
			
			@Override
			public void run()
			{
				goLeft();
			}
		});
		
		// shrink screen rect by 8% on all sides
		final RectEvaluable holder_rect = c_shrink(this, c_percent(c_height(this), c_n(8)));
		
		addChildClient(layout = new RowHolder(screen, holder_rect, 8));
		
		for (int i = 0; i < 7; i++) {
			final BouncyBox bbr = new BouncyBox();
			layout.add(bbr);
			boxes.add(bbr);
		}
		
		layout.add(new TextRenderer(Res.getFont("default"), "This is a text,  yo!", RGB.WHITE, Align.LEFT));
		
	}
	
	
	@Override
	public void render()
	{
		layout.render();
	}
	
	
	public void goLeft()
	{
		for (final BouncyBox bbr : boxes) {
			bbr.goLeft();
		}
	}
	
	
	public void goRight()
	{
		for (final BouncyBox bbr : boxes) {
			bbr.goRight();
		}
	}
	
}
