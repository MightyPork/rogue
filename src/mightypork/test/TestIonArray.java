package mightypork.test;


import java.io.File;
import java.io.IOException;
import java.util.Random;

import mightypork.util.files.ion.Ion;


public class TestIonArray {
	
	public static void main(String[] args) throws IOException
	{
		byte[] array = new byte[1024 * 8];
		
		Random rand = new Random();
		
		for (int i = 0; i < array.length; i++) {
			array[i] = (byte) rand.nextInt();
		}
		
		Ion.toFile(new File("hello.ion"), array);
	}
}
