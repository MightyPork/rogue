package mightypork.utils.math.coord;


public class Synths {
	
	private static abstract class HeteroSynth extends VecProxy {
		
		public HeteroSynth(Vec observed) {
			super(observed);
		}
		
		
		@Override
		protected abstract double processX(double x);
		
		
		@Override
		protected abstract double processY(double y);
		
		
		@Override
		protected abstract double processZ(double z);
	}
	
	private static abstract class UniformSynth extends VecProxy {
		
		public UniformSynth(Vec observed) {
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
	
	public static class Round extends UniformSynth {
		
		public Round(Vec observed) {
			super(observed);
		}
		
		
		@Override
		protected double process(double a)
		{
			return Math.round(a);
		}
	}
	
	public static class Ceil extends UniformSynth {
		
		public Ceil(Vec observed) {
			super(observed);
		}
		
		
		@Override
		protected double process(double a)
		{
			return Math.ceil(a);
		}
	}
	
	public static class Floor extends UniformSynth {
		
		public Floor(Vec observed) {
			super(observed);
		}
		
		
		@Override
		protected double process(double a)
		{
			return Math.floor(a);
		}
	}
	
	public static class Neg extends UniformSynth {
		
		public Neg(Vec observed) {
			super(observed);
		}
		
		
		@Override
		protected double process(double a)
		{
			return -a;
		}
	}
	
	public static class Half extends UniformSynth {
		
		public Half(Vec observed) {
			super(observed);
		}
		
		
		@Override
		protected double process(double a)
		{
			return a / 2;
		}
	}
	
	public static class Norm extends HeteroSynth {
		
		public Norm(Vec observed) {
			super(observed);
		}
		
		
		@Override
		protected double processX(double x)
		{
			return 0;
		}
		
		
		@Override
		protected double processY(double y)
		{
			return 0;
		}
		
		
		@Override
		protected double processZ(double z)
		{
			return 0;
		}
	}
}
