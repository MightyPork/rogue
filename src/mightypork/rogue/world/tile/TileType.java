package mightypork.rogue.world.tile;


import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.PAL16;
import mightypork.gamecore.util.math.color.pal.RGB;


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
	DOOR(PAL16.NEWPOOP, true),
	/** Passage (ie secret door) */
	PASSAGE(RGB.GRAY, true),
	/** Stairs */
	STAIRS(RGB.CYAN, false);
	
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
