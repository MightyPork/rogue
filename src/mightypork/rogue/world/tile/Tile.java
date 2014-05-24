package mightypork.rogue.world.tile;


import java.io.IOException;

import mightypork.gamecore.eventbus.BusAccess;
import mightypork.gamecore.eventbus.EventBus;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.color.Color;
import mightypork.ion.IonInput;
import mightypork.ion.IonObjBinary;
import mightypork.ion.IonOutput;
import mightypork.rogue.Const;
import mightypork.rogue.world.World;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.TileRenderContext;


/**
 * Tile data bundle. Client only renders.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class Tile implements BusAccess, IonObjBinary {
	
	// tmp extras
	public final TileGenData genData = new TileGenData();
	
	public final TileModel model;
	
	// temporary flag for map.
	protected boolean occupied;
	protected boolean explored;
	
	protected Level level;
	
	private TileRenderer renderer;
	
	
	public Tile(TileModel model)
	{
		this.model = model;
	}
	
	
	/**
	 * Render the tile, using the main texture sheet.
	 * 
	 * @param context rendering ctx
	 */
	@DefaultImpl
	public void renderTile(TileRenderContext context)
	{
		if (!isExplored() && Const.RENDER_UFOG) return;
		
		initRenderer();
		
		renderer.renderTile(context);
		
		if (doesReceiveShadow()) renderer.renderShadows(context);
	}
	
	
	@DefaultImpl
	public void renderUFog(TileRenderContext context)
	{
		initRenderer();
		
		renderer.renderUnexploredFog(context);
	}
	
	
	private void initRenderer()
	{
		if (renderer == null) {
			renderer = makeRenderer();
			
			if (renderer == /*still*/null) {
				Log.w("No renderer for tile " + Log.str(this));
				renderer = TileRenderer.NONE;
				return;
			}
		}
	}
	
	
	protected abstract TileRenderer makeRenderer();
	
	
	/**
	 * Render extra stuff (ie. dropped items).<br>
	 * Called after the whole map is rendered using renderTile.
	 * 
	 * @param context
	 */
	@DefaultImpl
	public void renderExtra(TileRenderContext context)
	{
		initRenderer();
		renderer.renderExtra(context);
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
		return getType() == TileType.DOOR || getType() == TileType.PASSAGE;
	}
	
	
	public final boolean isStairs()
	{
		return getType() == TileType.STAIRS;
	}
	
	
	@DefaultImpl
	public void updateTile(double delta)
	{
		initRenderer();
		renderer.update(delta);
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
	
	
	public Color getMapColor()
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
	
	
	/**
	 * Handle player click
	 * 
	 * @return true if the tile is interactive and did something.
	 */
	@DefaultImpl
	public boolean onClick()
	{
		return false;
	}
	
	
	public void setLevel(Level level)
	{
		this.level = level;
	}
	
	
	public Level getLevel()
	{
		return level;
	}
	
	
	protected World getWorld()
	{
		return level.getWorld();
	}
	
	
	@Override
	public EventBus getEventBus()
	{
		return level.getEventBus();
	}
	
}
