package mightypork.rogue.screens.gamescreen;


import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldPos;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.models.EntityMoveListener;
import mightypork.rogue.world.level.Level;
import mightypork.util.constraints.vect.Vect;
import mightypork.util.math.Polar;
import mightypork.util.math.Calc.Deg;
import mightypork.gamecore.control.events.KeyEvent;
import mightypork.gamecore.control.events.MouseButtonEvent;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.Keys;


public class WRBasicControls extends WorldRenderComponent implements KeyEvent.Listener, MouseButtonEvent.Listener, EntityMoveListener {
	
	private final PlayerControl pc;
	
	
	public WRBasicControls(World world) {
		super(world);
		pc = world.getPlayerControl();
		pc.addMoveListener(this);
	}
	
	
	private void handleHeldKey()
	{
		if (InputSystem.isKeyDown(Keys.LEFT)) {
			pc.walkWest();
		} else if (InputSystem.isKeyDown(Keys.RIGHT)) {
			pc.walkEast();
		} else if (InputSystem.isKeyDown(Keys.UP)) {
			pc.walkNorth();
		} else if (InputSystem.isKeyDown(Keys.DOWN)) {
			pc.walkSouth();
		}
		
		if(InputSystem.isMouseButtonDown(0)) {
			walkByMouse(InputSystem.getMousePos());
		}
	}
	
	
	@Override
	public void onStepFinished(Entity entity, World world, Level level)
	{
		handleHeldKey();
	}
	
	
	@Override
	public void onPathFinished(Entity entity, World world, Level level)
	{
		handleHeldKey();
	}
	
	
	@Override
	public void onPathInterrupted(Entity entity, World world, Level level)
	{
		handleHeldKey();
	}
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		if (!event.isDown()) return;
		
		walkByMouse(event.getPos());
	}
	
	
	private void walkByMouse(Vect mouse)
	{
		
		WorldPos clicked = toWorldPos(mouse);
		WorldPos plpos = pc.getPos();
		
		Polar p = Polar.fromCoord(clicked.x - plpos.x, clicked.y - plpos.y);
		
		int dir = Deg.round90(p.getAngleDeg()) / 90;
		
		switch (dir) {
			case 0:
				pc.walkEast();
				return;
				
			case 1:
				pc.walkSouth();
				return;
				
			case 2:
				pc.walkWest();
				return;
				
			case 3:
				pc.walkNorth();
				return;
		}
	}
	
	
	@Override
	public void receive(KeyEvent event)
	{
		handleHeldKey();
	}
	
}
