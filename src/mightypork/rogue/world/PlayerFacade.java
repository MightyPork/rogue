package mightypork.rogue.world;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemType;
import mightypork.rogue.world.level.Level;
import mightypork.utils.math.algo.Coord;
import mightypork.utils.math.algo.Move;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Convenient access to player-related methods and data in world.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class PlayerFacade {

	/** a world */
	private final World world;


	/**
	 * @return true if can go up
	 */
	public boolean canAscend()
	{
		return world.playerData.getLevelNumber() > 0;
	}


	/**
	 * @param world
	 */
	PlayerFacade(World world)
	{
		this.world = world;
	}


	/**
	 * @return true if can go down
	 */
	public boolean canDescend()
	{
		return world.playerData.getLevelNumber() < world.levels.size() - 1;
	}


	/**
	 * Go one level down if applicable
	 */
	public void descend()
	{
		if (!canDescend()) return;

		final int lvl_num = getLevelNumber();
		getLevel().removeEntity(world.playerEntity);

		world.playerData.setLevelNumber(lvl_num + 1);

		getLevel().forceFreeTile(getLevel().getEnterPoint());
		getLevel().addEntity(world.playerEntity, getLevel().getEnterPoint());
		getLevel().explore(getCoord());

		world.console.msgEnterFloor(getLevelNumber());
	}


	/**
	 * Go one level up if applicable
	 */
	public void ascend()
	{
		if (!canAscend()) return;

		final int lvl_num = getLevelNumber();
		getLevel().removeEntity(world.playerEntity);

		world.playerData.setLevelNumber(lvl_num - 1);

		getLevel().forceFreeTile(getLevel().getExitPoint());
		getLevel().addEntity(world.playerEntity, getLevel().getExitPoint());
		getLevel().explore(getCoord());

		world.console.msgEnterFloor(getLevelNumber());
	}


	/**
	 * @return current level number, zero based.
	 */
	public int getLevelNumber()
	{
		return world.playerData.getLevelNumber();
	}


	/**
	 * @return current level
	 */
	public Level getLevel()
	{
		return world.levels.get(world.playerData.getLevelNumber());
	}


	/**
	 * @return entity ID
	 */
	public int getEID()
	{
		return world.playerData.getEID();
	}


	/**
	 * @return entity coordinate in level
	 */
	public Coord getCoord()
	{
		return world.playerEntity.getCoord();
	}


	/**
	 * @return entity visual pos in level
	 */
	public Vect getVisualPos()
	{
		return world.playerEntity.pos.getVisualPos();
	}


	/**
	 * Find path to
	 *
	 * @param pos
	 */
	public void navigateTo(Coord pos)
	{
		world.playerEntity.pos.navigateTo(pos);
	}


	/**
	 * Discard steps in buffer
	 */
	public void cancelPath()
	{
		world.playerEntity.pos.cancelPath();
	}


	public void addPathStep(Move step)
	{
		world.playerEntity.pos.addStep(step);
	}


	public boolean isMoving()
	{
		return world.playerEntity.pos.isMoving();
	}


	public double getMoveProgress()
	{
		return world.playerEntity.pos.getProgress();
	}


	public boolean isDead()
	{
		return world.playerEntity.isDead();
	}


	public int getHealth()
	{
		return world.playerEntity.health.getHealth();
	}


	public int getHealthMax()
	{
		return world.playerEntity.health.getHealthMax();
	}


	public Inventory getInventory()
	{
		return world.playerData.getInventory();
	}


	public Entity getEntity()
	{
		return world.playerEntity;
	}


	public int getAttackStrength()
	{
		final Item weapon = world.playerData.getSelectedWeapon();

		if (weapon == null) return PlayerData.BARE_ATTACK;

		return PlayerData.BARE_ATTACK + weapon.getAttackPoints();
	}


	/**
	 * Eat food.
	 *
	 * @param itm food item
	 * @return if something was eaten
	 */
	public boolean eatFood(Item itm)
	{
		if (itm == null || itm.isEmpty() || itm.getType() != ItemType.FOOD) return false;

		if (getHealth() < getHealthMax()) {

			world.playerEntity.health.addHealth(itm.getFoodPoints());
			itm.consume();

			world.console.msgEat(itm);

			return true;
		}

		return false;
	}


	public void selectWeapon(int selected)
	{
		world.playerData.selectWeapon(selected);
	}


	public Item getSelectedWeapon()
	{
		return world.playerData.getSelectedWeapon();
	}


	public int getSelectedWeaponIndex()
	{
		return world.playerData.getSelectedWeaponIndex();
	}


	public void tryToEatSomeFood()
	{
		final List<Item> foods = new ArrayList<>();
		for (int i = 0; i < getInventory().getSize(); i++) {
			final Item itm = getInventory().getItem(i);
			if (itm != null && itm.getType() == ItemType.FOOD) {
				foods.add(itm);
			}
		}

		// sort from smallest to biggest foods
		Collections.sort(foods, new Comparator<Item>() {

			@Override
			public int compare(Item o1, Item o2)
			{
				return (o1.getFoodPoints() - o2.getFoodPoints());
			}
		});

		for (final Item itm : foods) {
			if (eatFood(itm)) {
				getInventory().clean();
				return;
			}
		}

		if (getHealth() < getHealthMax()) {
			world.console.msgNoMoreFood();
		} else {
			world.console.msgNotHungry();
		}
	}


	public void attack(Entity prey)
	{
		final int attackPoints = getAttackStrength();

		prey.receiveAttack(world.getPlayer().getEntity(), attackPoints);

		if (prey.isDead()) {
			world.console.msgKill(prey);
		}

		final Item wpn = getSelectedWeapon();

		if (wpn != null) {
			wpn.use();
			if (wpn.isEmpty()) {
				world.console.msgWeaponBreak(wpn);

				getInventory().clean();
				selectWeapon(-1);

				pickBestWeaponIfNoneSelected();
			}
		}

	}


	private void pickBestWeaponIfNoneSelected()
	{
		if (getSelectedWeapon() != null) return;

		final List<Item> wpns = new ArrayList<>();
		for (int i = 0; i < getInventory().getSize(); i++) {
			final Item itm = getInventory().getItem(i);
			if (itm != null && itm.getType() == ItemType.WEAPON) {
				wpns.add(itm);
			}
		}

		// sort from smallest to biggest foods
		Collections.sort(wpns, new Comparator<Item>() {

			@Override
			public int compare(Item o1, Item o2)
			{
				return (o2.getAttackPoints() - o1.getAttackPoints());
			}
		});

		for (final Item itm : wpns) {
			for (int i = 0; i < getInventory().getSize(); i++) {
				final Item itm2 = getInventory().getItem(i);
				if (itm2 == itm) {
					selectWeapon(i);
					break;
				}
			}
			break; // just one cycle
		}

		world.console.msgEquipWeapon(getSelectedWeapon());
	}


	public boolean addItem(Item item)
	{
		if (!getInventory().addItem(item)) {

			world.console.msgCannotPick();

			return false;
		}

		world.console.msgPick(item);

		if (item.getType() == ItemType.WEAPON) {
			if (getSelectedWeapon() != null) {
				if (item.getAttackPoints() > getSelectedWeapon().getAttackPoints()) {
					selectWeapon(-1); // unselect to grab the best one
				}
			}

			pickBestWeaponIfNoneSelected();
		}

		return true;
	}


	public void setHealth(int health)
	{
		world.playerEntity.health.setHealth(health);
	}


	public void setHealthMax(int health)
	{
		world.playerEntity.health.setHealthMax(health);
	}


	public World getWorld()
	{
		return world;
	}


	public boolean canGoTo(Move side)
	{
		return getEntity().pos.canGoTo(side);
	}


	public void dropItem(int itemIndex)
	{
		final Item itm = getInventory().getItem(itemIndex);
		if (itm != null && !itm.isEmpty()) {

			final Item piece = itm.split(1);
			getInventory().clean();

			Coord dropPos;
			if (world.playerEntity.pos.isMoving()) {
				dropPos = world.playerEntity.pos.getLastPos();
			} else {
				dropPos = getCoord();
			}

			if (!getLevel().getTile(dropPos).dropItem(piece)) {
				getInventory().addItem(piece); // add back
			} else {
				world.getConsole().msgDroppedItem(piece);
			}
		}
	}
}
