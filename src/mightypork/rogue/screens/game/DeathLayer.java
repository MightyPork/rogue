package mightypork.rogue.screens.game;


import java.io.IOException;

import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.input.TextButton;
import mightypork.gamecore.gui.components.layout.RowLayout;
import mightypork.gamecore.gui.components.layout.linear.LinearGap;
import mightypork.gamecore.gui.components.layout.linear.LinearLayout;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.rogue.GameStateManager.GameState;
import mightypork.rogue.Res;
import mightypork.rogue.events.GameStateRequest;
import mightypork.rogue.screens.game.ScreenGame.GScrState;
import mightypork.rogue.world.WorldProvider;


public class DeathLayer extends ScreenLayer {
	
	public DeathLayer(final ScreenGame screen)
	{
		super(screen);
		
		// darker down to cover console.
		final QuadPainter qp = new QuadPainter(RGB.BLACK_80);
		qp.setRect(root);
		root.add(qp);
		
		final GLFont thick_font = Res.getFont("thick");
		
		final RowLayout rl = new RowLayout(root, 5);
		rl.setRect(root.shrink(Num.ZERO, root.height().perc(20)));
		root.add(rl);
		
		final TextPainter txp = new TextPainter(thick_font, AlignX.CENTER, RGB.YELLOW, "You're dead!");
		rl.add(txp, 1);
		txp.setVPaddingPercent(15);
		
		LinearLayout linl = new LinearLayout(root, AlignX.CENTER);
		linl.add(new ImagePainter(Res.getTxQuad("death")));
		rl.add(linl, 3);
		
		linl = new LinearLayout(root, AlignX.CENTER);
		rl.add(linl);
		
		
		final TextButton btn1 = new TextButton(thick_font, "Retry", ScreenGame.COLOR_BTN_GOOD);
		btn1.textPainter.setAlign(AlignX.RIGHT);
		btn1.textPainter.setVPaddingPercent(25);
		linl.add(btn1);
		
		linl.add(new LinearGap(50));
		
		final TextButton btn2 = new TextButton(thick_font, "Quit", ScreenGame.COLOR_BTN_BAD);
		btn2.textPainter.setAlign(AlignX.LEFT);
		btn2.textPainter.setVPaddingPercent(25);
		linl.add(btn2);
		
		btn1.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				try {
					WorldProvider.get().loadWorld(WorldProvider.get().getWorld().getSaveFile());
					screen.setState(GScrState.WORLD);
				} catch (final IOException e) {
					Log.e(e);
				}
			}
		});
		
		btn2.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new GameStateRequest(GameState.MAIN_MENU));
			}
		});
	}
	
	
	@Override
	public int getZIndex()
	{
		return 300;
	}
	
}
