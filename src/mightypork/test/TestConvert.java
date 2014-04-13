package mightypork.test;


import mightypork.utils.objects.Convert;


public class TestConvert {
	
	public static void main(String[] args)
	{
		System.out.println(Convert.toVect("(10:20:30)"));
		System.out.println(Convert.toVect("30.6 ; 80"));
		System.out.println(Convert.toVect("30.6"));
		
		System.out.println(Convert.toRange("10"));
		System.out.println(Convert.toRange("10..60"));
		System.out.println(Convert.toRange("10-60"));
		System.out.println(Convert.toRange("10--60"));
		System.out.println(Convert.toRange("-10--60"));
		System.out.println(Convert.toRange("3.1;22"));
		System.out.println(Convert.toRange("3.1|22"));
		
	}
}
