package mightypork.rogue.world.gui;


import mightypork.gamecore.core.App;
import mightypork.gamecore.gui.components.InputComponent;
import mightypork.gamecore.input.events.MouseButtonEvent;
import mightypork.gamecore.input.events.MouseButtonHandler;
import mightypork.rogue.Const;
import mightypork.rogue.world.PlayerFacade;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.Tile;
import mightypork.utils.math.algo.Coord;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.var.RectVar;
import mightypork.utils.math.constraints.vect.Vect;


public class Minimap extends InputComponent implements MouseButtonHandler {

	private final RectVar bounds = Rect.makeVar();
	private int unit = 0;
	private final Num translucency = Num.make(0.8);
	private final Color playerColor = RGB.RED;


	@Override
	protected void renderComponent()
	{
		Color.pushAlpha(translucency);

		final Level lvl = WorldProvider.get().getCurrentLevel();
		unit = (int) Math.min(Math.max(2, Math.ceil((height().value() / 2) / (lvl.getHeight() + 2))), 10);

		final Vect plCoord = WorldProvider.get().getPlayer().getVisualPos();

		final int lw = lvl.getWidth();
		final int lh = lvl.getHeight();

		final Vect tl = topRight().sub(unit * lw, 0);

		bounds.setTo(tl, unit * lw, unit * lh);

		final Coord point = new Coord(tl.xi(), tl.yi());

		// FIXME do not use LWJGL directly

		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glBegin(GL11.GL_QUADS);

		for (final Coord pos = Coord.zero(); pos.y < lh; pos.y++, point.y += unit) {
			for (pos.x = 0, point.x = tl.xi(); pos.x < lw; pos.x++, point.x += unit) {

				final Tile t = lvl.getTile(pos);
				if (t.isNull() || (!t.isExplored() && Const.RENDER_UFOG)) continue;

				final Color clr = t.getMapColor();

				App.gfx().setColor(clr);

				GL11.glVertex2i(point.x, point.y);
				GL11.glVertex2i(point.x + unit, point.y);
				GL11.glVertex2i(point.x + unit, point.y + unit);
				GL11.glVertex2i(point.x, point.y + unit);
			}
		}

		// player
		App.gfx().setColor(playerColor);

		final double plx = tl.xi() + plCoord.x() * unit;
		final double ply = tl.yi() + plCoord.y() * unit;

		GL11.glVertex2d(plx, ply);
		GL11.glVertex2d(plx + unit, ply);
		GL11.glVertex2d(plx + unit, ply + unit);
		GL11.glVertex2d(plx, ply + unit);

		GL11.glEnd();

		Color.popAlpha();
	}


	@Override
	public void receive(MouseButtonEvent event)
	{
		if (event.isOver(bounds) && event.getButton() == 1) {
			if (event.isUp()) {
				final Vect relative = event.getPos().sub(bounds.origin());
				final Coord actual = Coord.make(relative.xi() / unit, relative.yi() / unit);

				final PlayerFacade player = WorldProvider.get().getPlayer();

				if (player.getLevel().getTile(actual).isExplored()) {
					player.navigateTo(actual);
				}
			}

			event.consume();
		}
	}
}
