package mightypork.rogue.world.tile;


import java.io.IOException;
import java.util.Stack;

import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.models.TileModel;
import mightypork.rogue.world.tile.renderers.TileRenderer;
import mightypork.util.files.ion.IonBinary;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.files.ion.IonInput;
import mightypork.util.files.ion.IonOutput;


/**
 * Tile data bundle. Client only renders.
 * 
 * @author MightyPork
 */
public final class Tile implements IonBinary {
	
	public static final short ION_MARK = 50;
	
	private TileModel model;
	private TileRenderer renderer;
	
	private int id;
	
	private final Stack<Item> items = new Stack<>();
	
	/** persistent field for model, reflected by renderer */
	public final IonBundle metadata = new IonBundle();
	
	// temporary flag for map.
	private boolean occupied;
	
	// for renderer of AO shadows
	public byte shadows;
	public boolean shadowsComputed;
	
	
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
	
	
	/**
	 * Render the tile alone (must not use other than the main map texture)
	 */
	public void renderTile(TileRenderContext context)
	{
		renderer.render(context);
		
		if (hasItems()) {
			renderer.renderItemOnTile(items.peek(), context);
		}
	}
	
	
	/**
	 * Render items
	 * 
	 * @param context
	 */
	public void renderItems(TileRenderContext context)
	{
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
		
		if (model.hasMetadata()) {
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
		
		if (model.hasMetadata()) {
			in.readBundle(metadata);
		}
	}
	
	
	/**
	 * Update tile logic state (on server)
	 * 
	 * @param level the level
	 * @param delta delta time
	 */
	public void update(Level level, double delta)
	{
		model.update(this, level, delta);
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
	
	
	public boolean isOccupied()
	{
		return occupied;
	}
	
	
	public void setOccupied(boolean occupied)
	{
		this.occupied = occupied;
	}
}
