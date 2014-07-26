package mightypork.rogue.world.tile.impl;


import java.io.IOException;

import mightypork.gamecore.graphics.textures.TxSheet;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;
import mightypork.rogue.world.tile.render.DoorTileRenderer;
import mightypork.utils.ion.IonInput;
import mightypork.utils.ion.IonOutput;


public abstract class TileBaseDoor extends TileSolid {
	
	private final DoorTileRenderer renderer;
	protected boolean locked = false;
	
	
	public TileBaseDoor(TileModel model, TxSheet locked, TxSheet closed, TxSheet open) {
		super(model);
		
		this.renderer = new DoorTileRenderer(this, locked, closed, open);
	}
	
	
	@Override
	protected TileRenderer makeRenderer()
	{
		return renderer;
	}
	
	
	@Override
	public boolean isWalkable()
	{
		return !locked;
	}
	
	
	@Override
	public TileType getType()
	{
		return TileType.DOOR;
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		super.load(in);
		locked = in.readBoolean();
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		super.save(out);
		out.writeBoolean(locked);
	}
	
	
	/**
	 * @return true if the door appears open
	 */
	public boolean isOpen()
	{
		return isOccupied();
	}
	
	
	/**
	 * @return true if the door is locked
	 */
	public boolean isLocked()
	{
		return locked;
	}
}
