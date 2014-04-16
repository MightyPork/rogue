package mightypork.test;


import mightypork.util.constraints.vect.Vect;
import mightypork.util.constraints.vect.VectConst;
import mightypork.util.constraints.vect.mutable.VectVar;


public class TestVec {
	
	public static void main(String[] args)
	{
		final VectVar a = Vect.makeVar(-100, 12, 6);
		
		final VectConst b = a.freeze();
		
		a.setTo(400, 400, 300);
		
		System.out.println(a);
		System.out.println(b);
		
		final Vect c = a.abs().neg();
		
		System.out.println(c);
		
		System.out.println("20,1");
		a.setTo(20, 1);
		
		System.out.println(a);
		System.out.println(c);
		
	}
}
