package mightypork.rogue.world.tile;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;

import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.map.TileRenderContext;
import mightypork.util.control.timing.Animator;
import mightypork.util.control.timing.Updateable;
import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.files.ion.IonConstructor;
import mightypork.util.files.ion.Ionizable;
import mightypork.util.files.ion.Streamable;


public final class Tile implements Ionizable, Updateable {
	
	public static final short ION_MARK = 700;
	
	private transient TileModel model;
	
	/**
	 * Temporary storage for the model (unlocked door state, lever switched etc)
	 */
	public transient Object modelData;
	
	/** Animator field for the model to use, if needed */
	public transient Animator anim;
	
	private transient DroppedItemRenderer itemRenderer; // lazy
	
	public int id;
	private final Stack<Item> items = new Stack<>();
	
	
	public Tile(int id)
	{
		this(Tiles.get(id));
	}
	
	
	public Tile(TileModel model)
	{
		this.model = model;
		this.id = model.id;
	}
	
	
	@IonConstructor
	public Tile()
	{
	}
	
	
	public void render(TileRenderContext context)
	{
		model.render(context);
		
		if (!items.isEmpty()) {
			getItemRenderer().render(items.peek(), context);
		}
	}
	
	
	@Override
	public void save(OutputStream out) throws IOException
	{
		if (model.isNullTile()) throw new RuntimeException("Cannot save null tile.");
		
		Ion.writeShort(out, (short) id); // tile ID
		Ion.writeSequence(out, items); // if empty, writes single END mark
		
		// models with metadata can save their stuff
		if (model.hasMetadata()) {
			IonBundle ib = new IonBundle();
			model.saveMetadata(this, ib);
			Ion.writeObject(out, ib);
		}
	}
	
	
	@Override
	public void load(InputStream in) throws IOException
	{
		id = Ion.readShort(in);
		
		// check if model is changed (can happen)
		if (model == null || id != model.id) {
			model = Tiles.get(id);
		}
		
		Ion.readSequence(in, items); // if END is found, nothing is read.
		
		// load model's stuff
		if (model.hasMetadata()) {
			IonBundle ib = (IonBundle) Ion.readObject(in);
			model.loadMetadata(this, ib);
		}
	}
	
	
	@Override
	public void update(double delta)
	{
		model.update(this, delta);
		if (!items.isEmpty()) {
			getItemRenderer().update(delta);
		}
	}
	
	
	private DroppedItemRenderer getItemRenderer()
	{
		if (itemRenderer == null) {
			itemRenderer = new DroppedItemRenderer();
		}
		
		return itemRenderer;
	}
	
	
	public TileModel getModel()
	{
		return model;
	}
	
	
	public boolean hasItems()
	{
		return !items.isEmpty();
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
}
