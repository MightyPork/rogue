package mightypork.rogue.world.tile;


import java.io.IOException;
import java.util.Stack;

import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.util.files.ion.IonBinary;
import mightypork.util.files.ion.IonBinaryHeadless;
import mightypork.util.files.ion.IonInput;
import mightypork.util.files.ion.IonOutput;
import mightypork.util.math.color.Color;


/**
 * Tile data bundle. Client only renders.
 * 
 * @author MightyPork
 */
public abstract class Tile implements IonBinaryHeadless {
	
	// tmp extras
	public final TileRenderData renderData = new TileRenderData();
	public final TileGenData genData = new TileGenData();
	
	protected final TileRenderer renderer;
	
	public final int id;
	
	protected final Stack<Item> items = new Stack<>();
	
	
	// temporary flag for map.
	protected boolean occupied;
	protected boolean explored;
	
	
	public Tile(int id, TileRenderer renderer)
	{
		this.id = id;
		this.renderer = renderer;
	}
	
	
	/**
	 * Render the tile, using the main texture sheet.
	 */
	public abstract void renderTile(TileRenderContext context);
	
	
	/**
	 * Render extra stuff (ie. dropped items).<br>
	 * Called after the whole map is rendered using renderTile.
	 * 
	 * @param context
	 */
	public abstract void renderExtra(TileRenderContext context);
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		out.writeBoolean(explored);
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		explored = in.readBoolean();
	}
	
	
	public final boolean isOccupied()
	{
		return occupied;
	}
	
	
	public final void setOccupied(boolean occupied)
	{
		this.occupied = occupied;
	}
	
	
	public final boolean isExplored()
	{
		return explored;
	}
	
	
	public void setExplored()
	{
		explored = true;
	}
	
	
	public final boolean isNull()
	{
		return getType() == TileType.NULL;
	}
	
	
	public final boolean isWall()
	{
		return getType() == TileType.WALL;
	}
	
	
	public final boolean isFloor()
	{
		return getType() == TileType.FLOOR;
	}
	
	
	public final boolean isDoor()
	{
		return getType() == TileType.DOOR;
	}
	
	
	public abstract void update(Level level, double delta);
	
	
	public abstract boolean isWalkable();
	
	
	public abstract boolean isPotentiallyWalkable();
	
	
	public abstract TileType getType();
	
	
	public abstract boolean canHaveItems();
	
	
	public abstract boolean doesCastShadow();
	
	
	public abstract boolean doesReceiveShadow();
	
	
	public abstract Color getMapColor();
	
}
