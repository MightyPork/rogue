package mightypork.rogue.world.tile;


import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.RGB;


public class TileColors {
	
	public static final Color NULL = RGB.NONE;
	public static final Color FLOOR = RGB.GRAY_DARK;
	public static final Color WALL = RGB.GRAY_LIGHT;
	public static final Color DOOR = RGB.BROWN;
	public static final Color COLLAPSED_WALL = RGB.GRAY;
	
	public static final Color ENTRANCE = RGB.CYAN;
	public static final Color EXIT = Color.fromHex(0x00EA8C);
	
	public static final Color SECRET_DOOR_REVEALED = RGB.PINK;
	public static final Color SECRET_DOOR_HIDDEN = WALL;
	
}
