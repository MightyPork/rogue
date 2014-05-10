package mightypork.rogue.screens.game;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.strings.StringProvider;
import mightypork.rogue.Res;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.gui.Minimap;


public class HudLayer extends ScreenLayer {
	
	private final Num playerHealthTotal = new Num() {
		
		@Override
		public double value()
		{
			return WorldProvider.get().getPlayer().getHealthMax() / 2D;
		}
	};
	
	private final Num playerHealthActive = new Num() {
		
		@Override
		public double value()
		{
			return WorldProvider.get().getPlayer().getHealth() / 2D;
		}
	};
	
	private Minimap mm;
	
	
	public HudLayer(Screen screen)
	{
		super(screen);
		
		buildNav();
		
		buildDisplays();
		
		buildMinimap();
		
		bindKey(new KeyStroke(Keys.M), new Runnable() {
			
			@Override
			public void run()
			{
				mm.setVisible(!mm.isVisible());
			}
		});
	}
	
	
	private void buildMinimap()
	{
		mm = new Minimap();
		mm.setRect(root.shrink(root.width().perc(5), root.height().perc(15)));
		root.add(mm);
	}
	
	
	private void buildDisplays()
	{
		final Num h = root.height();
		
		final Num displays_height = h.perc(6);
		
		//@formatter:off
		final HeartBar hearts = new HeartBar(
				playerHealthTotal,
				playerHealthActive,
				Res.txq("hud.heart.on"),
				Res.txq("hud.heart.half"),
				Res.txq("hud.heart.off"),
				AlignX.LEFT);
		//@formatter:on
		
		
		final Rect hearts_box = root.shrink(h.perc(3)).topLeft().startRect().growDown(displays_height);
		hearts.setRect(hearts_box);
		root.add(hearts);
		
		
		final TextPainter lvl = new TextPainter(Res.getFont("hud"), AlignX.RIGHT, RGB.WHITE, new StringProvider() {
			
			@Override
			public String getString()
			{
				return "Floor " + (1 + WorldProvider.get().getWorld().getPlayer().getLevelNumber());
			}
		});
		
		final Rect rr = root.shrink(h.perc(3)).topEdge().growDown(displays_height);
		lvl.setRect(rr.shrink(Num.ZERO, rr.height().perc(20)));
		root.add(lvl);
		
		
		/*final HeartBar experience = new HeartBar(6, 2, Res.getTxQuad("xp_on"), Res.getTxQuad("xp_off"), AlignX.RIGHT);
		final Rect xp_box = shrunk.topRight().startRect().growDown(displays_height);
		experience.setRect(xp_box);
		root.add(experience);*/
	}
	
	
	private void buildNav()
	{
		final IngameNav nav = new IngameNav(this);
		nav.setRect(root.bottomEdge().growUp(root.height().perc(12)));
		root.add(nav);
		
		// ltr
		nav.addLeft(new NavButton(Res.txq("nav.button.fg.inventory")));
		nav.addLeft(new NavButton(Res.txq("nav.button.fg.eat")));
		nav.addLeft(new NavButton(Res.txq("nav.button.fg.attack")));
		
		// rtl
		nav.addRight(new NavButton(Res.txq("nav.button.fg.options")));
		nav.addRight(new NavButton(Res.txq("nav.button.fg.help")));
	}
	
	
	@Override
	public int getZIndex()
	{
		return 100;
	}
	
	
	@Override
	public int getEventPriority()
	{
		return 400;
	}
	
}
