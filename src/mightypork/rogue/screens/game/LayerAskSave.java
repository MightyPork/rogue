package mightypork.rogue.screens.game;


import java.io.IOException;

import mightypork.gamecore.config.Config;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.input.TextButton;
import mightypork.gamecore.gui.components.layout.RowLayout;
import mightypork.gamecore.gui.components.layout.linear.LinearLayout;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.impl.FadingLayer;
import mightypork.gamecore.input.Edge;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.fonts.IFont;
import mightypork.rogue.screens.game.ScreenGame.GScrState;
import mightypork.rogue.world.WorldProvider;
import mightypork.utils.logging.Log;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.num.Num;


public class LayerAskSave extends FadingLayer {
	
	public Runnable task;
	
	private final ScreenGame gscreen;
	
	
	public void setTask(Runnable task)
	{
		this.task = task;
	}
	
	
	@Override
	protected void onHideFinished()
	{
		if (gscreen.getState() != GScrState.WORLD) {
			gscreen.setState(GScrState.WORLD); // go back..
		}
	}
	
	
	public LayerAskSave(final ScreenGame screen) {
		super(screen);
		this.gscreen = screen;
		
		// darker down to cover console.
		final QuadPainter qp = new QuadPainter(RGB.BLACK_80);
		qp.setRect(root);
		root.add(qp);
		
		final IFont thick_font = Res.getFont("thick");
		
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
				hide();
			}
		};
		
		final Action save = new Action() {
			
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
		};
		
		final Action discard = new Action() {
			
			@Override
			protected void execute()
			{
				if (task != null) task.run();
			}
		};
		
		btn1.setAction(save);
		btn2.setAction(discard);
		btn3.setAction(cancel);
		
		bindKey(Config.getKeyStroke("general.close"), Edge.RISING, cancel);
		bindKey(Config.getKeyStroke("general.cancel"), Edge.RISING, cancel);
		
		bindKey(Config.getKeyStroke("general.yes"), Edge.RISING, save);
		bindKey(Config.getKeyStroke("general.confirm"), Edge.RISING, save);
		
		bindKey(Config.getKeyStroke("general.no"), Edge.RISING, discard);
	}
	
	
	@Override
	public int getZIndex()
	{
		return 300;
	}
	
	
	@Override
	public void update(double delta)
	{
		super.update(delta);
	}
}
