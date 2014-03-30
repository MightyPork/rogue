package mightypork.rogue.fonts;


import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;

import mightypork.rogue.Config;
import mightypork.utils.logging.Log;
import mightypork.utils.math.coord.Coord;

import org.newdawn.slick.util.ResourceLoader;


/**
 * Remade universal font manager for Sector.
 * 
 * @author MightyPork
 */
public class FontManager {

	private static final boolean DEBUG = Config.LOG_FONTS;

	/**
	 * Glyph tables.
	 * 
	 * @author MightyPork
	 */
	public static class Glyphs {

		//@formatter:off
		/** all glyphs */
		public static final String all = 
				" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]" +
				"^_`abcdefghijklmnopqrstuvwxyz{|}~€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘’“”•–—˜™š›œžŸ¡¢£¤" +
				"¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäå" +
				"æçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
		
		/** letters and numbers, sufficient for basic messages etc. NO SPACE */
		public static final String alnum_nospace = 
				"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		/** letters and numbers, sufficient for basic messages etc. */
		public static final String alnum = 
				" "+alnum_nospace;
		
		/** letters and numbers with the most basic punctuation signs */
		public static final String basic_text = 
				" .-,.?!:;_"+alnum_nospace;

		/** letters */
		public static final String alpha = 
				" ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		
		/** numbers */
		public static final String numbers = 
				" 0123456789.-,:";
		
		/** non-standard variants of alnum */
		public static final String alnum_extra = 
				" ŒÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜŸÝßàáâãäåæçèéêëìíîïðñòóôõöøùúû" +
				"üýþÿĚŠČŘŽŤŇĎŮěščřžťňďůŁłđ";
		
		/** signs and punctuation */
		public static final String signs = 
				" !\"#$%&§'()*+,-./:;<=>?@[\\]^_{|}~";
		
		/** extra signs and punctuation */
		public static final String signs_extra = 
				" ¥€£¢`ƒ„…†‡ˆ‰‹‘’“”•›¡¤¦¨ª«¬­¯°±²³´µ¶·¸¹º»¼½¾¿÷™©­®→↓←↑";
		
		
		/** basic character set. */
		public static final String basic = alnum + signs;
		//@formatter:on
	}

	/**
	 * Font style
	 * 
	 * @author MightyPork
	 */
	public static enum Style
	{
		/** Normal */
		NORMAL,
		/** Italic */
		ITALIC,
		/** Stronger italic to left. */
		LEFT,
		/** Stronger italic to right */
		RIGHT,
		/** Monospace type */
		MONO,
		/** Bold */
		BOLD,
		/** Bold & Italic */
		BOLD_I,
		/** Bold & Left */
		BOLD_L,
		/** Bold & Right */
		BOLD_R,
		/** Heavy style, stronger than bold. */
		HEAVY,
		/** Light (lighter than normal) */
		LIGHT,
		/** narrow style, similar to Light */
		NARROW,
		/** Wide style, like Bold but with thinner lines */
		WIDE,
		/** Outline variant of normal */
		OUTLINE;
	}

//	/**
//	 * Preloaded font identifier [name, size, style]
//	 * 
//	 * @author MightyPork
//	 */
//	public static class FontId {
//
//		/** font size (pt) */
//		public float size = 24;
//		/** font name, registered with registerFile */
//		public String name = "";
//		/** font style. The given style must be in a file. */
//		public Style style;
//
//		/** Set of glyphs in this ID */
//		public String glyphs = "";
//
//		/** Index for faster comparision of glyph ids. */
//		public int glyphset_id = 0;
//
//
//		/**
//		 * Preloaded font identifier
//		 * 
//		 * @param name font name (registerFile)
//		 * @param size font size (pt)
//		 * @param style font style
//		 * @param glyphs glyphs to load
//		 */
//		public FontId(String name, double size, Style style, String glyphs) {
//			this.name = name;
//			this.size = (float) size;
//			this.style = style;
//
//			if (glyphs.equals(Glyphs.basic)) {
//				glyphset_id = 1;
//			} else if (glyphs.equals(Glyphs.alnum)) {
//				glyphset_id = 2;
//			} else if (glyphs.equals(Glyphs.basic_text)) {
//				glyphset_id = 3;
//			} else if (glyphs.equals(Glyphs.numbers)) {
//				glyphset_id = 4;
//			} else if (glyphs.equals(Glyphs.alpha)) {
//				glyphset_id = 5;
//			} else if (glyphs.equals(Glyphs.all)) {
//				glyphset_id = 6;
//			} else if (glyphs.equals(Glyphs.alnum_extra)) {
//				glyphset_id = 7;
//			} else if (glyphs.equals(Glyphs.signs)) {
//				glyphset_id = 8;
//			} else if (glyphs.equals(Glyphs.signs_extra)) {
//				glyphset_id = 9;
//			} else {
//				this.glyphs = glyphs;
//			}
//		}
//
//
//		@Override
//		public boolean equals(Object obj)
//		{
//			if (obj == null) return false;
//			if (!(obj.getClass().isAssignableFrom(getClass()))) return false;
//			if (obj instanceof FontId) {
//				if (obj == this) return true;
//				FontId id2 = ((FontId) obj);
//				boolean flag = true;
//				flag &= id2.size == size;
//				flag &= id2.name.equals(name);
//				flag &= id2.style == style;
//				flag &= ((id2.glyphset_id != -1 && id2.glyphset_id == glyphset_id) || id2.glyphs.equals(glyphs));
//				return flag;
//			}
//			return false;
//		}
//
//
//		@Override
//		public int hashCode()
//		{
//			return (new Float(size).hashCode()) ^ name.hashCode() ^ style.hashCode() ^ glyphset_id;
//		}
//
//
//		@Override
//		public String toString()
//		{
//			return "[" + name + ", " + size + ", " + style + (glyphset_id > 0 ? ", g=" + glyphset_id : ", g=custom") + "]";
//		}
//	}

