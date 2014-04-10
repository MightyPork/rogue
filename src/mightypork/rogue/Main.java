package mightypork.rogue;


public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Config.init();
		Config.save();
		
		(new App()).start();
	}
}
