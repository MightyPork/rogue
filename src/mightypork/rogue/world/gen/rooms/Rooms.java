package mightypork.rogue.world.gen.rooms;


import mightypork.rogue.world.gen.RoomBuilder;


public class Rooms {
	
	public static final RoomBuilder BASIC = new BasicRoom();
	public static final RoomBuilder SECRET = new SecretRoom();
	public static final RoomBuilder DEAD_END = new DeadEndRoom();
	public static final RoomBuilder ENTRANCE = new EntranceRoom();
	public static final RoomBuilder EXIT = new ExitRoom();
	public static final RoomBuilder BOSS_ROOM = new BossRoom();
}
