package mightypork.rogue.world.tile.impl;


import java.io.IOException;

import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileType;
import mightypork.utils.ion.IonInput;
import mightypork.utils.ion.IonOutput;


public abstract class TileBaseChest extends TileWithItems {
	
	public static final double POST_OPEN_DURA = 0.3;
	public boolean opened = false;
	public boolean removed = false;
	public double timeSinceOpen = 10000;
	
	private int clicks = 1;
	
	
	public TileBaseChest(TileModel model) {
		super(model);
	}
	
	
	@Override
	public TileType getType()
	{
		return TileType.FLOOR;
	}
	
	
	@Override
	public boolean isWalkable()
	{
		return removed;
	}
	
	
	@Override
	protected boolean shouldRenderItems()
	{
		return removed;
	}
	
	
	@Override
	public boolean onClick()
	{
		if (opened & removed) return false;
		
		if (clicks > 0) {
			clicks--;
			
			if (clicks == 0) {
				opened = true;
				timeSinceOpen = 0;
				getWorld().getConsole().msgOpenChest();
			}
			
		} else {
			if (opened && !removed && timeSinceOpen > POST_OPEN_DURA) {
				removed = true;
				clicks--;
			}
		}
		
		return true;
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		super.save(out);
		out.writeIntByte(clicks);
		out.writeBoolean(opened);
		out.writeBoolean(removed);
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		super.load(in);
		clicks = in.readIntByte();
		opened = in.readBoolean();
		removed = in.readBoolean();
	}
	
	
	@Override
	public void updateTile(double delta)
	{
		super.updateTile(delta);
		if (opened && timeSinceOpen < 10000) timeSinceOpen += delta;
	}
}
