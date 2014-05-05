package mightypork.rogue.screens.game;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.layout.HorizontalFixedFlowLayout;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.Res;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.gui.Minimap;


public class HudLayer extends ScreenLayer {
	
	private Num playerHealthTotal = new Num() {
		
		@Override
		public double value()
		{
			return WorldProvider.get().getPlayerEntity().health.getMaxHealth() / 2D;
		}
	};
	
	private Num playerHealthActive = new Num() {
		
		@Override
		public double value()
		{
			return WorldProvider.get().getPlayerEntity().health.getHealth() / 2D;
		}
	};
	
	
	public HudLayer(Screen screen)
	{
		super(screen);
		
		final Num h = root.height();
		final Num w = root.width();
		final Num minWH = w.min(h).max(700); // avoid too small shrinking
		
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
		
		//@formatter:off
		final HeartBar hearts = new HeartBar(
				playerHealthTotal,
				playerHealthActive,
				Res.getTxQuad("heart_on"),
				Res.getTxQuad("heart_half"),
				Res.getTxQuad("heart_off"),
				AlignX.LEFT);
		//@formatter:on
		
		
		final Rect hearts_box = shrunk.topLeft().startRect().growDown(displays_height);
		hearts.setRect(hearts_box);
		root.add(hearts);
		
		/*final HeartBar experience = new HeartBar(6, 2, Res.getTxQuad("xp_on"), Res.getTxQuad("xp_off"), AlignX.RIGHT);
		final Rect xp_box = shrunk.topRight().startRect().growDown(displays_height);
		experience.setRect(xp_box);
		root.add(experience);*/
		
		
		final Minimap mm = new Minimap();
		mm.setRect(root.shrink(root.width().perc(5), root.height().perc(15)));
		root.add(mm);
		
		bindKey(new KeyStroke(Keys.M), new Runnable() {
			
			@Override
			public void run()
			{
				mm.setVisible(!mm.isVisible());
			}
		});
	}
	
	
	@Override
	public int getZIndex()
	{
		return 100;
	}
	
	
	@Override
	public void render()
	{
		
		super.render();
	}
	
	
	@Override
	public int getEventPriority()
	{
		return 400;
	}
	
}
