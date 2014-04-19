package mightypork.rogue.screens.ingame;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.Component;
import mightypork.gamecore.gui.components.layout.HorizontalFixedFlowLayout;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.rogue.Res;
import mightypork.util.constraints.num.Num;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.math.color.PAL16;


public class GameGui extends ScreenLayer {
	
	public GameGui(Screen screen) {
		super(screen);
		
		final Num h = root.height();
		final Num w = root.width();
		final Num minWH = w.min(h).max(700); // avoid too small shrinking
		
		final Component qp = new QuadPainter(PAL16.VOID);
		qp.setRect(root);
		root.add(qp);
		
		final ImagePainter nav = new ImagePainter(Res.getTxQuad("panel"));
		nav.setRect(root.bottomEdge().growUp(minWH.perc(7)));
		root.add(nav);
		
		final HorizontalFixedFlowLayout itemSlots = new HorizontalFixedFlowLayout(root, nav.height().mul(1.8), AlignX.LEFT);
		itemSlots.setRect(nav.growUp(nav.height()).move(nav.height().mul(0.2), nav.height().mul(-0.2)));
		root.add(itemSlots);
		
		itemSlots.add(new NavItemSlot(Res.getTxQuad("meat")));
		itemSlots.add(new NavItemSlot(Res.getTxQuad("sword")));
		
		final Rect shrunk = root.shrink(minWH.perc(3));
		final Num displays_height = minWH.perc(6);
		
		final HeartBar hearts = new HeartBar(6, 3, Res.getTxQuad("heart_on"), Res.getTxQuad("heart_off"), AlignX.LEFT);
		final Rect hearts_box = shrunk.topLeft().startRect().growDown(displays_height);
		hearts.setRect(hearts_box);
		root.add(hearts);
		
		final HeartBar experience = new HeartBar(6, 2, Res.getTxQuad("xp_on"), Res.getTxQuad("xp_off"), AlignX.RIGHT);
		final Rect xp_box = shrunk.topRight().startRect().growDown(displays_height);
		experience.setRect(xp_box);
		root.add(experience);
	}
	
	
	@Override
	public int getPriority()
	{
		return 100;
	}
	
}
