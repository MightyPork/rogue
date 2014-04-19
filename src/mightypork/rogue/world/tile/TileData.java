package mightypork.rogue.world.tile;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;

import mightypork.rogue.world.item.Item;
import mightypork.util.files.ion.IonList;
import mightypork.util.files.ion.Ionizable;


/**
 * Tile data object.
 * 
 * @author MightyPork
 */
public final class TileData implements Ionizable {
	
	public static final short ION_MARK = 703;
	
	/** Items dropped onto this tile */
	public final Stack<Item> items = new Stack<>();	
	
	public int id;
	
	public boolean[] flags;
	public int[] numbers;
	
	
	@Override
	public void loadFrom(InputStream in) throws IOException
	{
		
	}
	
	@Override
	public void saveTo(OutputStream out) throws IOException
	{
	}
	
	@Override
	public short getIonMark()
	{
		return 0;
	}
	
	public Tile toTile() {
		Tile t = new Tile(Tiles.get(id));
		t.s
	}
	
}
