package mightypork.rogue.world.item.impl.active;


import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.PlayerFacade;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.ItemType;
import mightypork.rogue.world.item.render.QuadItemRenderer;


public class ItemHeartPiece extends Item {

	public ItemHeartPiece(ItemModel model)
	{
		super(model);
	}


	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(this, Res.txQuad("item.heart"));
	}


	@Override
	protected boolean isStackable()
	{
		return false;
	}


	@Override
	public int getAttackPoints()
	{
		return 0;
	}


	@Override
	public int getFoodPoints()
	{
		return 0;
	}


	@Override
	public ItemType getType()
	{
		return ItemType.ACTIVE;
	}


	@Override
	public int getMaxUses()
	{
		return 1;
	}


	@Override
	public boolean isDamageable()
	{
		return false;
	}


	@Override
	public String getVisualName()
	{
		return "Heart Piece";
	}


	@Override
	public boolean pickUp(PlayerFacade pl)
	{
		pl.setHealthMax(pl.getHealthMax() + 2); // two points / heart
		pl.setHealth(pl.getHealthMax());

		pl.getWorld().getConsole().msgHeartPiece();

		return true;
	}

}
