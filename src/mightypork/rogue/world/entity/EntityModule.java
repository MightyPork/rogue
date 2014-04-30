package mightypork.rogue.world.entity;


import java.io.IOException;
import java.util.Random;

import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.ion.IonObjBundled;


/**
 * Abstract entity module
 * 
 * @author MightyPork
 */
public abstract class EntityModule implements IonObjBundled, Updateable {
	
	protected final Entity entity;
	protected final Random rand = new Random();
	
	
	public EntityModule(Entity entity) {
		this.entity = entity;
	}
	
	public abstract boolean isModuleSaved();
	
	
	@Override
	@DefaultImpl
	public void load(IonBundle bundle) throws IOException
	{
	}
	
	
	@Override
	@DefaultImpl
	public void save(IonBundle bundle) throws IOException
	{
	}
	
	
	@Override
	@DefaultImpl
	public void update(double delta)
	{
	}
}
