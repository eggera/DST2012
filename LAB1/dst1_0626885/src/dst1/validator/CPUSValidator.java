package dst1.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CPUSValidator implements ConstraintValidator<CPUs, Integer>{

	private int min;
	private int max;
	
	@Override
	public void initialize(CPUs cpus) {
		this.min = cpus.min();
		this.max = cpus.max();
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext constraintContext) {
		if(value == null)
			return true;
		
		int val = value.intValue(); 
		if(val < min  ||  val > max)
			return false;
		
		return true;
	}
	
}
