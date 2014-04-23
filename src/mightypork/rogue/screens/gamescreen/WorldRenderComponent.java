package mightypork.rogue.screens.gamescreen;


import mightypork.gamecore.gui.components.InputComponent;
import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldPos;
import mightypork.rogue.world.WorldRenderer;
import mightypork.util.constraints.vect.Vect;


public class WorldRenderComponent extends InputComponent {
	
	protected final WorldRenderer worldRenderer;
	protected final World world;
	
	
	public WorldRenderComponent(World world) {
		this.world = world;
		this.worldRenderer = new WorldRenderer(world, this, 8, 6, 72);
	}
	
	
	@Override
	protected void renderComponent()
	{
		worldRenderer.render();
	}
	
	
	@Override
	public void updateLayout()
	{
		worldRenderer.poll(); // update sizing
	}
	
	
	/**
	 * Get tile coord at a screen position
	 */
	public WorldPos toWorldPos(Vect pos)
	{
		return worldRenderer.getClickedTile(pos);
	}
}
