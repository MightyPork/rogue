package mightypork.utils.files.ion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;


/**
 * Utilities to store and load objects to streams.
 * 
 * @author MightyPork
 */
public class StreamUtils {
	
	private static ByteBuffer bi = ByteBuffer.allocate(Integer.SIZE / 8);
	private static ByteBuffer bd = ByteBuffer.allocate(Double.SIZE / 8);
	private static ByteBuffer bf = ByteBuffer.allocate(Float.SIZE / 8);
	private static ByteBuffer bc = ByteBuffer.allocate(Character.SIZE / 8);
	private static ByteBuffer bl = ByteBuffer.allocate(Long.SIZE / 8);
	private static ByteBuffer bs = ByteBuffer.allocate(Short.SIZE / 8);
	
	private static byte[] ai = new byte[Integer.SIZE / 8];
	private static byte[] ad = new byte[Double.SIZE / 8];
	private static byte[] af = new byte[Float.SIZE / 8];
	private static byte[] ac = new byte[Character.SIZE / 8];
	private static byte[] al = new byte[Long.SIZE / 8];
	private static byte[] as = new byte[Short.SIZE / 8];
	
	
	// CONVERSIONS
	
	private static byte[] convBool(boolean bool)
	{
		return new byte[] { (byte) (bool ? 1 : 0) };
	}
	
	
	private static byte[] convByte(byte num)
	{
		return new byte[] { num };
	}
	
	
	private static byte[] convChar(char num)
	{
		bc.clear();
		bc.putChar(num);
		return bc.array();
	}
	
	
	private static byte[] convShort(short num)
	{
		bs.clear();
		bs.putShort(num);
		return bs.array();
	}
	
	
	private static byte[] convInt(int num)
	{
		bi.clear();
		bi.putInt(num);
		return bi.array();
	}
	
	
	private static byte[] convLong(long num)
	{
		bl.clear();
		bl.putLong(num);
		return bl.array();
	}
	
	
	private static byte[] convFloat(float num)
	{
		bf.clear();
		bf.putFloat(num);
		return bf.array();
	}
	
	
	private static byte[] convDouble(double num)
	{
		bd.clear();
		bd.putDouble(num);
		return bd.array();
	}
	
	
	private static byte[] convString(String str)
	{
		char[] chars = str.toCharArray();
		
		ByteBuffer bstr = ByteBuffer.allocate((Character.SIZE / 8) * chars.length + (Character.SIZE / 8));
		for (char c : chars) {
			bstr.putChar(c);
		}
		
		bstr.putChar((char) 0);
		
		return bstr.array();
	}
	
	
	private static byte[] convString_b(String str)
	{
		char[] chars = str.toCharArray();
		ByteBuffer bstr = ByteBuffer.allocate((Byte.SIZE / 8) * chars.length + 1);
		for (char c : chars) {
			bstr.put((byte) c);
		}
		bstr.put((byte) 0);
		
		return bstr.array();
	}
	
	
	public static void writeBoolean(OutputStream out, boolean num) throws IOException
	{
		out.write(convBool(num));
	}
	
	
	public static void writeByte(OutputStream out, byte num) throws IOException
	{
		out.write(convByte(num));
	}
	
	
	public static void writeChar(OutputStream out, char num) throws IOException
	{
		out.write(convChar(num));
	}
	
	
	public static void writeShort(OutputStream out, short num) throws IOException
	{
		out.write(convShort(num));
	}
	
	
	public static void writeInt(OutputStream out, int num) throws IOException
	{
		out.write(convInt(num));
	}
	
	
	public static void writeLong(OutputStream out, long num) throws IOException
	{
		out.write(convLong(num));
	}
	
	
	public static void writeFloat(OutputStream out, float num) throws IOException
	{
		out.write(convFloat(num));
	}
	
	
	public static void writeDouble(OutputStream out, double num) throws IOException
	{
		out.write(convDouble(num));
	}
	
	
	public static void writeString(OutputStream out, String str) throws IOException
	{
		out.write(convString(str));
	}
	
	
	public static void writeStringBytes(OutputStream out, String str) throws IOException
	{
		out.write(convString_b(str));
	}
	
	
	// READING
	
	public static boolean readBoolean(InputStream in) throws IOException
	{
		return in.read() > 0;
	}
	
	
	public static byte readByte(InputStream in) throws IOException
	{
		return (byte) in.read();
	}
	
	
	public static char readChar(InputStream in) throws IOException
	{
		in.read(ac, 0, ac.length);
		ByteBuffer buf = ByteBuffer.wrap(ac);
		return buf.getChar();
	}
	
	
	public static short readShort(InputStream in) throws IOException
	{
		in.read(as, 0, as.length);
		ByteBuffer buf = ByteBuffer.wrap(as);
		return buf.getShort();
	}
	
	
	public static long readLong(InputStream in) throws IOException
	{
		in.read(al, 0, al.length);
		ByteBuffer buf = ByteBuffer.wrap(al);
		return buf.getLong();
	}
	
	
	public static int readInt(InputStream in) throws IOException
	{
		in.read(ai, 0, ai.length);
		ByteBuffer buf = ByteBuffer.wrap(ai);
		return buf.getInt();
	}
	
	
	public static float readFloat(InputStream in) throws IOException
	{
		in.read(af, 0, af.length);
		ByteBuffer buf = ByteBuffer.wrap(af);
		return buf.getFloat();
	}
	
	
	public static double readDouble(InputStream in) throws IOException
	{
		in.read(ad, 0, ad.length);
		ByteBuffer buf = ByteBuffer.wrap(ad);
		return buf.getDouble();
	}
	
	
	public static String readString(InputStream in) throws IOException
	{
		String s = "";
		char c;
		while ((c = readChar(in)) > 0) {
			s += c;
		}
		return s;
	}
	
	
	public static String readStringBytes(InputStream in) throws IOException
	{
		String s = "";
		byte b;
		while ((b = readByte(in)) > 0) {
			s += (char) b;
		}
		return s;
	}
	
}
