package mightypork.rogue.gui.screens.screenBouncy;


import static mightypork.utils.math.constraints.ConstraintFactory.*;

import java.util.ArrayList;
import java.util.List;

import mightypork.rogue.gui.constraints.ElementHolder;
import mightypork.rogue.gui.screens.Screen;
import mightypork.rogue.gui.screens.ScreenLayer;
import mightypork.utils.math.constraints.RectConstraint;


public class LayerBouncyBoxes extends ScreenLayer {
	
	List<BouncyBox> boxes = new ArrayList<BouncyBox>();
	private ElementHolder layout;
	
	
	public LayerBouncyBoxes(Screen screen) {
		super(screen);
		
		RectConstraint holder_rect = c_shrink(this, c_percent(c_height(this), c_n(8)));
		
		layout = new ElementHolder(screen, holder_rect);
		addChildClient(layout);
		
		for (int i = 0; i < 32; i++) {
			BouncyBox bbr = new BouncyBox();
			layout.add(bbr, c_row(null, 32, i));
			boxes.add(bbr);
		}
		
	}
	
	
	@Override
	public void render()
	{
		layout.render();
	}
	
	
	@Override
	public void update(double delta)
	{
		// no impl
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