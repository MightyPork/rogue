package mightypork.rogue.world;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;

import mightypork.util.control.timing.Updateable;
import mightypork.util.files.ion.BinaryUtils;
import mightypork.util.files.ion.Ionizable;


/**
 * Concrete tile in the world.
 * 
 * @author MightyPork
 */
public class TileData implements Ionizable, Updateable {
	
	public static final short ION_MARK = 700;
	
	/** Items dropped onto this tile */
	public final Stack<ItemData> items = new Stack<>();
	
	/** Whether the tile is occupied by an entity */
	public boolean occupied;
	
	private TileModel model;
	
	// data fields for models to use
	public Object data;
	public boolean[] flags;
	public int[] ints;
	
	
	/**
	 * Create from model id
	 * 
	 * @param id model id
	 */
	public TileData(int id) {
		this(Tiles.get(id));
	}
	
	
	/**
	 * Create from model
	 * 
	 * @param model model
	 */
	public TileData(TileModel model) {
		this.model = model;
		model.create(this);
	}
	
	
	/**
	 * Create without model. Model will be read from ION input stream.
	 */
	public TileData() {
	}
	
	
	@Override
	public void loadFrom(InputStream in) throws IOException
	{
		final int id = BinaryUtils.readInt(in);
		model = Tiles.get(id);
		model.load(this, in);
	}
	
	
	@Override
	public void saveTo(OutputStream out) throws IOException
	{
		BinaryUtils.writeInt(out, model.getId());
		model.save(this, out);
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
	
	public void render(TileRenderContext context)
	{
		model.render(this, context);
		if (!items.isEmpty()) items.peek().render(context);
	}
	
	
	@Override
	public void update(double delta)
	{
		model.update(this, delta);
		if (!items.isEmpty()) items.peek().update(delta);
	}
}
