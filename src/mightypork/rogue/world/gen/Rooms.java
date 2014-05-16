package mightypork.rogue.world.gen;


import mightypork.rogue.world.gen.rooms.*;
import mightypork.rogue.world.item.Item;


public class Rooms {
	
	public static final RoomBuilder BASIC = new BasicRoom();
	public static final RoomBuilder STORAGE = new StorageRoom();
	public static final RoomBuilder DEAD_END = new DeadEndRoom();
	public static final RoomBuilder ENTRANCE = new EntranceRoom();
	public static final RoomBuilder EXIT = new ExitRoom();
	public static final RoomBuilder BOSS = new BossRoom();
	
	
	public static RoomBuilder treasure(Item item)
	{
		return new TreasureChestRoom(item);
	}
	
	
	public static RoomBuilder shrine(Item item)
	{
		return new ItemShrineRoom(item);
	}
}
