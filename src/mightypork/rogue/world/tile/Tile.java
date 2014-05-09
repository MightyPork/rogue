package mightypork.rogue.world.tile;


import java.io.IOException;
import java.util.Random;

import mightypork.gamecore.logging.Log;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonObjBlob;
import mightypork.gamecore.util.ion.IonOutput;
import mightypork.gamecore.util.math.color.Color;
import mightypork.rogue.world.World;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.LevelAccess;
import mightypork.rogue.world.level.render.TileRenderContext;


/**
 * Tile data bundle. Client only renders.
 * 
 * @author MightyPork
 */
public abstract class Tile implements IonObjBlob {
	
	// tmp extras
	public final TileGenData genData = new TileGenData();
	
	/** RNG for random stuff in tiles */
	protected static final Random rand = new Random();
	
	public final TileModel model;
	
	// temporary flag for map.
	protected boolean occupied;
	protected boolean explored;
	
	protected LevelAccess level;
	
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
		if (!isExplored()) return;
		
		if (renderer == null) {
			renderer = makeRenderer();
		}
		
		if (renderer == null) {
			Log.w("No renderer for tile " + Log.str(this));
			return;
		}
		
		renderer.renderTile(context);
		
		if (doesReceiveShadow()) renderer.renderShadows(context);
		
		renderer.renderUnexploredFog(context);
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
	
	
	@DefaultImpl
	public void update(double delta)
	{
		makeRenderer().update(delta);
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
	
	
	public void setLevel(LevelAccess level)
	{
		this.level = level;
	}
	
	
	public LevelAccess getLevel()
	{
		return level;
	}
	
	
	protected World getWorld()
	{
		return level.getWorld();
	}
	
}
