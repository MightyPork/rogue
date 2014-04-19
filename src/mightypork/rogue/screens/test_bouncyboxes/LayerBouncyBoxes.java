package mightypork.rogue.screens.test_bouncyboxes;


import java.util.ArrayList;
import java.util.List;

import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.layout.RowHolder;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.BaseScreen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.rogue.Res;
import mightypork.util.constraints.num.Num;
import mightypork.util.constraints.vect.Vect;
import mightypork.util.math.color.Color;


public class LayerBouncyBoxes extends ScreenLayer {
	
	List<BouncyBox> boxes = new ArrayList<>();
	private RowHolder layout;
	
	
	public LayerBouncyBoxes(BaseScreen screen)
	{
		super(screen);
		
		bindKey(new KeyStroke(true, Keys.RIGHT), new Runnable() {
			
			@Override
			public void run()
			{
				goRight();
			}
		});
		
		bindKey(new KeyStroke(true, Keys.LEFT), new Runnable() {
			
			@Override
			public void run()
			{
				goLeft();
			}
		});
		
		// shrink screen rect by 8% on all sides
		
		root.add(layout = new RowHolder(this, root.shrink(root.height().perc(5)), 10));
		
		for (int i = 0; i < 9; i++) {
			final BouncyBox bbr = new BouncyBox();
			layout.add(bbr);
			boxes.add(bbr);
		}
		
		final TextPainter tp = new TextPainter(Res.getFont("default"), AlignX.LEFT, Color.WHITE);
		tp.setText("Press left & right to move.");
		final Num shadowOffset = tp.height().div(16);
		tp.setShadow(Color.RED, Vect.make(shadowOffset, shadowOffset));
		
		layout.add(tp);
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
