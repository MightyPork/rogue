package mightypork.rogue.world.entity;


import mightypork.utils.annotations.Stub;
import mightypork.utils.interfaces.Updateable;
import mightypork.utils.ion.IonBundled;
import mightypork.utils.ion.IonDataBundle;


/**
 * Abstract entity module<br>
 * Modules make up an entity AI and behavior.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class EntityModule implements IonBundled, Updateable {

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
	@Stub
	public void load(IonDataBundle bundle)
	{
	}


	@Override
	@Stub
	public void save(IonDataBundle bundle)
	{
	}


	@Override
	@Stub
	public void update(double delta)
	{
	}
}
