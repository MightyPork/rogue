package mightypork.rogue.world.tile.render;


import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.textures.TxQuad;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.impl.TileBaseChest;


public class ChestRenderer extends TileRenderer {

	private final TxQuad txqFloor;
	private final TxQuad txqChest;

	private final TileBaseChest chestTile;
	private final TxQuad txqChestOpen;


	public ChestRenderer(TileBaseChest tile, TxQuad txq, TxQuad chest, TxQuad chestOpen)
	{
		super(tile);

		this.chestTile = tile;

		this.txqFloor = txq;
		this.txqChest = chest;
		this.txqChestOpen = chestOpen;
	}


	@Override
	public void renderTile(TileRenderContext context)
	{
		App.gfx().quad(context.getRect(), txqFloor);
	}


	@Override
	public void renderExtra(TileRenderContext context)
	{
		if (!chestTile.opened) {
			App.gfx().quad(context.getRect(), txqChest);
		} else {
			if (!chestTile.removed) {
				App.gfx().quad(context.getRect(), txqChestOpen);
			}
		}
	}
}
