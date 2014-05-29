package mightypork.rogue.world.tile.render;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.impl.TileBaseDoor;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.timing.TimedTask;


public class DoorTileRenderer extends TileRenderer {
	
	private final TxSheet locked;
	private final TxSheet closed;
	private final TxSheet open;
	
	private boolean visuallyOpen = false;
	
	private final TimedTask closeTask = new TimedTask() {
		
		@Override
		public void run()
		{
			visuallyOpen = ((TileBaseDoor) tile).isOpen();
		}
	};
	
	
	public DoorTileRenderer(TileBaseDoor doorTile, TxSheet locked, TxSheet closed, TxSheet open)
	{
		super(doorTile);
		
		this.locked = locked;
		this.closed = closed;
		this.open = open;
	}
	
	
	@Override
	public void renderTile(TileRenderContext context)
	{
		final Rect rect = context.getRect();
		
		if (!visuallyOpen && ((TileBaseDoor) tile).isOpen()) visuallyOpen = true;
		
		if (visuallyOpen && !((TileBaseDoor) tile).isOpen()) {
			if (!closeTask.isRunning()) closeTask.start(0.4);
		}
		
		if (visuallyOpen) {
			Render.quadTextured(rect, open.getRandomQuad(context.getTileNoise()));
		} else {
			final TxSheet sheet = (((TileBaseDoor) tile).isLocked() ? locked : closed);
			Render.quadTextured(rect, sheet.getRandomQuad(context.getTileNoise()));
		}
	}
	
	
	@Override
	public void update(double delta)
	{
		closeTask.update(delta);
	}
	
}
