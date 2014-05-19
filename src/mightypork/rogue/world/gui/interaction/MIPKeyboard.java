package mightypork.rogue.world.gui.interaction;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mightypork.gamecore.Config;
import mightypork.gamecore.eventbus.clients.DelegatingClient;
import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.KeyStroke.Edge;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.util.math.algo.Move;
import mightypork.gamecore.util.math.algo.Moves;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.world.entity.impl.PlayerEntity;
import mightypork.rogue.world.events.PlayerStepEndListener;
import mightypork.rogue.world.gui.MapView;


public class MIPKeyboard extends MapInteractionPlugin implements DelegatingClient, PlayerStepEndListener, Updateable {
	
	//@formatter:off
	private static final KeyStroke[] keys = {
		Config.getKey("game.walk.left"),
		Config.getKey("game.walk.right"),
		Config.getKey("game.walk.up"),
		Config.getKey("game.walk.down")
	};
	
	private static final Move[] sides = { Moves.W, Moves.E, Moves.N, Moves.S };
	//@formatter:on
	
	private final KeyBindingPool kbp = new KeyBindingPool();
	private final List<Object> clients = new ArrayList<>();
	
	{
		clients.add(kbp);
	}
	
	
	@Override
	public boolean doesDelegate()
	{
		return true;
	}
	
	
	@Override
	public Collection<?> getChildClients()
	{
		return clients;
	}
	
	
	public MIPKeyboard(MapView mapView)
	{
		super(mapView);
		for (int i = 0; i < 4; i++) {
			
			final int j = i;
			kbp.bindKey(keys[i], Edge.RISING, new Runnable() {
				
				@Override
				public void run()
				{
					clickSide(sides[j]);
				}
			});
		}
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
	
	
//	@Override
//	public void receive(KeyEvent evt)
//	{
//		
//		if (evt.isDown() || mapView.plc.getPlayer().isMoving()) return; // not interested
//			
//		if (InputSystem.getActiveModKeys() != Keys.MOD_NONE) return;
//		
//		for (int i = 0; i < 4; i++) {
//			if (evt.getKey() == keys[i].getKey()) {
//				mapView.plc.clickTile(sides[i]);
//			}
//		}
//	}
	
	private void clickSide(Move side)
	{
		if (isImmobile()) {
			return;
		}
		mapView.plc.clickTile(side);
	}
	
	
	private boolean walkByKey()
	{
		if (isImmobile()) return false;
		
		if (mapView.plc.getPlayer().getMoveProgress() < 0.8) return false;
		
		if (InputSystem.getActiveModKeys() != Keys.MOD_NONE) return false;
		
		for (int i = 0; i < 4; i++) {
			if (keys[i].isDown()) {
				
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
