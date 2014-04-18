package mightypork.rogue.world.item;


import mightypork.rogue.world.EntityModel;
import mightypork.util.constraints.rect.proxy.RectBound;


/**
 * An item model
 * 
 * @author MightyPork
 */
public abstract class ItemModel extends EntityModel<ItemData, RectBound> {
	
	public ItemModel(int id) {
		super(id);
		Items.register(id, this);
	}
}
