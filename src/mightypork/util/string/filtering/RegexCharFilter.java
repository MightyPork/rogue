package mightypork.util.string.filtering;


public class RegexCharFilter implements CharFilter {
	
	private final String formula;
	
	
	public RegexCharFilter(String regex)
	{
		this.formula = regex;
	}
	
	
	@Override
	public boolean isValid(char c)
	{
		return Character.toString(c).matches(formula);
	}
	
}
