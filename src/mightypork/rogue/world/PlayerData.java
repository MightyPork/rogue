package mightypork.rogue.world;


import mightypork.rogue.world.item.Item;
import mightypork.utils.ion.IonBundled;
import mightypork.utils.ion.IonDataBundle;


/**
 * Player information stored in world.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class PlayerData implements IonBundled {
	
	/** Player inventory size */
	private static final int INV_SIZE = 8;
	
	/** Attack str with bare hands */
	public static final int BARE_ATTACK = 1;
	
	
	private int eid = -1; // marks not initialized
	private int level;
	
	private Inventory inventory = new Inventory(INV_SIZE);
	
	private int selectedWeapon = -1;
	
	
	@Override
	public void load(IonDataBundle bundle)
	{
		eid = bundle.get("eid", -1);
		level = bundle.get("floor", -1);
		selectedWeapon = bundle.get("weapon", -1);
		
		inventory = bundle.get("inv", inventory);
	}
	
	
	@Override
	public void save(IonDataBundle bundle)
	{
		bundle.put("eid", eid);
		bundle.put("floor", level);
		bundle.put("weapon", selectedWeapon);
		bundle.put("inv", inventory);
	}
	
	
	public void setEID(int eid)
	{
		if (isInitialized()) throw new RuntimeException("Cannot change player EID.");
		this.eid = eid;
	}
	
	
	public void setLevelNumber(int level)
	{
		this.level = level;
	}
	
	
	public int getEID()
	{
		return eid;
	}
	
	
	public int getLevelNumber()
	{
		return level;
	}
	
	
	public boolean isInitialized()
	{
		return eid != -1;
	}
	
	
	public Inventory getInventory()
	{
		return inventory;
	}
	
	
	public int getSelectedWeaponIndex()
	{
		return selectedWeapon;
	}
	
	
	public boolean hasWeaponSelected()
	{
		return !(selectedWeapon < 0 || selectedWeapon >= getInventory().getSize());
	}
	
	
	public Item getSelectedWeapon()
	{
		if (!hasWeaponSelected()) return null;
		return inventory.getItem(selectedWeapon);
	}
	
	
	public void selectWeapon(int selectedWeapon)
	{
		this.selectedWeapon = selectedWeapon;
		
		if (!hasWeaponSelected()) selectedWeapon = -1; // normalize
	}
	
}
