package mightypork.rogue.world.tile;


import java.util.Stack;

import mightypork.rogue.world.item.Item;

/**
 * Tile data object. Can be extended for particular models' needs.
 * 
 * @author MightyPork
 */
public abstract class TileData {
	
	/** Items dropped onto this tile */
	public final Stack<Item> items = new Stack<>();
	
}
