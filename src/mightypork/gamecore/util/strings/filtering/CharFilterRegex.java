package mightypork.gamecore.util.strings.filtering;


public class CharFilterRegex implements CharFilter {
	
	private final String formula;
	
	
	public CharFilterRegex(String regex)
	{
		this.formula = regex;
	}
	
	
	@Override
	public boolean isValid(char c)
	{
		return Character.toString(c).matches(formula);
	}
	
}
