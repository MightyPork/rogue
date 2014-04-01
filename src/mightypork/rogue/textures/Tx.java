package mightypork.rogue.textures;


// TODO rewrite

/**
 * List of texture quads for GUIs
 * 
 * @author MightyPork
 */
public class Tx {
	
	// logo
	public static TxQuad LOGO;
	
	
	public static void initForSplash()
	{
		// splash logo
		LOGO = TxQuad.fromSize(Textures.logo, 15, 9, 226, 132);
	}
	
	
	public static void init()
	{
		// title image (word art)
		
	}
	
}
