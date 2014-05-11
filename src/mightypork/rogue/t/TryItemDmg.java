package mightypork.rogue.t;


import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.Items;


public class TryItemDmg {
	
	public static void main(String[] args)
	{
		final Item itm = Items.CLUB.createItemDamaged(80);
		System.out.println(itm.getMaxUses() + " - remaining: " + itm.getRemainingUses());
	}
	
}
