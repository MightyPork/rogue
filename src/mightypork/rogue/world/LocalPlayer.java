package mightypork.rogue.world;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.files.ion.IonConstructor;
import mightypork.util.files.ion.Ionizable;


/**
 * Player info
 * 
 * @author MightyPork
 */
public class LocalPlayer implements Ionizable, MapObserver {
	
	public static final short ION_MARK = 708;
	
	public WorldPos position = new WorldPos();
	
	
	@IonConstructor
	public LocalPlayer()
	{
	}
	
	
	public LocalPlayer(int x, int y, int floor)
	{
		this.position.setTo(x, y, floor);
	}
	
	
	public LocalPlayer(WorldPos pos)
	{
		this.position = pos;
	}
	
	
	@Override
	public void load(InputStream in) throws IOException
	{
		IonBundle ib = (IonBundle) Ion.readObject(in);
		
		position = ib.get("pos", position);
	}
	
	
	@Override
	public void save(OutputStream out) throws IOException
	{
		IonBundle ib = new IonBundle();
		
		ib.put("pos", position);
		
		Ion.writeObject(out, ib);
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
	
	@Override
	public WorldPos getPosition()
	{
		return position;
	}
	
	
	@Override
	public int getViewRange()
	{
		return 15;
	}
}
