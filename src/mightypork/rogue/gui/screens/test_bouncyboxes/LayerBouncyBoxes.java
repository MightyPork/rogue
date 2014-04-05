package mightypork.rogue.gui.screens.test_bouncyboxes;


import static mightypork.utils.math.constraints.ConstraintFactory.*;

import java.util.ArrayList;
import java.util.List;

import mightypork.rogue.gui.Screen;
import mightypork.rogue.gui.ScreenLayer;
import mightypork.rogue.gui.constraints.RowHolder;
import mightypork.utils.math.constraints.RectConstraint;


public class LayerBouncyBoxes extends ScreenLayer {
	
	List<BouncyBox> boxes = new ArrayList<BouncyBox>();
	private RowHolder layout;
	
	
	public LayerBouncyBoxes(Screen screen) {
		super(screen);
		
		// shrink screen rect by 8% on all sides
		RectConstraint holder_rect = c_shrink(this, c_percent(c_height(this), c_n(8)));
		
		addChildClient(layout = new RowHolder(screen, holder_rect, 16));
		
		for (int i = 0; i < 16; i++) {
			BouncyBox bbr = new BouncyBox();
			layout.addRow(bbr);
			boxes.add(bbr);
		}
		
	}
	
	
	@Override
	public void render()
	{
		layout.render();
	}
	
	
	public void goLeft()
	{
		for (BouncyBox bbr : boxes) {
			bbr.goLeft();
		}
	}
	
	
	public void goRight()
	{
		for (BouncyBox bbr : boxes) {
			bbr.goRight();
		}
	}
	
}
