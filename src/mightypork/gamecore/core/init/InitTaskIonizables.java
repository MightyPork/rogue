package mightypork.gamecore.core.init;


import java.io.IOException;

import mightypork.gamecore.core.InitTask;
import mightypork.utils.ion.Ion;
import mightypork.utils.ion.IonInput;
import mightypork.utils.ion.IonOutput;
import mightypork.utils.ion.IonizerBinary;
import mightypork.utils.math.algo.Coord;
import mightypork.utils.math.algo.Move;


/**
 * Register extra ionizables added by the game library (non-native ION types).<br>
 * This initializer can be called anywhere in the initialization sequence.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class InitTaskIonizables extends InitTask {
	
	@Override
	public void run()
	{
		Ion.registerIndirect(255, new IonizerBinary<Coord>() {
			
			@Override
			public void save(Coord object, IonOutput out) throws IOException
			{
				out.writeInt(object.x);
				out.writeInt(object.y);
			}
			
			
			@Override
			public Coord load(IonInput in) throws IOException
			{
				final int x = in.readInt();
				final int y = in.readInt();
				return new Coord(x, y);
			}
			
		});
		
		Ion.registerIndirect(254, new IonizerBinary<Move>() {
			
			@Override
			public void save(Move object, IonOutput out) throws IOException
			{
				out.writeInt(object.x());
				out.writeInt(object.y());
			}
			
			
			@Override
			public Move load(IonInput in) throws IOException
			{
				final int x = in.readInt();
				final int y = in.readInt();
				return new Move(x, y);
			}
			
		});
	}
	
	
	@Override
	public String getName()
	{
		return "ion";
	}
	
}
