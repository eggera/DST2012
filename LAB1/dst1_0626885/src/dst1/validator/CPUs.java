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


// Target specifies where this annotation may be placed
@Target ({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention (RUNTIME)
// Constraint specifies the ConstraintValidator that validates this annotation
@Constraint (validatedBy = CPUSValidator.class)
// Documented means that this annotation is included in javadocs
@Documented

// annotation providing standard methods
public @interface CPUs {

	// the message is displayed in case this annotation is not valid
	String message() default "muss zwischen 4 und 8 liegen";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	// returns a minimum for this constraint (attribute)
	int min();

	// returns a maximum for this constraint (attribute)
	int max();
}
