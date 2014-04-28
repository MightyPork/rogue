package mightypork.rogue.screens.gamescreen.world;


import mightypork.gamecore.control.events.input.MouseButtonEvent;
import mightypork.gamecore.control.events.input.MouseButtonListener;
import mightypork.gamecore.gui.components.InputComponent;
import mightypork.gamecore.render.Render;
import mightypork.rogue.world.Coord;
import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.modules.EntityPos;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.Tile;
import mightypork.util.math.color.Color;
import mightypork.util.math.color.RGB;
import mightypork.util.math.constraints.num.Num;
import mightypork.util.math.constraints.rect.Rect;
import mightypork.util.math.constraints.rect.mutable.RectMutable;
import mightypork.util.math.constraints.vect.Vect;

import org.lwjgl.opengl.GL11;


public class Minimap extends InputComponent implements MouseButtonListener {
	
	private final RectMutable bounds = Rect.makeVar();
	private int unit = 0;
	private final Num translucency = Num.make(0.8);
	private final Color playerColor = RGB.RED;
	
	
	
	@Override
	protected void renderComponent()
	{
		Color.pushAlpha(translucency);
		
		final Level lvl = WorldProvider.get().getCurrentLevel();
		unit = (int) Math.min(Math.max(2, Math.ceil((height().value()/2) / (lvl.getHeight() + 2))), 6);
		
		final World w = lvl.getWorld();
		final Entity e = w.getPlayerEntity();
		final Vect plCoord = e.pos.getVisualPos();
		
		final int lw = lvl.getWidth();
		final int lh = lvl.getHeight();
		
		final Vect tl = topRight().sub(unit * lw, 0);
		
		bounds.setTo(tl, unit * lw, unit * lh);
		
		final Coord point = new Coord(tl.xi(), tl.yi());
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glBegin(GL11.GL_QUADS);
		
		for (final Coord pos = Coord.zero(); pos.y < lh; pos.y++, point.y += unit) {
			for (pos.x = 0, point.x = tl.xi(); pos.x < lw; pos.x++, point.x += unit) {
				
				final Tile t = lvl.getTile(pos);
				if (t.isNull() || !t.isExplored()) continue;
				
				final Color clr = t.getMapColor();
				
				Render.setColor(clr);
				
				GL11.glVertex2i(point.x, point.y);
				GL11.glVertex2i(point.x + unit, point.y);
				GL11.glVertex2i(point.x + unit, point.y + unit);
				GL11.glVertex2i(point.x, point.y + unit);
			}
		}
		
		// player
		Render.setColor(playerColor);
		
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
		if (event.isOver(bounds)) {
			if (event.isUp()) {
				final Vect relative = event.getPos().sub(bounds.origin());
				final Coord actual = Coord.make(relative.xi() / unit, relative.yi() / unit);
				final Entity player = WorldProvider.get().getPlayerEntity();
				
				if (player.getLevel().getTile(actual).isExplored()) {
					player.pos.navigateTo(actual);
				}
			}
			
			event.consume();
		}
	}
}
