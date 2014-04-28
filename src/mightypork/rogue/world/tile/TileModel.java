package mightypork.rogue.world.tile;


import java.io.IOException;

import mightypork.util.files.ion.IonInput;
import mightypork.util.files.ion.IonOutput;


/**
 * Tile model (logic of a tile)
 * 
 * @author MightyPork
 */
public final class TileModel {
	
	/** Model ID */
	public final int id;
	public final TileRenderer renderer;
	public final Class<? extends Tile> tileClass;
	
	
	public TileModel(int id, Class<? extends Tile> tile, TileRenderer renderer)
	{
		Tiles.register(id, this);
		this.id = id;
		this.renderer = renderer;
		this.tileClass = tile;
	}
	
	
	/**
	 * @return new tile of this type
	 */
	public Tile createTile()
	{
		try {
			return tileClass.getConstructor(TileModel.class, TileRenderer.class).newInstance(this, renderer);
		} catch (Exception e) {
			throw new RuntimeException("Could not instantiate a tile.", e);
		}
	}
	
	
	public Tile loadTile(IonInput in) throws IOException
	{
		Tile t = createTile();
		t.load(in);
		return t;
	}
	
	
	public void saveTile(IonOutput out, Tile tile) throws IOException
	{
		tile.save(out);
	}
}
