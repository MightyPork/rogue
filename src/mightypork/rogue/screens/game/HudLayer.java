package mightypork.rogue.screens.game;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.strings.StringProvider;
import mightypork.rogue.Res;
import mightypork.rogue.world.World;
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
	
	protected Minimap mm;
	
	private final ScreenGame gameScreen;
	
	
	public HudLayer(ScreenGame screen)
	{
		super(screen);
		this.gameScreen = screen;
		
		buildNav();
		
		buildDisplays();
		
		buildMinimap();
		
		buildConsole();
	}
	
	
	private void buildConsole()
	{
		final Num rh = root.height();
		final Num rw = root.width();
		final Rect consoleRect = root.shrink(rw.perc(2), Num.ZERO, rh.perc(6), rh.perc(16));
		
		final Num perRow = consoleRect.height().div(20).max(12).min(32);
		
		final WorldConsoleRenderer wcr = new WorldConsoleRenderer(perRow);
		wcr.setRect(consoleRect);
		root.add(wcr);
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
		
		//@formatter:off
		final HeartBar hearts = new HeartBar(
				playerHealthTotal,
				playerHealthActive,
				Res.txq("hud.heart.on"),
				Res.txq("hud.heart.half"),
				Res.txq("hud.heart.off"),
				AlignX.LEFT);
		//@formatter:on
		
		final Rect hearts_box = root.shrink(h.perc(3)).topEdge().growDown(h.perc(6));
		hearts.setRect(hearts_box);
		root.add(hearts);
		
		final TextPainter levelText = new TextPainter(Res.getFont("tiny"), AlignX.RIGHT, RGB.WHITE, new StringProvider() {
			
			@Override
			public String getString()
			{
				final World w = WorldProvider.get().getWorld();
				return (w.isPaused() ? "[P] " : "") + "Floor " + (1 + w.getPlayer().getLevelNumber());
			}
		});
		
		levelText.setRect(hearts_box.moveY(hearts_box.height().mul(1 / 7D)));
		root.add(levelText);
	}
	
	
	private void buildNav()
	{
		final IngameNav nav = new IngameNav(this);
		nav.setRect(root.bottomEdge().growUp(root.height().perc(12)));
		root.add(nav);
		
		NavButton btn;
		
		nav.addRight(btn = new NavButton(Res.txq("nav.button.fg.inventory")));
		btn.setAction(gameScreen.actionToggleInv);
		
		nav.addRight(btn = new NavButton(Res.txq("nav.button.fg.eat")));
		btn.setAction(gameScreen.actionEat);
		
		nav.addRight(btn = new NavButton(Res.txq("nav.button.fg.pause")));
		btn.setAction(gameScreen.actionTogglePause);
		
		
		// TODO actions
		nav.addLeft(new NavButton(Res.txq("nav.button.fg.options")));
		nav.addLeft(new NavButton(Res.txq("nav.button.fg.help")));
		
		nav.addLeft(btn = new NavButton(Res.txq("nav.button.fg.map")));
		btn.setAction(gameScreen.actionToggleMinimap);
		
		nav.addLeft(btn = new NavButton(Res.txq("nav.button.fg.magnify")));
		btn.setAction(gameScreen.actionToggleZoom);
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
