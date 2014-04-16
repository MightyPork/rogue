package mightypork.util.files.ion;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;


/**
 * Utilities to store and load objects to streams.
 * 
 * @author MightyPork
 */
public class BinaryUtils {
	
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
	
	public static byte[] getBytesBool(boolean bool)
	{
		return new byte[] { (byte) (bool ? 1 : 0) };
	}
	
	
	public static byte[] getBytesByte(byte num)
	{
		return new byte[] { num };
	}
	
	
	public static byte[] getBytesChar(char num)
	{
		bc.clear();
		bc.putChar(num);
		return bc.array();
	}
	
	
	public static byte[] getBytesShort(short num)
	{
		bs.clear();
		bs.putShort(num);
		return bs.array();
	}
	
	
	public static byte[] getBytesInt(int num)
	{
		bi.clear();
		bi.putInt(num);
		return bi.array();
	}
	
	
	public static byte[] getBytesLong(long num)
	{
		bl.clear();
		bl.putLong(num);
		return bl.array();
	}
	
	
	public static byte[] getBytesFloat(float num)
	{
		bf.clear();
		bf.putFloat(num);
		return bf.array();
	}
	
	
	public static byte[] getBytesDouble(double num)
	{
		bd.clear();
		bd.putDouble(num);
		return bd.array();
	}
	
	
	public static byte[] getBytesString(String str)
	{
		final char[] chars = str.toCharArray();
		
		final ByteBuffer bstr = ByteBuffer.allocate((Character.SIZE / 8) * chars.length + (Character.SIZE / 8));
		for (final char c : chars) {
			bstr.putChar(c);
		}
		
		bstr.putChar((char) 0);
		
		return bstr.array();
	}
	
	
	public static void writeBoolean(OutputStream out, boolean num) throws IOException
	{
		out.write(getBytesBool(num));
	}
	
	
	public static void writeByte(OutputStream out, byte num) throws IOException
	{
		out.write(getBytesByte(num));
	}
	
	
	public static void writeChar(OutputStream out, char num) throws IOException
	{
		out.write(getBytesChar(num));
	}
	
	
	public static void writeShort(OutputStream out, short num) throws IOException
	{
		out.write(getBytesShort(num));
	}
	
	
	public static void writeInt(OutputStream out, int num) throws IOException
	{
		out.write(getBytesInt(num));
	}
	
	
	public static void writeLong(OutputStream out, long num) throws IOException
	{
		out.write(getBytesLong(num));
	}
	
	
	public static void writeFloat(OutputStream out, float num) throws IOException
	{
		out.write(getBytesFloat(num));
	}
	
	
	public static void writeDouble(OutputStream out, double num) throws IOException
	{
		out.write(getBytesDouble(num));
	}
	
	
	public static void writeString(OutputStream out, String str) throws IOException
	{
		out.write(getBytesString(str));
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
		if (-1 == in.read(ac, 0, ac.length)) throw new IOException("End of stream.");
		final ByteBuffer buf = ByteBuffer.wrap(ac);
		return buf.getChar();
	}
	
	
	public static short readShort(InputStream in) throws IOException
	{
		if (-1 == in.read(as, 0, as.length)) throw new IOException("End of stream.");
		final ByteBuffer buf = ByteBuffer.wrap(as);
		return buf.getShort();
	}
	
	
	public static long readLong(InputStream in) throws IOException
	{
		if (-1 == in.read(al, 0, al.length)) throw new IOException("End of stream.");
		
		final ByteBuffer buf = ByteBuffer.wrap(al);
		return buf.getLong();
	}
	
	
	public static int readInt(InputStream in) throws IOException
	{
		if (-1 == in.read(ai, 0, ai.length)) throw new IOException("End of stream.");
		final ByteBuffer buf = ByteBuffer.wrap(ai);
		return buf.getInt();
	}
	
	
	public static float readFloat(InputStream in) throws IOException
	{
		if (-1 == in.read(af, 0, af.length)) throw new IOException("End of stream.");
		final ByteBuffer buf = ByteBuffer.wrap(af);
		return buf.getFloat();
	}
	
	
	public static double readDouble(InputStream in) throws IOException
	{
		if (-1 == in.read(ad, 0, ad.length)) throw new IOException("End of stream.");
		final ByteBuffer buf = ByteBuffer.wrap(ad);
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
}
