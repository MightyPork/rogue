package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.input.events.KeyEvent;
import mightypork.gamecore.input.events.KeyListener;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.gamecore.util.math.algo.Step;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.entity.entities.PlayerEntity;
import mightypork.rogue.world.events.PlayerStepEndListener;
import mightypork.rogue.world.gui.MapView;


public class MIPKeyboard extends MapInteractionPlugin implements PlayerStepEndListener, KeyListener, Updateable {
	
	private static final int[] keys = { Keys.LEFT, Keys.RIGHT, Keys.UP, Keys.DOWN };
	private static final Step[] sides = { Sides.W, Sides.E, Sides.N, Sides.S };
	
	
	public MIPKeyboard(MapView mapView)
	{
		super(mapView);
	}
	
	
	@Override
	public void onStepFinished(PlayerEntity player)
	{
		walkByKey();
	}
	
	
	@Override
	public boolean onClick(Vect mouse, int button, boolean down)
	{
		return false;
	}
	
	
	@Override
	public void receive(KeyEvent evt)
	{
		if (evt.isDown() || mapView.playerControl.getPlayerEntity().pos.isMoving()) return; // not interested
		
		for (int i = 0; i < 4; i++) {
			if (evt.getKey() == keys[i]) {
				mapView.playerControl.clickTile(sides[i]);
			}
		}
	}
	
	
	private boolean walkByKey()
	{
		if (mapView.playerControl.getPlayerEntity().pos.getProgress()<0.8) return false;
		
		for (int i = 0; i < 4; i++) {
			if (InputSystem.isKeyDown(keys[i])) {
				
				final Step side = sides[i];
				if (mapView.playerControl.canGo(side)) {
					mapView.playerControl.go(side);
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	
	@Override
	public void update(double delta)
	{
		walkByKey();
	}
}
