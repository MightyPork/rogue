package mightypork.rogue.world.entity;


import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.timing.Updateable;
import mightypork.ion.IonBundle;
import mightypork.ion.IonObjBundled;


/**
 * Abstract entity module<br>
 * Modules make up an entity AI and behavior.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class EntityModule implements IonObjBundled, Updateable {
	
	protected final Entity entity;
	
	
	public EntityModule(Entity entity)
	{
		this.entity = entity;
	}
	
	
	/**
	 * @return whether the module should be saved into a world file
	 */
	public abstract boolean isModuleSaved();
	
	
	@Override
	@DefaultImpl
	public void load(IonBundle bundle)
	{
	}
	
	
	@Override
	@DefaultImpl
	public void save(IonBundle bundle)
	{
	}
	
	
	@Override
	@DefaultImpl
	public void update(double delta)
	{
	}
}
