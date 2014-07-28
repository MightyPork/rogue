package mightypork.rogue.world.gen;


import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.Level;
import mightypork.utils.math.Range;
import mightypork.utils.math.algo.Coord;


public class LevelBuilder {

	public static enum BuildOrder
	{
		FIRST, MIDDLE, LAST
	}

	private class RoomEntry {

		int count;
		RoomBuilder room;
		boolean important;


		public RoomEntry(RoomBuilder room, int count, boolean important)
		{
			this.count = count;
			this.room = room;
			this.important = important;
		}
	}

	private class ItemEntry {

		Item item;
		boolean important;


		public ItemEntry(Item item, boolean important)
		{
			this.item = item;
			this.important = important;
		}
	}

	private class EntityEntry {

		Entity entity;
		boolean important;


		public EntityEntry(Entity item, boolean important)
		{
			this.entity = item;
			this.important = important;
		}
	}

	private final ScratchMap map;
	private final Random rand;
	private boolean built;

	private final LinkedList<RoomEntry> roomsFirst = new LinkedList<>();
	private final LinkedList<RoomEntry> roomsMiddle = new LinkedList<>();
	private final LinkedList<RoomEntry> roomsLast = new LinkedList<>();

	private final LinkedList<ItemEntry> items = new LinkedList<>();
	private final LinkedList<EntityEntry> entities = new LinkedList<>();


	/**
	 * make a new level builder instance.
	 *
	 * @param max_size max map size (square side - tiles)
	 * @param theme tiles theme
	 * @param seed level seed
	 */
	public LevelBuilder(int max_size, MapTheme theme, long seed)
	{
		this.rand = new Random(seed);
		this.map = new ScratchMap(max_size, theme, rand);
	}


	/**
	 * Add a single room to the room buffer.
	 *
	 * @param room room builder
	 * @param order build order
	 * @param important try harder and throw error on fail
	 */
	public void addRoom(RoomBuilder room, BuildOrder order, boolean important)
	{
		addRoom(room, Range.make(1, 1), order, important);
	}


	/**
	 * Add multiple rooms of the type to the room buffer.
	 *
	 * @param room room builder
	 * @param count number of rooms to build
	 * @param order build order
	 * @param important try harder and throw error on fail
	 */
	public void addRoom(RoomBuilder room, Range count, BuildOrder order, boolean important)
	{
		final List<RoomEntry> list;

		switch (order) {
			case FIRST:
				list = roomsFirst;
				break;

			default:
			case MIDDLE:
				list = roomsMiddle;
				break;

			case LAST:
				list = roomsLast;
				break;
		}

		list.add(new RoomEntry(room, count.randInt(rand), important));
	}


	private void buildRooms(LinkedList<RoomEntry> list)
	{
		while (!list.isEmpty()) {

			Collections.shuffle(list, rand);

			for (final Iterator<RoomEntry> iter = list.iterator(); iter.hasNext();) {
				final RoomEntry rge = iter.next();

				map.addRoom(rge.room, rge.important);

				if ((--rge.count) <= 0) {
					iter.remove();
				}
			}
		}
	}


	private void buildCorridors() throws WorldGenError
	{
		map.buildCorridors();
	}


	private void buildEntities()
	{
		for (final EntityEntry entry : entities) {
			final int tries = entry.important ? 200 : 50;
			final boolean success = map.addEntityInMap(entry.entity, tries);

			if (entry.important && !success) {
				throw new WorldGenError("Could not place an important entity: " + entry.entity);
			}
		}
	}


	private void buildItems()
	{
		for (final ItemEntry entry : items) {
			final int tries = entry.important ? 200 : 50;
			final boolean success = map.addItemInMap(entry.item, tries);

			if (entry.important && !success) {
				throw new WorldGenError("Could not place an important item: " + entry.item);
			}
		}
	}


	private void writeToMap()
	{
		buildRooms(roomsFirst);
		buildRooms(roomsMiddle);
		buildRooms(roomsLast);
		buildCorridors();

		map.fixGlitches();

		buildItems();
		buildEntities();
	}


	/**
	 * Write to a new level instance.
	 *
	 * @param world level's world
	 * @return the level
	 * @throws WorldGenError on error in generation
	 */
	public Level build(World world) throws WorldGenError
	{
		if (built) {
			throw new WorldGenError("Level already built.");
		}
		built = true;

		writeToMap();

		final Coord size = map.getNeededSize();
		final Level lvl = new Level(size.x, size.y);
		lvl.setWorld(world); // important for creating entities

		map.writeToLevel(lvl);

		return lvl;
	}


	/**
	 * Add an item to be added to the level when tiles are built.
	 *
	 * @param item item to add
	 * @param important try harder and throw error on fail
	 * @throws WorldGenError on fail
	 */
	public void addItem(Item item, boolean important) throws WorldGenError
	{
		items.add(new ItemEntry(item, important));
	}


	/**
	 * Add an entity to be added to the level when tiles are built.<br>
	 * It's EID will be assigned during writing to level.
	 *
	 * @param entity entity to add
	 * @param important try harder and throw error on fail
	 * @throws WorldGenError on fail
	 */
	public void addEntity(Entity entity, boolean important) throws WorldGenError
	{
		entities.add(new EntityEntry(entity, important));
	}

}
