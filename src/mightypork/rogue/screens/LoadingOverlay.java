package mightypork.rogue.screens;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.events.ScreenRequest;
import mightypork.gamecore.gui.screens.Overlay;
import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.PAL16;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.timing.TimedTask;
import mightypork.gamecore.util.strings.StringProvider;
import mightypork.rogue.Res;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;


public class LoadingOverlay extends Overlay {
	
	private static final double T_IN = 0.5;
	private static final double T_OUT = 1;
	
	private final NumAnimated alpha = new NumAnimated(0);
	
	private final StringProvider msgStrProv = new StringProvider() {
		
		@Override
		public String getString()
		{
			return msg == null ? "" : msg;
		}
	};
	private String msg;
	
	
	public LoadingOverlay(AppAccess app) {
		super(app);
		
		final QuadPainter qp = new QuadPainter(PAL16.SEABLUE);
		qp.setRect(root);
		root.add(qp);
		
		updated.add(alpha);
		
		Rect textRect = root.shrink(Num.ZERO, root.height().perc(48));
		textRect = textRect.moveY(root.height().perc(-10));
		
		final TextPainter tp = new TextPainter(Res.getFont("thick"), AlignX.CENTER, RGB.WHITE, msgStrProv);
		
		tp.setRect(textRect);
		tp.setShadow(RGB.BLACK_60, tp.height().mul(1/8D).toVectXY());
		root.add(tp);
	}
	
	
	@Override
	public int getZIndex()
	{
		return 10001; // not too high, so app can put something on top
	}
	
	
	public void show(String message)
	{
		this.msg = message;
		alpha.setEasing(Easing.SINE_IN);
		alpha.fadeIn(T_IN);
	}
	
	
	public void hide()
	{
		
		alpha.setEasing(Easing.SINE_OUT);
		alpha.fadeOut(T_OUT);
	}
	
	@Override
	public void render()
	{
		Color.pushAlpha(alpha);
		super.render();
		Color.popAlpha();
	}
}
