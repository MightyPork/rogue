package mightypork.test;


import java.util.Locale;

import mightypork.util.math.noise.NoiseGen;


public class TestPerlin {	
	
	public static void main(String[] args)
	{
		Locale.setDefault(Locale.ENGLISH);
		
		int w = 50, h = 50;
		
		NoiseGen ng = new NoiseGen(0.12, 0, 2.5, 5, (long) (Math.random()*100));
		
		double[][] map = ng.buildMap(w, h);
		
		char[] colors = {' ', '░','▒','▓','█'};
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {	
				// "pixels" two-thick
				System.out.print(colors[(int) Math.floor(map[y][x])]);
				System.out.print(colors[(int) Math.floor(map[y][x])]);
			}
			System.out.println();
		}
	}
	
}
