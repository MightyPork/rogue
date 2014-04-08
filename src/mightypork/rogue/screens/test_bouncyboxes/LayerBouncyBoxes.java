package mightypork.rogue.screens.test_bouncyboxes;


import static mightypork.utils.math.constraints.ConstraintFactory.*;

import java.util.ArrayList;
import java.util.List;

import mightypork.gamecore.gui.renderers.RowHolder;
import mightypork.gamecore.gui.renderers.TextRenderer;
import mightypork.gamecore.gui.renderers.TextRenderer.Align;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.rogue.Res;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.RectConstraint;


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
		final RectConstraint holder_rect = c_shrink(this, c_percent(c_height(this), c_n(8)));
		
		addChildClient(layout = new RowHolder(screen, holder_rect, 8));
		
		for (int i = 0; i < 7; i++) {
			final BouncyBox bbr = new BouncyBox();
			layout.add(bbr);
			boxes.add(bbr);
		}
		
		layout.add(new TextRenderer(Res.getFont("default"), RGB.WHITE, Align.LEFT) {
			@Override
			public String getText()
			{
				return "Running at " + getDisplay().getFps() + " fps!";
			}
		});
		
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
