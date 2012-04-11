package dst1.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target ({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention (RUNTIME)
@Constraint (validatedBy = CPUSValidator.class)
@Documented
public @interface CPUs {

	String message() default "muss zwischen 4 und 8 liegen";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	int min();
	
	int max();
}
