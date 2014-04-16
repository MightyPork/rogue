package mightypork.util.math.color;


/**
 * Basic RGB palette
 * 
 * @author MightyPork
 */
public interface RGB {
	
	Color WHITE = Color.fromHex(0xFFFFFF);
	Color BLACK = Color.fromHex(0x000000);
	Color GRAY_DARK = Color.fromHex(0x808080);
	Color GRAY_LIGHT = Color.fromHex(0xC0C0C0);
	
	Color RED = Color.fromHex(0xFF0000);
	Color GREEN = Color.fromHex(0x00FF00);
	Color BLUE = Color.fromHex(0x0000FF);
	
	Color YELLOW = Color.fromHex(0xFFFF00);
	Color CYAN = Color.fromHex(0x00FFFF);
	Color MAGENTA = Color.fromHex(0xFF00FF);
	
	Color PINK = Color.fromHex(0xFF3FFC);
	Color ORANGE = Color.fromHex(0xFC4800);
}
