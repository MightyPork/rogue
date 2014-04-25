package mightypork.rogue.screens.gamescreen.world;


import mightypork.gamecore.control.events.input.MouseButtonEvent;
import mightypork.gamecore.control.events.input.MouseButtonListener;
import mightypork.gamecore.gui.components.InputComponent;
import mightypork.gamecore.render.Render;
import mightypork.rogue.world.Coord;
import mightypork.rogue.world.EntityPos;
import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.Tile;
import mightypork.util.math.color.Color;
import mightypork.util.math.constraints.rect.Rect;
import mightypork.util.math.constraints.rect.mutable.RectMutable;
import mightypork.util.math.constraints.vect.Vect;

import org.lwjgl.opengl.GL11;


public class Minimap extends InputComponent implements MouseButtonListener {
	
	private final World world;
	private final RectMutable bounds = Rect.makeVar();
	private int unit = 0;
	private final Color back = Color.rgba(0, 0.2, 0.2, 0.4);
	private final Color back2 = Color.rgba(0, 0.5, 0.5, 0.5);
	
	
	public Minimap(World w)
	{
		this.world = w;
	}
	
	
	@Override
	protected void renderComponent()
	{
		
		final Level lvl = world.getCurrentLevel();
		unit = (int) Math.min(Math.max(2, Math.ceil(height().value() / (lvl.getHeight() + 2))), 8);
		
		final World w = lvl.getWorld();
		final Entity e = w.getPlayerEntity();
		final EntityPos plCoord = e.getPosition();
		
		final int lw = lvl.getWidth();
		final int lh = lvl.getHeight();
		
		final Vect tl = topRight().sub(unit * lw, 0);
		
		bounds.setTo(tl, unit * lw, unit * lh);
		
		Render.quad(bounds.grow(unit * 0.5), back);
		Render.quad(bounds, back2);
		
		final Coord point = new Coord(tl.xi(), tl.yi());
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_QUADS);
		for (final Coord pos = Coord.zero(); pos.y < lh; pos.y++, point.y += unit) {
			for (pos.x = 0, point.x = tl.xi(); pos.x < lw; pos.x++, point.x += unit) {
				
				final Tile t = lvl.getTile(pos);
				if (t.isNull() || !t.data.explored) continue;
				
				final Color clr = t.getMapColor();
				
				GL11.glColor4d(clr.r(), clr.g(), clr.b(), clr.a() * 0.9);
				
				GL11.glVertex2i(point.x, point.y);
				GL11.glVertex2i(point.x + unit, point.y);
				GL11.glVertex2i(point.x + unit, point.y + unit);
				GL11.glVertex2i(point.x, point.y + unit);
			}
		}
		
		// player
		GL11.glColor3d(1, 0, 0);
		
		final double plx = tl.xi() + plCoord.visualX() * unit;
		final double ply = tl.yi() + plCoord.visualY() * unit;
		
		GL11.glVertex2d(plx, ply);
		GL11.glVertex2d(plx + unit, ply);
		GL11.glVertex2d(plx + unit, ply + unit);
		GL11.glVertex2d(plx, ply + unit);
		
		GL11.glEnd();
	}
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		if (event.isOver(bounds) && event.isUp()) {
			final Vect relative = event.getPos().sub(bounds.origin());
			final Coord actual = Coord.make(relative.xi() / unit, relative.yi() / unit);
			
			if (!world.getCurrentLevel().getTile(actual).data.explored) return; // unexplored
			
			world.getPlayerEntity().navigateTo(actual);
			event.consume();
		}
	}
}
