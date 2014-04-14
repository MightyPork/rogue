package mightypork.test;


import mightypork.utils.math.num.Num;
import mightypork.utils.math.num.NumVar;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVar;


public class TestCoords {
	
	public static void main(String[] args)
	{
		{
			VectVar a = Vect.makeVar();
			VectVar b = Vect.makeVar();
			
			Vect cross = a.cross(b);
			Num dot = a.dot(b);
			Vect sum = a.add(b);
			Num dist = a.dist(b);
			
			a.setTo(0, 10, 0);
			b.setTo(0, 6, 7);
			
			System.out.println("a = " + a);
			System.out.println("b = " + b);
			System.out.println("axb = " + cross);
			System.out.println("a.b = " + dot);
			System.out.println("a+b = " + sum);
			System.out.println("dist(a,b) = " + dist);
		}
		
		{
			NumVar a = Num.makeVar();
			
			Num end = a;
			
			for (int i = 0; i < 100; i++) {
				end = end.add(1);
			}
			
			System.out.println(end);
			a.setTo(37);
			System.out.println(end);
			
		}
		
	}
}
