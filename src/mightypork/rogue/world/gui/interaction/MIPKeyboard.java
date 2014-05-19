package mightypork.rogue.world.gui.interaction;


import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.input.events.KeyEvent;
import mightypork.gamecore.input.events.KeyEventHandler;
import mightypork.gamecore.util.math.algo.Move;
import mightypork.gamecore.util.math.algo.Moves;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.entity.impl.PlayerEntity;
import mightypork.rogue.world.events.PlayerStepEndListener;
import mightypork.rogue.world.gui.MapView;


public class MIPKeyboard extends MapInteractionPlugin implements PlayerStepEndListener, KeyEventHandler, Updateable {
	
	private static final int[] keys = { Keys.LEFT, Keys.RIGHT, Keys.UP, Keys.DOWN };
	private static final Move[] sides = { Moves.W, Moves.E, Moves.N, Moves.S };
	
	
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
		if (isImmobile()) {
			return;
		}
		
		if (evt.isDown() || mapView.plc.getPlayer().isMoving()) return; // not interested
		
		if (InputSystem.getActiveModKeys() != Keys.MOD_NONE) return;
		
		for (int i = 0; i < 4; i++) {
			if (evt.getKey() == keys[i]) {
				mapView.plc.clickTile(sides[i]);
			}
		}
	}
	
	
	private boolean walkByKey()
	{
		if (isImmobile()) return false;
		
		if (mapView.plc.getPlayer().getMoveProgress() < 0.8) return false;
		
		
		if (InputSystem.getActiveModKeys() != Keys.MOD_NONE) return false;
		
		
		for (int i = 0; i < 4; i++) {
			if (InputSystem.isKeyDown(keys[i])) {
				
				final Move side = sides[i];
				if (mapView.plc.canGo(side)) {
					mapView.plc.go(side);
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
