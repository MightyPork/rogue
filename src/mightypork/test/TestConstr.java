package mightypork.test;


import java.util.Locale;

import mightypork.utils.math.num.NumMutable;
import mightypork.utils.math.rect.Rect;
import mightypork.utils.math.rect.RectVal;
import mightypork.utils.math.rect.RectView;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectMutable;
import mightypork.utils.math.vect.VectVal;
import mightypork.utils.math.vect.VectView;


public class TestConstr {
	
	public static void main(String[] args)
	{
		Locale.setDefault(Locale.ENGLISH);
		
		int cnt = -1;
		
		{
			final RectVal rect = RectVal.make(0, 0, 10, 10);
			final VectVal point = VectVal.make(50, 50);
			System.out.println("Test " + ++cnt + ": rect = " + rect);
			System.out.println("Test " + cnt + ": point = " + point);
			System.out.println("Test " + cnt + ": centered rect = " + rect.view().centerTo(point));
		}
		
		{
			final RectVal rect = RectVal.make(0, 0, 10, 10);
			final RectView v = rect.view().view();
			System.out.println("\nTest " + ++cnt + ": " + (v == rect.view()));
		}
		
		{
			final RectVal rect = RectVal.make(0, 0, 10, 10);
			final RectView v = rect.view().view().view().view().view().view();
			System.out.println("\nTest " + ++cnt + ": " + (v == rect.view()));
		}
		
		{
			final Vect a = VectVal.make(3, 3);
			final VectVal v = a.copy().copy().copy();
			System.out.println("\nTest " + ++cnt + ": " + (v == a.copy()));
		}
		
		{
			final Vect a = VectVal.make(3, 3);
			final VectVal v = a.copy().copy().copy();
			System.out.println("\nTest " + ++cnt + ": " + (v == a.copy()));
		}
		
		{
			final VectMutable a = VectMutable.make(10, 10);
			final VectView view = a.view().mul(10).half().sub(1, 1);
			System.out.println("\nTest " + ++cnt + ": " + (view.equals(VectVal.make(49, 49))));
			a.add(10, 0);
			System.out.println("Test " + cnt + ": " + (view.equals(VectVal.make(99, 49))));
			a.setTo(900, 999);
			System.out.println(view);
		}
		
		{
			final NumMutable side = NumMutable.make(100);
			final VectMutable center = VectMutable.make(0, 0);
			
			final Rect box = side.view().box().centerTo(center);
			
			System.out.println(box);
			
			side.setTo(10);
			
			System.out.println(box);
			
			center.setTo(900, -50);
			
			System.out.println(box);
			
		}
		
		{
			final NumMutable a = NumMutable.make(100);
			
			a.setTo(a.mul(50).add(10).div(2));
			
			System.out.println(a);
			Rect r;
			System.out.println(r = a.box());
			
			a.reset();
			
			System.out.println(r);
			
		}
	}
}
