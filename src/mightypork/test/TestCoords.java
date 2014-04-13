package mightypork.test;


import java.util.Locale;

import mightypork.utils.math.num.NumView;
import mightypork.utils.math.vect.VectMutable;
import mightypork.utils.math.vect.VectView;


public class TestCoords {
	
	public static void main(String[] args)
	{
		Locale.setDefault(Locale.ENGLISH);
		
		// test
		final VectMutable var = VectMutable.make(1, 2, 3);
		
		final VectView cubicRoot = var.mul(var).mul(var);
		final VectView half = var.half();
		
		System.out.println("x, x^3, x/5");
		System.out.println(var);
		System.out.println(cubicRoot);
		System.out.println(half);
		
		var.setTo(var.mul(10));
		
		System.out.println("x = x*10; x, x^3, x/5");
		System.out.println(var);
		System.out.println(cubicRoot);
		System.out.println(half);
		
		final NumView y = var.yn();
		System.out.println("y: " + y.value());
		
		var.setTo(var.add(100, 100));
		
		System.out.println("x = x*100; x.y(), x, x^3, x/5");
		System.out.println(y.value());
		System.out.println(var);
		System.out.println(cubicRoot);
		System.out.println(half);
		
	}
}
