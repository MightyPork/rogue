package mightypork.rogue.screens;


import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.Overlay;
import mightypork.gamecore.resources.Res;
import mightypork.utils.Support;
import mightypork.utils.math.AlignX;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.animation.NumAnimated;
import mightypork.utils.math.color.pal.PAL16;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.timing.TimedTask;
import mightypork.utils.string.StringProvider;


/**
 * Overlay with blue background and loading-info text, that accompanies an async
 * task.
 *
 * @author Ondřej Hruška (MightyPork)
 */
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

	private boolean busy;
	private String msg;
	private Runnable task;

	private final TimedTask tt = new TimedTask() {

		@Override
		public void run()
		{
			Support.runAsThread(new Runnable() {

				@Override
				public void run()
				{
					task.run();
					alpha.setEasing(Easing.SINE_OUT);
					alpha.fadeOut(T_OUT);
					busy = false;
				}
			});
		}
	};


	public LoadingOverlay()
	{

		final QuadPainter qp = new QuadPainter(PAL16.SEABLUE);
		qp.setRect(root);
		root.add(qp);

		updated.add(alpha);
		updated.add(tt);

		Rect textRect = root.shrink(Num.ZERO, root.height().perc(48));
		textRect = textRect.moveY(root.height().perc(-10));

		final TextPainter tp = new TextPainter(Res.getFont("thick"), AlignX.CENTER, RGB.WHITE, msgStrProv);

		tp.setRect(textRect);
		tp.setShadow(RGB.BLACK_60, tp.height().mul(1 / 8D).toVectXY());
		root.add(tp);

		setAlpha(alpha);
	}


	@Override
	public int getZIndex()
	{
		return 10001; // not too high, so app can put something on top
	}


	/**
	 * Show for a task
	 *
	 * @param message task description
	 * @param task task
	 */
	public void show(String message, Runnable task)
	{
		if (busy) throw new IllegalStateException("Loader is busy with another task.");

		this.msg = message;
		this.task = task;
		this.busy = true;

		alpha.setEasing(Easing.SINE_IN);
		alpha.fadeIn(T_IN);

		tt.start(T_IN);
	}
}
