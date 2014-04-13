package mightypork.utils.math.num;


import mightypork.utils.math.rect.Rect;


/**
 * Math operations for numbers
 * 
 * @author MightyPork
 * @param <N> functions return type
 */
interface NumMath<N extends NumMath<N>> extends Num {
	
	double CMP_EPSILON = 0.0000001;
	
	
	N add(Num addend);
	
	
	N sub(Num subtrahend);
	
	
	N mul(Num factor);
	
	
	N div(Num factor);
	
	
	N perc(Num percent);
	
	
	N max(Num other);
	
	
	N min(Num other);
	
	
	N pow(Num other);
	
	
	N average(Num other);
	
	
	N add(double addend);
	
	
	N sub(double subtrahend);
	
	
	N mul(double factor);
	
	
	N div(double factor);
	
	
	N perc(double percent);
	
	
	N neg();
	
	
	N abs();
	
	
	N max(double other);
	
	
	N min(double other);
	
	
	N pow(double other);
	
	
	N square();
	
	
	N cube();
	
	
	N sqrt();
	
	
	N cbrt();
	
	
	N sin();
	
	
	N cos();
	
	
	N tan();
	
	
	N asin();
	
	
	N acos();
	
	
	N atan();
	
	
	N round();
	
	
	N floor();
	
	
	N ceil();
	
	
	N signum();
	
	
	N half();
	
	
	N average(double other);
	
	
	boolean lt(Num other);
	
	
	boolean lte(Num other);
	
	
	boolean gt(Num other);
	
	
	boolean gte(Num other);
	
	
	boolean eq(Num other);
	
	
	boolean lt(double other);
	
	
	boolean lte(double other);
	
	
	boolean gt(double other);
	
	
	boolean gte(double other);
	
	
	boolean eq(double other);
	
	
	boolean isNegative();
	
	
	boolean isPositive();
	
	
	boolean isZero();
	
	
	/**
	 * Make a square rect with this side, positioned at 0,0
	 * 
	 * @return new rect
	 */
	Rect box();
}
