package mightypork.rogue.textures;


import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;


/**
 * Texture loading class
 * 
 * @author MightyPork
 */
public class Textures {

	protected static Texture buttons_menu;
	protected static Texture buttons_small;

	// patterns need raw access (are repeated)
	public static Texture steel_small_dk;
	public static Texture steel_small_lt;
	public static Texture steel_big_dk;
	public static Texture steel_big_lt;
	public static Texture steel_big_scratched;
	public static Texture steel_brushed;
	public static Texture steel_rivet_belts;
	public static Texture water;

	// particles need direct access
	public static Texture snow;
	public static Texture leaves;
	public static Texture rain;
	public static Texture circle;

	protected static Texture logo;
	protected static Texture widgets;
	public static Texture program;
	protected static Texture icons;

	public static Texture map_tiles;
	public static Texture map_shading;

	// spotted
	public static Texture turtle_blue;
	public static Texture turtle_green;
	public static Texture turtle_purple;
	public static Texture turtle_yellow;

	// misc
	public static Texture turtle_red;
	public static Texture turtle_brown_dk;
	public static Texture turtle_brown_lt;
	public static Texture turtle_brown_orn;
	// a shadow sheet
	public static Texture turtle_shadows;

	public static Texture food;
	public static Texture food_shadows;

	private static final String GUI = "res/images/gui/";
	private static final String PATTERNS = "res/images/patterns/";
	private static final String PROGRAM = "res/images/program/";
	private static final String TILES = "res/images/tiles/";
	private static final String SPRITES = "res/images/sprites/";
	private static final String PARTICLES = "res/images/particles/";


	/**
	 * Load what's needed for splash
	 */
	public static void loadForSplash()
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		logo = TextureManager.load(GUI + "logo.png");
		steel_small_dk = TextureManager.load(PATTERNS + "steel.png");

		Tx.initForSplash();
	}


	/**
	 * Load textures
	 */
	public static void load()
	{
		// gui
		buttons_menu = TextureManager.load(GUI + "buttons_menu.png");
		buttons_small = TextureManager.load(GUI + "buttons_small.png");
		widgets = TextureManager.load(GUI + "widgets.png");
		icons = TextureManager.load(GUI + "icons.png");

		// patterns

		steel_small_lt = TextureManager.load(PATTERNS + "steel_light.png"); // XXX Unused texture
		steel_big_dk = TextureManager.load(PATTERNS + "steel_big.png");
		steel_big_lt = TextureManager.load(PATTERNS + "steel_big_light.png");
		steel_big_scratched = TextureManager.load(PATTERNS + "steel_big_scratched.png");
		steel_brushed = TextureManager.load(PATTERNS + "steel_brushed.png");
		steel_rivet_belts = TextureManager.load(PATTERNS + "rivet_belts.png");
		water = TextureManager.load(PATTERNS + "water.png");

		// particles
		snow = TextureManager.load(PARTICLES + "snow.png");
		leaves = TextureManager.load(PARTICLES + "leaves.png");
		rain = TextureManager.load(PARTICLES + "rain.png");
		circle = TextureManager.load(PARTICLES + "circle.png");

		// program tiles
		program = TextureManager.load(PROGRAM + "program.png");

		// map tiles
		map_tiles = TextureManager.load(TILES + "tiles.png");
		map_shading = TextureManager.load(TILES + "shading.png");

		// turtle
		turtle_blue = TextureManager.load(SPRITES + "HD-blue.png");
		turtle_yellow = TextureManager.load(SPRITES + "HD-yellow.png");
		turtle_green = TextureManager.load(SPRITES + "HD-green.png");
		turtle_purple = TextureManager.load(SPRITES + "HD-purple.png");
		turtle_red = TextureManager.load(SPRITES + "HD-red.png");

		turtle_brown_dk = TextureManager.load(SPRITES + "HD-brown-dk.png");
		turtle_brown_lt = TextureManager.load(SPRITES + "HD-brown-lt.png");
		turtle_brown_orn = TextureManager.load(SPRITES + "HD-brown-ornamental.png");
		turtle_shadows = TextureManager.load(SPRITES + "HD-shadow.png");

		food = TextureManager.load(SPRITES + "food.png");
		food_shadows = TextureManager.load(SPRITES + "food-shadow.png");

		glDisable(GL_TEXTURE_2D);

		Tx.init();
	}

}
