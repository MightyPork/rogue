package mightypork.utils.string.validation;


public class FilenameCharValidator extends CharValidatorRegex {
	
	public FilenameCharValidator() {
		super("[a-zA-Z0-9 +\\-.,_%@#$!'\"]");
	}
	
}
