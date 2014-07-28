package mightypork.rogue.screens.game;


import java.io.File;

import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.fonts.IFont;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.components.input.TextButton;
import mightypork.gamecore.gui.components.layout.RowLayout;
import mightypork.gamecore.gui.components.layout.linear.LinearLayout;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.impl.FadingLayer;
import mightypork.gamecore.input.Trigger;
import mightypork.gamecore.resources.Res;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
import mightypork.rogue.world.WorldProvider;
import mightypork.utils.math.AlignX;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.num.Num;


public class LayerWin extends FadingLayer {

	public LayerWin(final ScreenGame screen)
	{
		super(screen);

		// darker down to cover console.
		final QuadPainter qp = new QuadPainter(RGB.BLACK_80);
		qp.setRect(root);
		root.add(qp);

		final IFont thick_font = Res.getFont("thick");

		final RowLayout rl = new RowLayout(root, 5);
		rl.setRect(root.shrink(Num.ZERO, root.height().perc(15)));
		root.add(rl);

		final TextPainter txp = new TextPainter(thick_font, AlignX.CENTER, RGB.YELLOW, "You won!");
		rl.add(txp, 1);
		txp.setVPaddingPercent(13);

		LinearLayout linl = new LinearLayout(root, AlignX.CENTER);
		linl.add(new ImagePainter(Res.getTxQuad("win")));
		rl.add(linl, 3);

		linl = new LinearLayout(root, AlignX.CENTER);
		rl.add(linl);

		final TextButton btn1 = new TextButton(thick_font, "Leave", ScreenGame.COLOR_BTN_GOOD);
		btn1.textPainter.setVPaddingPercent(25);
		linl.add(btn1);

		final Action quit = new Action() {

			@Override
			protected void execute()
			{
				// delete the save file
				final File f = WorldProvider.get().getWorld().getSaveFile();
				f.delete();

				App.bus().send(new RogueStateRequest(RogueState.MAIN_MENU));
			}
		};

		btn1.setAction(quit);

		bindKey(App.cfg().getKeyStroke("general.confirm"), Trigger.RISING, quit);
		bindKey(App.cfg().getKeyStroke("general.close"), Trigger.RISING, quit);
	}


	@Override
	public int getZIndex()
	{
		return 300;
	}


	@Override
	protected void onShowFinished()
	{
		App.audio().fadeOutAllLoops();
		Res.getSoundEffect("game.win").play(1);
	}

}
