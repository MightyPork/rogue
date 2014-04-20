package mightypork.rogue.world.structs;


import mightypork.rogue.world.map.LevelMap;
import mightypork.util.files.ion.templates.IonizableArrayList;


public class LevelList extends IonizableArrayList<LevelMap> {
	
	public static final short ION_MARK = 709;
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
}
