package mightypork.test;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.util.files.ion.Ion;


public class TestIonArray2 {
	
	public static void main(String[] args) throws IOException
	{
		final int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 99999, 8888888 };
		
		final OutputStream out = new FileOutputStream("fuck.ion");
		
		Ion.writeIntArray(out, array);
		Ion.writeString(out, "HELLO DUDE WHATSUP");
		Ion.writeCharArray(out, "HERE'S ONE COOL ARRAY!!!".toCharArray());
		
		// ---
		
		final InputStream in = new FileInputStream("fuck.ion");
		
		final int[] a = Ion.readIntArray(in);
		
		for (final int i : a)
			System.out.println(i);
		
		final String s = Ion.readString(in);
		System.out.println(s);
		
		final char[] v = Ion.readCharArray(in);
		
		for (final int i : v)
			System.out.print((char) i);
		
	}
}
