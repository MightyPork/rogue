package mightypork.utils.math.vect;


public class VectFilters {
	
	private static abstract class Uniform extends VectProxy {
		
		public Uniform(Vect observed) {
			super(observed);
		}
		
		
		@Override
		protected double processX(double x)
		{
			return super.processX(x);
		}
		
		
		@Override
		protected double processY(double y)
		{
			return super.processY(y);
		}
		
		
		@Override
		protected double processZ(double z)
		{
			return super.processZ(z);
		}
		
		
		protected abstract double process(double a);
	}
	
	public static class Round extends Uniform {
		
		public Round(Vect observed) {
			super(observed);
		}
		
		
		@Override
		protected double process(double a)
		{
			return Math.round(a);
		}
	}
	
	public static class Ceil extends Uniform {
		
		public Ceil(Vect observed) {
			super(observed);
		}
		
		
		@Override
		protected double process(double a)
		{
			return Math.ceil(a);
		}
	}
	
	public static class Floor extends Uniform {
		
		public Floor(Vect observed) {
			super(observed);
		}
		
		
		@Override
		protected double process(double a)
		{
			return Math.floor(a);
		}
	}
	
	public static class Neg extends Uniform {
		
		public Neg(Vect observed) {
			super(observed);
		}
		
		
		@Override
		protected double process(double a)
		{
			return -a;
		}
	}
	
	public static class Half extends Uniform {
		
		public Half(Vect observed) {
			super(observed);
		}
		
		
		@Override
		protected double process(double a)
		{
			return a / 2D;
		}
	}
}
