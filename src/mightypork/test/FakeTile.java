package mightypork.test;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.rogue.world.tile.TileData;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderContext;
import mightypork.util.files.ion.Ion;


class FakeTileData extends TileData {
	
	int number;
	String name;
}


public class FakeTile extends TileModel {
	
	public FakeTile(int id) {
		super(id);
	}
	
	
	@Override
	public boolean isPotentiallyWalkable()
	{
		return true;
	}
	
	
	@Override
	public boolean isWalkable(TileData data)
	{
		return true;
	}
	
	
	@Override
	public TileData createData()
	{
		return new FakeTileData() {
			
			{
				number = 255;
				name = "ABC";
			}
		};
	}
	
	
	@Override
	public void load(TileData data, InputStream in) throws IOException
	{
		((FakeTileData) data).name = Ion.readString(in);
		((FakeTileData) data).number = Ion.readInt(in);
	}
	
	
	@Override
	public void save(TileData data, OutputStream out) throws IOException
	{
		Ion.writeString(out, ((FakeTileData) data).name);
		Ion.writeInt(out, ((FakeTileData) data).number);
	}
	
	
	@Override
	public void render(TileData data, TileRenderContext context)
	{
		//
	}
	
	
	@Override
	public void update(TileData item, double delta)
	{
		//
	}
	
}
