package mightypork.rogue.screens.test_bouncyboxes;


import static mightypork.gamecore.gui.constraints.Constraints.*;

import java.util.ArrayList;
import java.util.List;

import mightypork.gamecore.gui.components.layout.RowHolder;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.constraints.RectConstraint;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.rogue.Res;
import mightypork.utils.math.color.RGB;
import mightypork.utils.string.StringProvider;


public class LayerBouncyBoxes extends ScreenLayer {
	
	List<BouncyBox> boxes = new ArrayList<>();
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
		final RectConstraint holder_rect = _shrink(this, _percent(_height(this), 4));
		
		addChildClient(layout = new RowHolder(screen, holder_rect, 11));
		
		for (int i = 0; i <= 9; i++) {
			final BouncyBox bbr = new BouncyBox();
			layout.add(bbr);
			boxes.add(bbr);
		}
		
		layout.add(new TextPainter(Res.getFont("default"), Align.LEFT, RGB.WHITE, new StringProvider() {
			
			@Override
			public String getString()
			{
				return "Running at " + getDisplay().getFps() + " fps!";
			}
		}));
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
