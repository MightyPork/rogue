package mightypork.gamecore.input;


import org.lwjgl.input.Keyboard;


/**
 * Key constants, from LWJGL {@link Keyboard}
 * 
 * @author MightyPork
 */
public interface Keys {
	
	//@formatter:off

	public static final int NONE            = 0x00;

	public static final int ESCAPE          = 0x01;
	
	public static final int NUM_1           = 0x02;
	public static final int NUM_2           = 0x03;
	public static final int NUM_3           = 0x04;
	public static final int NUM_4           = 0x05;
	public static final int NUM_5           = 0x06;
	public static final int NUM_6           = 0x07;
	public static final int NUM_7           = 0x08;
	public static final int NUM_8           = 0x09;
	public static final int NUM_9           = 0x0A;
	public static final int NUM_0           = 0x0B;
	
	public static final int Q               = 0x10;
	public static final int W               = 0x11;
	public static final int E               = 0x12;
	public static final int R               = 0x13;
	public static final int T               = 0x14;
	public static final int Y               = 0x15;
	public static final int U               = 0x16;
	public static final int I               = 0x17;
	public static final int O               = 0x18;
	public static final int P               = 0x19;
	public static final int A               = 0x1E;
	public static final int S               = 0x1F;
	public static final int D               = 0x20;
	public static final int F               = 0x21;
	public static final int G               = 0x22;
	public static final int H               = 0x23;
	public static final int J               = 0x24;
	public static final int K               = 0x25;
	public static final int L               = 0x26;
	public static final int Z               = 0x2C;
	public static final int X               = 0x2D;
	public static final int C               = 0x2E;
	public static final int V               = 0x2F;
	public static final int B               = 0x30;
	public static final int N               = 0x31;
	public static final int M               = 0x32;

	public static final int MINUS           = 0x0C;
	public static final int EQUALS          = 0x0D;
	public static final int SLASH           = 0x35;
	public static final int BACKSLASH       = 0x2B;
	public static final int L_BRACKET       = 0x1A;
	public static final int R_BRACKET       = 0x1B;
	public static final int SEMICOLON       = 0x27;
	public static final int APOSTROPHE      = 0x28;
	public static final int GRAVE           = 0x29;
	public static final int COMMA           = 0x33;
	public static final int PERIOD          = 0x34;
	
	public static final int SPACE           = 0x39;
	public static final int BACKSPACE       = 0x0E;
	public static final int TAB             = 0x0F;
	
	public static final int F1              = 0x3B;
	public static final int F2              = 0x3C;
	public static final int F3              = 0x3D;
	public static final int F4              = 0x3E;
	public static final int F5              = 0x3F;
	public static final int F6              = 0x40;
	public static final int F7              = 0x41;
	public static final int F8              = 0x42;
	public static final int F9              = 0x43;
	public static final int F10             = 0x44;
	public static final int F11             = 0x57;
	public static final int F12             = 0x58;
	public static final int F13             = 0x64;
	public static final int F14             = 0x65;
	public static final int F15             = 0x66;

	public static final int CAPS_LOCK       = 0x3A;
	public static final int SCROLL_LOCK     = 0x46;	
	public static final int NUM_LOCK        = 0x45;
	
	public static final int SUBTRACT        = 0x4A; /* - on numeric keypad */
	public static final int ADD             = 0x4E; /* + on numeric keypad */
	public static final int NUMPAD_0        = 0x52;
	public static final int NUMPAD_1        = 0x4F;
	public static final int NUMPAD_2        = 0x50;
	public static final int NUMPAD_3        = 0x51;
	public static final int NUMPAD_4        = 0x4B;
	public static final int NUMPAD_5        = 0x4C;
	public static final int NUMPAD_6        = 0x4D;
	public static final int NUMPAD_7        = 0x47;
	public static final int NUMPAD_8        = 0x48;
	public static final int NUMPAD_9        = 0x49;
	public static final int DECIMAL         = 0x53; /* . on numeric keypad */
	public static final int NUMPAD_ENTER    = 0x9C; /* Enter on numeric keypad */
	public static final int DIVIDE          = 0xB5; /* / on numeric keypad */
	public static final int MULTIPLY        = 0x37; /* * on numeric keypad */
	
	public static final int L_CONTROL       = 0x1D;
	public static final int R_CONTROL       = 0x9D;
	public static final int L_ALT           = 0x38;
	public static final int R_ALT           = 0xB8;
	public static final int L_SHIFT         = 0x2A;
	public static final int R_SHIFT         = 0x36;
	public static final int L_META          = 0xDB;
	public static final int R_META          = 0xDC;
	
	public static final int UP              = 0xC8; /* UpArrow on arrow keypad */
	public static final int DOWN            = 0xD0; /* DownArrow on arrow keypad */
	public static final int LEFT            = 0xCB; /* LeftArrow on arrow keypad */
	public static final int RIGHT           = 0xCD; /* RightArrow on arrow keypad */

	public static final int HOME            = 0xC7; /* Home on arrow keypad */
	public static final int END             = 0xCF; /* End on arrow keypad */
	
	public static final int PAGE_UP         = 0xC9; /* PgUp on arrow keypad */
	public static final int PAGE_DOWN       = 0xD1; /* PgDn on arrow keypad */

	public static final int RETURN          = 0x1C;
	public static final int PAUSE           = 0xC5; /* Pause */
	public static final int INSERT          = 0xD2; /* Insert on arrow keypad */
	public static final int DELETE          = 0xD3; /* Delete on arrow keypad */
	
	//@formatter:on
}
