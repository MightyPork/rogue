package mightypork.rogue.screens.game;


import java.io.IOException;

import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.input.TextButton;
import mightypork.gamecore.gui.components.layout.RowLayout;
import mightypork.gamecore.gui.components.layout.linear.LinearLayout;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.KeyStroke.Edge;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;
import mightypork.gamecore.util.math.timing.TimedTask;
import mightypork.rogue.screens.game.ScreenGame.GScrState;
import mightypork.rogue.world.WorldProvider;


public class AskSaveLayer extends ScreenLayer {
	
	public Runnable task;
	
	NumAnimated numa = new NumAnimated(0, Easing.QUADRATIC_OUT);
	TimedTask hideTT = new TimedTask() {
		
		@Override
		public void run()
		{
			gscreen.setState(GScrState.WORLD); // go back..
		}
	};
	
	private final ScreenGame gscreen;
	
	
	public void setTask(Runnable task)
	{
		this.task = task;
	}
	
	
	public AskSaveLayer(final ScreenGame screen)
	{
		super(screen);
		this.gscreen = screen;
		
		// darker down to cover console.
		final QuadPainter qp = new QuadPainter(RGB.BLACK_80);
		qp.setRect(root);
		root.add(qp);
		
		final GLFont thick_font = Res.getFont("thick");
		
		final RowLayout rl = new RowLayout(root, 2);
		rl.setRect(root.shrink(Num.ZERO, root.height().perc(40)).moveY(root.height().perc(-10)));
		root.add(rl);
		
		final TextPainter txp = new TextPainter(thick_font, AlignX.CENTER, RGB.WHITE, "Save the game?");
		rl.add(txp, 1);
		txp.setVPaddingPercent(25);
		
		final LinearLayout ll = new LinearLayout(root, AlignX.CENTER);
		rl.add(ll);
		
		final double vPadPerc = 20;
		
		final TextButton btn1 = new TextButton(thick_font, "Yes", ScreenGame.COLOR_BTN_GOOD);
		btn1.textPainter.setAlign(AlignX.RIGHT);
		btn1.textPainter.setVPaddingPercent(vPadPerc);
		ll.add(btn1);
		ll.gap(50);
		
		final TextButton btn2 = new TextButton(thick_font, "No", ScreenGame.COLOR_BTN_BAD);
		btn2.textPainter.setAlign(AlignX.CENTER);
		btn2.textPainter.setVPaddingPercent(vPadPerc);
		ll.add(btn2);
		ll.gap(50);
		
		final TextButton btn3 = new TextButton(thick_font, "Cancel", ScreenGame.COLOR_BTN_CANCEL);
		btn3.textPainter.setAlign(AlignX.LEFT);
		btn3.textPainter.setVPaddingPercent(vPadPerc);
		ll.add(btn3);
		
		final Action cancel = new Action() {
			
			@Override
			protected void execute()
			{
				if (numa.isFinished()) {
					numa.fadeOut(0.3);
					hideTT.start(0.3);
				}
			}
		};
		
		btn1.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				try {
					WorldProvider.get().saveWorld();
					if (task != null) task.run();
				} catch (final IOException e) {
					Log.e(e);
				}
			}
		});
		
		btn2.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				if (task != null) task.run();
			}
		});
		
		btn3.setAction(cancel);
		
		bindKey(new KeyStroke(Keys.ESCAPE), Edge.RISING, cancel);
		
		updated.add(numa);
		updated.add(hideTT);
		
		setAlpha(numa);
	}
	
	
	@Override
	public int getZIndex()
	{
		return 301;
	}
	
	
	@Override
	public void show()
	{
		if (!isVisible()) {
			super.show();
			numa.fadeIn(0.3);
			hideTT.stop();
		}
	}
	
}