	/**
	 * Group of styles of one font.
	 * 
	 * @author MightyPork
	 */
	public static class FontFamily extends HashMap<Style, String> {
	}

	/**
	 * Table of font files. name → {style:file,style:file,style:file...}
	 */
	private static HashMap<String, FontFamily> fontFiles = new HashMap<String, FontFamily>();


	/**
	 * Register font file.
	 * 
	 * @param path resource path (res/fonts/...)
	 * @param name font name (for binding)
	 * @param style font style in this file
	 */
	public static void registerFile(String path, String name, Style style)
	{
		if (fontFiles.containsKey(name)) {
			if (fontFiles.get(name) != null) {
				fontFiles.get(name).put(style, path);
				return;
			}
		}

		// insert new table of styles to font name.
		FontFamily family = new FontFamily();
		family.put(style, path);
		fontFiles.put(name, family);
	}

	/** Counter of loaded fonts */
	public static int loadedFontCounter = 0;


	/**
	 * Preload font if needed, get preloaded font.<br>
	 * If needed file is not available, throws runtime exception.
	 * 
	 * @param name font name (registerFile)
	 * @param size font size (pt)
	 * @param style font style
	 * @param glyphs glyphs needed
	 * @return the loaded font.
	 */
	public static LoadedFont loadFont(String name, double size, Style style, String glyphs)
	{
		return loadFont(name, size, style, glyphs, 9, 8, Coord.one(), 0, 0);
	}


	/**
	 * Preload font if needed, get preloaded font.<br>
	 * If needed file is not available, throws runtime exception.
	 * 
	 * @param name font name (registerFile)
	 * @param size font size (pt)
	 * @param style font style
	 * @param glyphs glyphs needed
	 * @param correctLeft left horizontal correction
	 * @param correctRight right horizontal correction
	 * @param scale font scale (changing aspect ratio)
	 * @param clipTop top clip (0-1) - top part of the font to be cut off
	 * @param clipBottom bottom clip (0-1) - bottom part of the font to be cut
	 *            off
	 * @return the loaded font.
	 */
	public static LoadedFont loadFont(String name, double size, Style style, String glyphs, int correctLeft, int correctRight, Coord scale, double clipTop, double clipBottom)
	{
		String resourcePath;
		try {
			resourcePath = fontFiles.get(name).get(style);
			if (resourcePath == null) {
				Log.w("Font [" + name + "] does not have variant " + style + ".\nUsing NORMAL instead.");
				resourcePath = fontFiles.get(name).get(Style.NORMAL);
				if (resourcePath == null) {
					throw new NullPointerException();
				}
			}
		} catch (NullPointerException npe) {
			throw new RuntimeException("Font loading failed: no font file registered for name \"" + name + "\".");
		}

		InputStream in = ResourceLoader.getResourceAsStream(resourcePath);

		Font awtFont;
		try {
			awtFont = Font.createFont(Font.TRUETYPE_FONT, in);
		} catch (Exception e) {
			Log.e("Loading of font " + resourcePath + " failed.", e);
			throw new RuntimeException(e);
		}

		awtFont = awtFont.deriveFont((float) size); // set font size
		LoadedFont font = new LoadedFont(awtFont, true, glyphs);

		font.setCorrection(correctLeft, correctRight);
		font.setClip(clipTop, clipBottom);
		font.setScale(scale.x, scale.y);

		loadedFontCounter++;

		if (DEBUG) Log.f3("Font from file \"" + resourcePath + "\" preloaded.");

		return font;
	}
}
