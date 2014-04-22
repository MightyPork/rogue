package mightypork.rogue.world.tile;


import java.io.IOException;
import java.util.Stack;

import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.map.TileRenderContext;
import mightypork.util.control.timing.Animator;
import mightypork.util.control.timing.Updateable;
import mightypork.util.ion.IonBinary;
import mightypork.util.ion.IonBundle;
import mightypork.util.ion.IonInput;
import mightypork.util.ion.IonOutput;


public final class Tile implements IonBinary {
	
	public static final short ION_MARK = 50;
	
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
	
	
	public Tile()
	{
	}
	
	
	public void render(TileRenderContext context)
	{
		model.render(context);
		
		if (hasItems()) {
			getItemRenderer().render(items.peek(), context);
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
			final IonBundle ib = new IonBundle();
			model.saveMetadata(this, ib);
			out.writeBundle(ib);
		}
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		id = in.readIntByte();
		
		// check model
		if (model == null || id != model.id) {
			model = Tiles.get(id);
		}
		
		if (model.hasDroppedItems()) {
			in.readSequence(items);
		}
		
		if (model.hasMetadata()) {
			model.loadMetadata(this, in.readBundle());
		}
	}
	
	
	public void updateLogic(double delta)
	{
		model.updateLogic(this, delta);
	}

	public void updateVisual(double delta)
	{
		model.updateVisual(this, delta);
		if (hasItems()) {
			getItemRenderer().updateVisual(delta);
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
		return model.hasDroppedItems() && !items.isEmpty();
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
}
