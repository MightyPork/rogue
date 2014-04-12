package mightypork.test;


import java.util.Locale;

import mightypork.utils.math.rect.RectVal;
import mightypork.utils.math.rect.RectView;
import mightypork.utils.math.vect.VectVal;
import static mightypork.utils.math.constraints.Constraints.*;


public class TestConstr {
	
	public static void main(String[] args)
	{
		Locale.setDefault(Locale.ENGLISH);
		
		final RectView rect = RectVal.make(0, 0, 10, 10);
		final VectVal point = VectVal.make(50,50);
		
		System.out.println(rect);
		System.out.println(point);
		System.out.println(cCenterTo(rect, point).getRect());
		
		
//		final RectValue rm = RectValue.make(0, 0, 100, 100);
//		System.out.println(rm);
//		
//		final RectValue added = rm.move(10, 10);
//		
//		System.out.println(added);
//		System.out.println(added.getOrigin());
//		System.out.println(added.getSize());
//		
//		System.out.println(added.getOrigin().add(added.getSize()));
		
//		VecMutable vv = new MutableCoord(0,0);
//		
//		System.out.println(vv);
//		System.out.println(vv.add(50,50));
		
	}
}
