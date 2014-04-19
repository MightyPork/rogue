package mightypork.test;


import java.io.*;
import mightypork.util.files.ion.Ion;


public class TestIonArray2 {
	
	public static void main(String[] args) throws IOException
	{
		int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9,99999,8888888 };
		
		OutputStream out = new FileOutputStream("fuck.ion");
		
		Ion.writeIntArray(out, array);
		Ion.writeString(out, "HELLO DUDE WHATSUP");
		Ion.writeCharArray(out, "HERE'S ONE COOL ARRAY!!!".toCharArray());

		// ---
		
		InputStream in = new FileInputStream("fuck.ion");
		
		int[] a = Ion.readIntArray(in);
		
		for (int i : a)
			System.out.println(i);

		String s = Ion.readString(in);
		System.out.println(s);
		

		char[] v = Ion.readCharArray(in);
		
		for (int i : v)
			System.out.print((char)i);
		
	}
}
