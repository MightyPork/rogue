package mightypork.rogue.world.structs;


import mightypork.rogue.world.map.Level;
import mightypork.util.files.ion.templates.IonizableArrayList;


public class LevelList extends IonizableArrayList<Level> {
	
	public static final short ION_MARK = 709;
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
}
