package mightypork.rogue.world.gui;


import java.util.Collection;
import java.util.ConcurrentModificationException;

import mightypork.gamecore.graphics.fonts.FontRenderer;
import mightypork.gamecore.gui.components.BaseComponent;
import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.WorldConsole;
import mightypork.rogue.world.WorldProvider;
import mightypork.utils.logging.Log;
import mightypork.utils.math.AlignX;
import mightypork.utils.math.Calc;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.num.var.NumVar;
import mightypork.utils.math.constraints.rect.Rect;


public class WorldConsoleRenderer extends BaseComponent {

	private final Num rowHeight;
	private final FontRenderer fr;
	private final Rect itemViewRect;


	public WorldConsoleRenderer(Num rowHeight)
	{
		this.rowHeight = rowHeight;
		this.fr = new FontRenderer(Res.font("tiny"));

		final Num itmsize = height().perc(25).min(256).max(16);

		this.itemViewRect = bottomRight().sub(itmsize.perc(25), itmsize.perc(25)).startRect().grow(itmsize, Num.ZERO, itmsize, Num.ZERO);
	}


	@Override
	protected void renderComponent()
	{
		final double rh = rowHeight.value();

		final Rect lowRow = bottomEdge().growUp(rowHeight);

		final WorldConsole console = WorldProvider.get().getWorld().getConsole();
		final Collection<WorldConsole.Entry> entries = console.getEntries();

		int cnt = 0;

		final NumVar alph = Num.makeVar();

		Color.pushAlpha(alph);

		try {

			for (final WorldConsole.Entry entry : entries) {

				alph.setTo(entry.getAlpha());

				final Rect rrr = lowRow.moveY(-rh * cnt);

				fr.draw(entry.getMessage(), rrr.move(rh / 8D, rh / 8D), AlignX.LEFT, RGB.BLACK_60);
				fr.draw(entry.getMessage(), rrr, AlignX.LEFT, RGB.WHITE);

				cnt++;
			}

		} catch (final ConcurrentModificationException e) {
			Log.e(e); // this should not happen
		}

		if (console.lastPickupItem != null) {

			double alpha = 1;
			if (console.timeSinceLastPickup > 2) {
				alpha = 1 - Easing.CIRC_OUT.get(Calc.clamp((console.timeSinceLastPickup - 2) / 1, 0, 1));
			}

			alph.setTo(alpha);
			console.lastPickupItem.render(itemViewRect);
		}

		Color.popAlpha();

	}

}
