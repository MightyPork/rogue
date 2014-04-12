package mightypork.test;


import mightypork.utils.math.rect.FixedRect;
import mightypork.utils.math.rect.RectView;


public class TestConstr {
	
	public static void main(String[] args)
	{
		
		final RectView rm = new FixedRect(0, 0, 100, 100);
		System.out.println(rm);
		
		final RectView added = rm.move(10, 10);
		
		System.out.println(added);
		System.out.println(added.getOrigin());
		System.out.println(added.getSize());
		
		System.out.println(added.getOrigin().add(added.getSize()));
		
//		VecMutable vv = new MutableCoord(0,0);
//		
//		System.out.println(vv);
//		System.out.println(vv.add(50,50));
		
	}
}
