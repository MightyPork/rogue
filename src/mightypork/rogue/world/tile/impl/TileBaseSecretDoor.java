package mightypork.rogue.world.tile.impl;


import java.io.IOException;

import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.rogue.world.tile.TileColors;
import mightypork.rogue.world.tile.TileModel;
import mightypork.utils.ion.IonInput;
import mightypork.utils.ion.IonOutput;
import mightypork.utils.math.color.Color;


public abstract class TileBaseSecretDoor extends TileBaseDoor {
	
	private int clicks = 2;
	
	
	public TileBaseSecretDoor(TileModel model, TxSheet secret, TxSheet closed, TxSheet open) {
		super(model, secret, closed, open);
	}
	
	
	@Override
	public boolean onClick()
	{
		if (!locked) return false;
		
		if (clicks > 0) clicks--;
		
		if (clicks == 0) {
			locked = false;
			getWorld().getConsole().msgDiscoverSecretDoor();
		}
		
		return true;
	}
	
	
	@Override
	public Color getMapColor()
	{
		if (locked) return TileColors.SECRET_DOOR_HIDDEN;
		return TileColors.SECRET_DOOR_REVEALED;
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		super.save(out);
		out.writeIntByte(clicks);
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		super.load(in);
		clicks = in.readIntByte();
	}
}
