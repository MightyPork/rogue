package mightypork.rogue.world.tile;


import java.io.IOException;

import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.util.annotations.DefaultImpl;
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
	
	public final TileModel model;
	
	// temporary flag for map.
	protected boolean occupied;
	protected boolean explored;
	
	
	public Tile(TileModel model, TileRenderer renderer)
	{
		this.model = model;
		this.renderer = renderer;
	}
	
	
	/**
	 * Render the tile, using the main texture sheet.
	 */
	@DefaultImpl
	public void renderTile(TileRenderContext context)
	{
		if (!isExplored()) return;
		
		renderer.renderTile(context);
		
		if (doesReceiveShadow()) renderer.renderShadows(context);
		
		renderer.renderUnexploredFog(context);
	}
	
	
	/**
	 * Render extra stuff (ie. dropped items).<br>
	 * Called after the whole map is rendered using renderTile.
	 * 
	 * @param context
	 */
	@DefaultImpl
	public void renderExtra(TileRenderContext context)
	{
	}
	
	
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
	
	
	@DefaultImpl
	public void update(Level level, double delta)
	{
	}
	
	
	/**
	 * Check if this tile is right now walkable.<br>
	 * If type is not potentially walkable, this method must return false.
	 * 
	 * @return true if currently walkable
	 */
	@DefaultImpl
	public boolean isWalkable()
	{
		return isPotentiallyWalkable();
	}
	
	
	public final boolean isPotentiallyWalkable()
	{
		return getType().isPotentiallyWalkable();
	}
	
	
	public abstract TileType getType();
	
	
	public abstract boolean doesCastShadow();
	
	
	@DefaultImpl
	public boolean doesReceiveShadow()
	{
		return !doesCastShadow();
	}
	
	
	public final Color getMapColor()
	{
		return getType().getMapColor();
	}
	
	
	public final TileModel getModel()
	{
		return model;
	}
	
	
	/**
	 * Drop item onto this tile
	 * 
	 * @param item
	 * @return true if dropped
	 */
	public abstract boolean dropItem(Item item);
	
	
	/**
	 * Remove an item from this tile
	 * 
	 * @return the picked item, or null if none
	 */
	public abstract Item pickItem();
	
	
	/**
	 * @return true if the tile has dropped items
	 */
	public abstract boolean hasItem();
	
}
