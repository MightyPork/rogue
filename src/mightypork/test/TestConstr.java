package mightypork.test;


import java.util.Locale;

import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.num.NumVar;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.RectConst;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.VectConst;
import mightypork.utils.math.constraints.vect.VectVar;


public class TestConstr {
	
	public static void main(String[] args)
	{
		Locale.setDefault(Locale.ENGLISH);
		
		int cnt = -1;
		
		{
			final RectConst rect = Rect.make(0, 0, 10, 10);
			final VectConst point = Vect.make(50, 50);
			System.out.println("Test " + ++cnt + ": rect = " + rect);
			System.out.println("Test " + cnt + ": point = " + point);
			System.out.println("Test " + cnt + ": centered rect = " + rect.centerTo(point));
		}
		
		{
			final RectConst rect = Rect.make(0, 0, 10, 10);
			final Rect v = rect;
			System.out.println("\nTest " + ++cnt + ": " + (v == rect));
		}
		
		{
			final RectConst rect = Rect.make(0, 0, 10, 10);
			final Rect v = rect;
			System.out.println("\nTest " + ++cnt + ": " + (v == rect));
		}
		
		{
			final Vect a = Vect.make(3, 3);
			@SuppressWarnings("deprecation")
			final VectConst v = a.freeze().freeze().freeze();
			System.out.println("\nTest " + ++cnt + ": " + (v == a.freeze()));
		}
		
		{
			final Vect a = Vect.make(3, 3);
			@SuppressWarnings("deprecation")
			final VectConst v = a.freeze().freeze().freeze();
			System.out.println("\nTest " + ++cnt + ": " + (v == a.freeze()));
		}
		
		{
			final VectVar a = Vect.makeVar(10, 10);
			final Vect view = a.mul(10).half().sub(1, 1);
			System.out.println("\nTest " + ++cnt + ": " + (view.equals(Vect.make(49, 49))));
			a.add(10, 0);
			System.out.println("Test " + cnt + ": " + (view.equals(Vect.make(99, 49))));
			a.setTo(900, 999);
			System.out.println(view);
		}
		
		{
			final NumVar side = Num.makeVar(100);
			final VectVar center = Vect.makeVar(0, 0);
			
			final Rect box = Rect.make(side, side).centerTo(center);
			
			System.out.println(box);
			
			side.setTo(10);
			
			System.out.println(box);
			
			center.setTo(900, -50);
			
			System.out.println(box);
			
		}
		
		{
			final NumVar a = Num.makeVar(100);
			
			a.setTo(a.mul(50).add(10).div(2));
			
			System.out.println(a);
			
		}
	}
}
