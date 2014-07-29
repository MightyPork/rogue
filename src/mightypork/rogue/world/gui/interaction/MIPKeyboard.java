package mightypork.rogue.world.gui.interaction;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mightypork.gamecore.core.App;
import mightypork.gamecore.core.config.Config;
import mightypork.gamecore.input.KeyBindingPool;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.input.Trigger;
import mightypork.rogue.world.entity.impl.EntityPlayer;
import mightypork.rogue.world.events.PlayerStepEndListener;
import mightypork.rogue.world.gui.MapView;
import mightypork.utils.eventbus.clients.DelegatingClient;
import mightypork.utils.interfaces.Updateable;
import mightypork.utils.math.algo.Move;
import mightypork.utils.math.algo.Moves;
import mightypork.utils.math.constraints.vect.Vect;


public class MIPKeyboard extends MapInteractionPlugin implements DelegatingClient, PlayerStepEndListener, Updateable {
	
	// FIXME cannot be static.
	
	private static final Move[] sides = { Moves.W, Moves.E, Moves.N, Moves.S };
	
	private final KeyBindingPool kbp = new KeyBindingPool();
	private final List<Object> clients = new ArrayList<>();


	{
		clients.add(kbp);
	}
	
	
	private KeyStroke[] getKeys()
	{
		//@formatter:off
		
		final Config cfg = App.cfg();
		
		return new KeyStroke[] {
				cfg.getKeyStroke("game.walk.left"),
				cfg.getKeyStroke("game.walk.right"),
				cfg.getKeyStroke("game.walk.up"),
				cfg.getKeyStroke("game.walk.down")
		};
		//@formatter:on
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

		final KeyStroke[] keys = getKeys();
		
		// bind keys
		for (int i = 0; i < 4; i++) {
			
			final int j = i;
			kbp.bindKey(keys[i], Trigger.RISING, new Runnable() {
				
				@Override
				public void run()
				{
					clickSide(sides[j]);
				}
			});
		}
	}
	
	
	@Override
	public void onStepFinished(EntityPlayer player)
	{
		walkByKey();
	}
	
	
	@Override
	public boolean onClick(Vect mouse, int button, boolean down)
	{
		return false;
	}
	
	
	private void clickSide(Move side)
	{
		if (isImmobile() || getPlayer().isMoving()) return;
		
		mapView.plc.clickTile(side);
	}
	
	
	private boolean walkByKey()
	{
		if (isImmobile()) return false;
		
		if (mapView.plc.getPlayer().getMoveProgress() < 0.8) return false;
		
		if (Keys.getActiveMods() != Keys.MOD_NONE) return false;

		final KeyStroke[] keys = getKeys();
		
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
