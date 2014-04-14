package mightypork.utils.math.num;


public class NumDigest {
	
	public final NumConst source;
	
	public final double value;
	
	
	public NumDigest(Num num) {
		this.value = num.value();
		this.source = num.freeze();
	}
}
