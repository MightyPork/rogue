package mightypork.rogue.world.tile;


import java.io.IOException;

import mightypork.ion.IonInput;
import mightypork.ion.IonOutput;


/**
 * Tile model (builder)
 * 
 * @author Ondřej Hruška
 */
public final class TileModel {
	
	/** Model ID */
	public final int id;
	public final Class<? extends Tile> tileClass;
	
	
	public TileModel(int id, Class<? extends Tile> tile)
	{
		Tiles.register(id, this);
		this.id = id;
		this.tileClass = tile;
	}
	
	
	/**
	 * @return new tile of this type
	 */
	public <T extends Tile> T createTile()
	{
		try {
			return (T) tileClass.getConstructor(TileModel.class).newInstance(this);
		} catch (final Exception e) {
			throw new RuntimeException("Could not instantiate a tile.", e);
		}
	}
	
	
	public Tile loadTile(IonInput in) throws IOException
	{
		final Tile t = createTile();
		t.load(in);
		return t;
	}
	
	
	public void saveTile(IonOutput out, Tile tile) throws IOException
	{
		if (tileClass != tile.getClass()) throw new RuntimeException("Tile class mismatch.");
		
		tile.save(out);
	}
}
