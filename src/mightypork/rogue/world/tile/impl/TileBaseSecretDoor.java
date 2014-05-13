package mightypork.rogue.world.tile.impl;


import java.io.IOException;

import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonOutput;
import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileType;


public abstract class TileBaseSecretDoor extends TileBaseDoor {
	
	private int clicks = Calc.randInt(2, 3);
	
	
	public TileBaseSecretDoor(TileModel model, TxSheet secret, TxSheet closed, TxSheet open)
	{
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
		if (locked) return TileType.WALL.getMapColor();
		return RGB.PINK;
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
