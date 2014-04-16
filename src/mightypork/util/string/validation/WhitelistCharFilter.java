package mightypork.util.string.validation;


public class WhitelistCharFilter implements CharFilter {
	
	private final String whitelist;
	
	
	public WhitelistCharFilter(String allowed) {
		this.whitelist = allowed;
	}
	
	
	@Override
	public boolean isValid(char c)
	{
		return whitelist.contains(Character.toString(c));
	}
	
}
