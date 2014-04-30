package mightypork.gamecore.resources.fonts;


/**
 * Glyph tables.
 * 
 * @author MightyPork
 */
public class Glyphs {
	
	public static final String space = " ";
	public static final String latin = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public static final String latin_extra = "ŒÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜŸÝßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿĚŠČŘŽŤŇĎŮěščřžťňďůŁłđ";
	public static final String numbers = "0123456789";
	public static final String punctuation = ".-,.?!:;\"'";
	public static final String punctuation_extra = "()¿¡»«›‹“”‘’„…";
	public static final String symbols = "[]{}#$%&§*+/<=>@\\^_|~°";
	public static final String symbols_extra = "¥€£¢`ƒ†‡ˆ‰•¤¦¨ªº¹²³¬­¯±´µ¶·¸¼½¾×÷™©­®→↓←↑";
	
	public static final String basic = space + latin + numbers + punctuation + symbols;
	public static final String extra = latin_extra + punctuation_extra + symbols_extra;
	public static final String all = basic + extra;
	
}