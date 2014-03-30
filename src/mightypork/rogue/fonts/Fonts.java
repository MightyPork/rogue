package mightypork.rogue.fonts;


import static mightypork.rogue.fonts.FontManager.Style.*;
import mightypork.rogue.fonts.FontManager.Glyphs;
import mightypork.utils.logging.Log;


/**
 * Global font preloader
 * 
 * @author Rapus
 */
public class Fonts {

	public static LoadedFont splash_info;

	public static LoadedFont tooltip;
	public static LoadedFont gui;
	public static LoadedFont gui_title;
	public static LoadedFont program_number;
	public static LoadedFont menu_button;
	public static LoadedFont menu_title;
	public static LoadedFont tiny;


	private static void registerFileNames()
	{
		FontManager.registerFile("res/fonts/4feb.ttf", "4feb", NORMAL);
	}


	/**
	 * Load fonts needed for splash.
	 */
	public static void loadForSplash()
	{
		registerFileNames();

		gui = FontManager.loadFont("4feb", 24, NORMAL, Glyphs.basic).setCorrection(8, 7);
		splash_info = FontManager.loadFont("4feb", 42, NORMAL, "Loading.");
	}


	/**
	 * Preload all fonts we will use in the game
	 */
	public static void load()
	{
		tooltip = FontManager.loadFont("4feb", 24, NORMAL, Glyphs.basic_text).setCorrection(8, 7);
		gui_title = FontManager.loadFont("4feb", 30, NORMAL, Glyphs.basic_text);
		menu_button = FontManager.loadFont("4feb", 36, NORMAL, Glyphs.basic_text);
		menu_title = FontManager.loadFont("4feb", 34, NORMAL, Glyphs.basic_text);
		program_number = FontManager.loadFont("4feb", 28, NORMAL, Glyphs.numbers);
		tiny = FontManager.loadFont("4feb", 20, NORMAL, Glyphs.basic_text);

		Log.i("Fonts loaded = " + FontManager.loadedFontCounter);

	}
}
