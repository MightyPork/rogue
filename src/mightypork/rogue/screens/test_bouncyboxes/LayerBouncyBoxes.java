package mightypork.rogue.screens.test_bouncyboxes;


import static mightypork.utils.math.constraints.ConstraintFactory.*;

import java.util.ArrayList;
import java.util.List;

import mightypork.gamecore.gui.renderers.RowHolder;
import mightypork.gamecore.gui.renderers.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.rogue.Res;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.string.StringProvider;


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
		final RectConstraint holder_rect = c_shrink(this, c_percent(c_height(this), 4));
		
		addChildClient(layout = new RowHolder(screen, holder_rect, 11));
		
		for (int i = 0; i <= 9; i++) {
			final BouncyBox bbr = new BouncyBox();
			layout.add(bbr);
			boxes.add(bbr);
		}
		
		StringProvider sp = new StringProvider() {
			
			@Override
			public String getString()
			{
				return "Running at " + getDisplay().getFps() + " fps!";
			}
		};
		
		layout.add(new TextPainter(Res.getFont("default"), Align.LEFT, RGB.WHITE, sp));
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

	
	@Override
	public int getPriority()
	{
		return 0;
	}
}
