package mightypork.test;


import java.util.Locale;

import mightypork.utils.math.constraints.NumBound;
import mightypork.utils.math.num.Num;
import mightypork.utils.math.num.NumView;
import mightypork.utils.math.vect.VectMutable;
import mightypork.utils.math.vect.VectView;


public class TestCoords {
	
	public static void main(String[] args)
	{
		Locale.setDefault(Locale.ENGLISH);
		
		// test
		VectMutable var = VectMutable.make(1, 2, 3);
		
		VectView cubicRoot = var.view().mul(var).mul(var);
		VectView half = var.view().half();
		
		System.out.println("x, x^3, x/5");
		System.out.println(var);
		System.out.println(cubicRoot);
		System.out.println(half);
		
		var.mul(10);
		
		System.out.println("x = x*10; x, x^3, x/5");
		System.out.println(var);
		System.out.println(cubicRoot);
		System.out.println(half);
		
		NumView y = var.view().yn();
		System.out.println("y: "+y.value());
		
		var.add(100,100);

		System.out.println("x = x*100; x.y(), x, x^3, x/5");
		System.out.println(y.value());
		System.out.println(var);
		System.out.println(cubicRoot);
		System.out.println(half);
		
	}
}
