package mightypork.rogue.world.tile;


import mightypork.util.math.color.Color;
import mightypork.util.math.color.PAL16;
import mightypork.util.math.color.RGB;


/**
 * Kinds of tiles
 * 
 * @author MightyPork
 */
public enum TileType
{
	/** No tile */
	NULL(RGB.NONE, false),
	/** Floor tile */
	FLOOR(RGB.GRAY_DARK, true),
	/** Wall tile */
	WALL(RGB.GRAY_LIGHT, false),
	/** Door/gate tile */
	DOOR(PAL16.NEWPOOP, true);
	
	private final Color mapColor;
	private final boolean potentiallyWalkable;
	
	
	private TileType(Color mapColor, boolean potentiallyWalkable)
	{
		this.mapColor = mapColor;
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
