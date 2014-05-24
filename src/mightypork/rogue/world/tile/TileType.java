package mightypork.rogue.world.tile;


import mightypork.gamecore.util.math.color.Color;


/**
 * Kinds of tiles
 * 
 * @author Ondřej Hruška
 */
public enum TileType
{
	/** No tile */
	NULL(TileColors.NULL, false),
	/** Floor tile */
	FLOOR(TileColors.FLOOR, true),
	/** Wall tile */
	WALL(TileColors.WALL, false),
	/** Door/gate tile */
	DOOR(TileColors.DOOR, true),
	/** Passage (ie secret door) */
	PASSAGE(TileColors.COLLAPSED_WALL, true),
	/** Stairs */
	STAIRS(TileColors.WALL, false);
	
	private final Color mapColor;
	private final boolean potentiallyWalkable;
	
	
	private TileType(Color defaultMapColor, boolean potentiallyWalkable)
	{
		this.mapColor = defaultMapColor;
		this.potentiallyWalkable = potentiallyWalkable;
	}
	
	
	public Color getMapColor()
	{
		return mapColor;
	}
	
	
	public boolean isPotentiallyWalkable()
	{
		return potentiallyWalkable;
	}
}
