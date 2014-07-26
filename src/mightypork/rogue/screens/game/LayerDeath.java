package mightypork.rogue.screens.game;


import java.io.IOException;

import mightypork.gamecore.core.config.Config;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.components.input.TextButton;
import mightypork.gamecore.gui.components.layout.RowLayout;
import mightypork.gamecore.gui.components.layout.linear.LinearGap;
import mightypork.gamecore.gui.components.layout.linear.LinearLayout;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.impl.FadingLayer;
import mightypork.gamecore.input.Edge;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.fonts.IFont;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
import mightypork.rogue.screens.game.ScreenGame.GScrState;
import mightypork.rogue.world.WorldProvider;
import mightypork.utils.logging.Log;
import mightypork.utils.math.AlignX;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.num.Num;


public class LayerDeath extends FadingLayer {
	
	public LayerDeath(final ScreenGame screen) {
		super(screen);
		
		// darker down to cover console.
		final QuadPainter qp = new QuadPainter(RGB.BLACK_80);
		qp.setRect(root);
		root.add(qp);
		
		final IFont thick_font = Res.getFont("thick");
		
		final RowLayout rl = new RowLayout(root, 5);
		rl.setRect(root.shrink(Num.ZERO, root.height().perc(15)));
		root.add(rl);
		
		final TextPainter txp = new TextPainter(thick_font, AlignX.CENTER, RGB.YELLOW, "Rats won!");
		rl.add(txp, 1);
		txp.setVPaddingPercent(13);
		
		LinearLayout linl = new LinearLayout(root, AlignX.CENTER);
		linl.add(new ImagePainter(Res.getTxQuad("death2")));
		rl.add(linl, 3);
		
		linl = new LinearLayout(root, AlignX.CENTER);
		rl.add(linl);
		
		final TextButton btn1 = new TextButton(thick_font, "Retry", ScreenGame.COLOR_BTN_GOOD);
		btn1.textPainter.setVPaddingPercent(25);
		linl.add(btn1);
		
		linl.add(new LinearGap(50));
		
		final TextButton btn2 = new TextButton(thick_font, "Leave", ScreenGame.COLOR_BTN_BAD);
		btn2.textPainter.setVPaddingPercent(25);
		linl.add(btn2);
		
		final Action load = new Action() {
			
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
		};
		
		final Action quit = new Action() {
			
			@Override
			protected void execute()
			{
				getEventBus().send(new RogueStateRequest(RogueState.MAIN_MENU));
			}
		};
		
		btn1.setAction(load);
		btn2.setAction(quit);
		
		bindKey(Config.getKeyStroke("game.load"), Edge.RISING, load);
		bindKey(Config.getKeyStroke("general.confirm"), Edge.RISING, load);
		bindKey(Config.getKeyStroke("general.close"), Edge.RISING, quit);
	}
	
	
	@Override
	public int getZIndex()
	{
		return 300;
	}
	
}
