package mightypork.test;


import java.util.Random;


public class TestRandomSeed {
	
	public static void main(String[] args)
	{
		
		{
			final Random rand = new Random();
			final long begin = System.currentTimeMillis();
			
			for (int i = 0; i < 1000000; i++) {
				rand.setSeed(1000);
			}
			
			System.out.println((System.currentTimeMillis() - begin) / 1000D);
		}
		
		{
			final long begin = System.currentTimeMillis();
			
			for (int i = 0; i < 1000000; i++) {
				final Random rand = new Random();
				rand.setSeed(1000);
			}
			
			System.out.println((System.currentTimeMillis() - begin) / 1000D);
		}
		
	}
}
