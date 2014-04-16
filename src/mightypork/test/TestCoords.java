package mightypork.test;


import mightypork.util.math.constraints.num.Num;
import mightypork.util.math.constraints.num.mutable.NumVar;
import mightypork.util.math.constraints.vect.Vect;
import mightypork.util.math.constraints.vect.mutable.VectVar;


public class TestCoords {
	
	public static void main(String[] args)
	{
		{
			final VectVar a = Vect.makeVar();
			final VectVar b = Vect.makeVar();
			
			final Vect cross = a.cross(b);
			final Num dot = a.dot(b);
			final Vect sum = a.add(b);
			final Num dist = a.dist(b);
			
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
			final NumVar a = Num.makeVar();
			
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
