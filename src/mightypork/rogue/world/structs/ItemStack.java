package mightypork.rogue.world.structs;


import mightypork.rogue.world.item.Item;
import mightypork.util.files.ion.templates.IonizableStack;


public class ItemStack extends IonizableStack<Item> {
	
	private static final short ION_MARK = 710;
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
}
