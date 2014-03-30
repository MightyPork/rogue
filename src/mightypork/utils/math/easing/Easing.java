package mightypork.utils.math.easing;


import mightypork.utils.math.Calc;


/**
 * Easing function.<br>
 * The easing function must be time-invariant and it's domain is [0-1].
 * 
 * @author MightyPork
 */
public interface Easing {

	/**
	 * Get value of the easing function at given time.
	 * 
	 * @param time number in range 0..1
	 * @return value at given time
	 */
	public double get(double time);

	public static final Easing NONE = new Easing() {

		@Override
		public double get(double time)
		{
			double t = Calc.clampd(time, 0, 1);

			return (t < 0.5 ? 0 : 1);
		}
	};

	public static final Easing LINEAR = new Easing() {

		@Override
		public double get(double time)
		{
			double t = Calc.clampd(time, 0, 1);

			return t;
		}
	};

	public static final Easing QUADRATIC_IN = new Easing() {

		@Override
		public double get(double time)
		{
			double t = Calc.clampd(time, 0, 1);

			return t * t;
		}
	};

	public static final Easing QUADRATIC_OUT = new Easing() {

		@Override
		public double get(double time)
		{
			double t = Calc.clampd(time, 0, 1);

			return 1 - (t - 1) * (t - 1);
		}
	};

	public static final Easing QUADRATIC = new Easing() {

		@Override
		public double get(double time)
		{
			double t = Calc.clampd(time, 0, 1);

			if (t < 0.5) return QUADRATIC_IN.get(2 * t) * 0.5;
			return 0.5 + QUADRATIC_OUT.get(2 * t - 1) * 0.5;
		}
	};

	public static final Easing CUBIC_IN = new Easing() {

		@Override
		public double get(double time)
		{
			double t = Calc.clampd(time, 0, 1);

			return t * t * t;
		}
	};
	public static final Easing CUBIC_OUT = new Easing() {

		@Override
		public double get(double time)
		{
			double t = Calc.clampd(time, 0, 1);

			return (t - 1) * (t - 1) * (t - 1) + 1;
		}
	};
	public static final Easing CUBIC = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);
			t /= d / 2;
			if (t < 1) return c / 2 * t * t * t + b;
			t -= 2;
			return c / 2 * (t * t * t + 2) + b;
		}
	};
	public static final Easing QUARTIC_IN = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);
			t /= d;
			return c * t * t * t * t + b;
		}
	};
	public static final Easing QUARTIC_OUT = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);

			t /= d;
			t--;
			return -c * (t * t * t * t - 1) + b;
		}
	};
	public static final Easing QUARTIC = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);

			t /= d / 2;
			if (t < 1) return c / 2 * t * t * t * t + b;
			t -= 2;
			return -c / 2 * (t * t * t * t - 2) + b;
		}
	};
	public static final Easing QUINTIC_IN = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);
			t /= d;
			return c * t * t * t * t * t + b;
		}
	};
	public static final Easing QUINTIC_OUT = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);
			t /= d;
			t--;
			return c * (t * t * t * t * t + 1) + b;
		}
	};
	public static final Easing QUINTIC_IN_OUT = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);
			t /= d / 2;
			if (t < 1) return c / 2 * t * t * t * t * t + b;
			t -= 2;
			return c / 2 * (t * t * t * t * t + 2) + b;
		}
	};
	public static final Easing SINE_IN = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);
			return -c * Math.cos(t / d * (Math.PI / 2)) + c + b;
		}
	};
	public static final Easing SINE_OUT = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);

			return c * Math.sin(t / d * (Math.PI / 2)) + b;
		}
	};
	public static final Easing SINE = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);
			return -c / 2 * (Math.cos(Math.PI * t / d) - 1) + b;
		}
	};
	public static final Easing EXPO_IN = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);

			return c * Math.pow(2, 10 * (t / d - 1)) + b;
		}
	};
	public static final Easing EXPO_OUT = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);

			return c * (-Math.pow(2, -10 * t / d) + 1) + b;
		}
	};
	public static final Easing EXPO = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);

			t /= d / 2;
			if (t < 1) return c / 2 * Math.pow(2, 10 * (t - 1)) + b;
			t--;
			return c / 2 * (-Math.pow(2, -10 * t) + 2) + b;
		}
	};
	public static final Easing CIRC_IN = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);
			t /= d;
			return -c * (Math.sqrt(1 - t * t) - 1) + b;
		}
	};
	public static final Easing CIRC_OUT = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);

			t--;
			return c * Math.sqrt(1 - t * t) + b;
		}
	};
	public static final Easing CIRC = new Easing() {

		@Override
		public double get(double time)
		{
			double d = 1;
			double t = time;
			double b = 0;
			double c = (1 - 0);

			t /= d / 2;
			if (t < 1) return -c / 2 * (Math.sqrt(1 - t * t) - 1) + b;
			t -= 2;
			return c / 2 * (Math.sqrt(1 - t * t) + 1) + b;
		}
	};
}
