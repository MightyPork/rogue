package mightypork.rogue.world.tile;


import java.io.IOException;
import java.util.Stack;

import mightypork.rogue.world.WorldAccess;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.map.Level;
import mightypork.rogue.world.map.TileRenderContext;
import mightypork.util.control.timing.Animator;
import mightypork.util.ion.IonBinary;
import mightypork.util.ion.IonBundle;
import mightypork.util.ion.IonInput;
import mightypork.util.ion.IonOutput;


/**
 * Tile data bundle. Client only renders.
 * 
 * @author MightyPork
 */
public final class Tile implements IonBinary {
	
	public static final short ION_MARK = 50;
	
	private TileModel model;
	private TileRenderer renderer;
	
	public int id;
	
	private final Stack<Item> items = new Stack<>();
	
	/** persistent field for model, reflected by renderer */
	public final IonBundle metadata = new IonBundle();
	
	/** non-persistent data field for model */
	public Object tmpdata;
	
	
	public Tile(int id)
	{
		this(Tiles.get(id));
	}
	
	
	public Tile(TileModel model)
	{
		setModel(model);
	}
	
	
	public Tile()
	{
	}
	
	
	private void setModel(TileModel model)
	{
		this.model = model;
		this.id = model.id;
		this.renderer = model.renderer;
	}
	
	
	public void render(TileRenderContext context)
	{
		renderer.render(context);
		
		if (hasItems()) {
			renderer.renderItemOnTile(items.peek(), context);
		}
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		out.writeIntByte(id);
		
		if (model.hasDroppedItems()) {
			out.writeSequence(items);
		}
		
		if (model.hasPersistentMetadata()) {
			out.writeBundle(metadata);
		}
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		id = in.readIntByte();
		
		// if model changed
		if (model == null || id != model.id) {
			setModel(Tiles.get(id));
		}
		
		if (model.hasDroppedItems()) {
			in.readSequence(items);
		}
		
		if (model.hasPersistentMetadata()) {
			in.readBundle(metadata);
		}
	}
	
	
	/**
	 * Update tile logic state (on server)
	 * 
	 * @param world the world
	 * @param delta delta time
	 */
	public void updateLogic(WorldAccess world, Level level, double delta)
	{
		model.updateLogic(this, world, level, delta);
	}


	public void updateVisual(WorldAccess world, Level level, double delta)
	{
		model.updateVisual(this, world, level, delta);
	}
	
	
	public boolean isWalkable()
	{
		return model.isWalkable(this);
	}
	
	
	public TileModel getModel()
	{
		return model;
	}
	
	
	public boolean hasItems()
	{
		return model.hasDroppedItems() && !items.isEmpty();
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
}
