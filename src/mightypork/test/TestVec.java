package mightypork.test;


import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectConst;
import mightypork.utils.math.vect.VectVar;


public class TestVec {
	
	public static void main(String[] args)
	{
		VectVar a = Vect.makeVar(-100, 12, 6);
		
		VectConst b = a.freeze();
		
		a.setTo(400, 400, 300);
		
		System.out.println(a);
		System.out.println(b);
		
		Vect c = a.abs().neg();
		
		System.out.println(c);
		
		System.out.println("20,1");
		a.setTo(20, 1);
		
		System.out.println(a);
		System.out.println(c);
		
	}
}
