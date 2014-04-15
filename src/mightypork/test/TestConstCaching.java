package mightypork.test;


import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.caching.VectCache;
import mightypork.utils.math.constraints.vect.mutable.VectVar;


public class TestConstCaching {
	
	public static void main(String[] args)
	{
		final VectVar in = Vect.makeVar(0, 0);
		final VectCache cache = in.cached();
		cache.enableDigestCaching(true);
		
		System.out.println("in = " + in);
		System.out.println("cache = " + cache);
		System.out.println("cache digest = " + cache.digest());
		System.out.println("\n-- in := 100, 50, 25 --\n");
		in.setTo(100, 50, 25);
		System.out.println("in = " + in);
		System.out.println("cache = " + cache);
		System.out.println("cache digest = " + cache.digest());
		System.out.println("\n-- cache.poll() --\n");
		cache.markDigestDirty();
		System.out.println("in = " + in);
		System.out.println("cache = " + cache);
		System.out.println("cache digest = " + cache.digest());
		System.out.println("\n-- in := 1, 2, 3 --\n");
		in.setTo(1, 2, 3);
		System.out.println("in = " + in);
		System.out.println("cache = " + cache);
		System.out.println("cache digest = " + cache.digest());
		System.out.println("\n-- cache.poll() --\n");
		cache.markDigestDirty();
		System.out.println("cache = " + cache);
		System.out.println("cache digest = " + cache.digest());
		
	}
}
