package mightypork.test;

import java.util.Locale;

import mightypork.utils.math.rect.RectVal;
import mightypork.utils.math.rect.RectView;
import mightypork.utils.math.vect.VectVal;


public class TestConstr {
	
	public static void main(String[] args)
	{
		Locale.setDefault(Locale.ENGLISH);
		
		final RectVal rect = RectVal.make(0, 0, 10, 10);
		final VectVal point = VectVal.make(50, 50);
		
		System.out.println(rect);
		System.out.println(point);
		System.out.println(rect.view().centerTo(point));
		
		
	}
}
