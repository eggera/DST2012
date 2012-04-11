package test;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import dst1.model.Computer;
import dst1.model.PersistenceUtil;

public class Test3 {

	public static void test() {
		
		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 3  --------------\n\n");
		
		
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		Calendar creation = Calendar.getInstance();
		Calendar lastUpdate = Calendar.getInstance();
		creation.set(2012, 01, 01);
		lastUpdate.set(2012, 02, 02);
		
		
		Computer validComputer = new Computer("computer1", 6, "AUT-VIE@1510", 
											creation.getTime(), lastUpdate.getTime());
		
		
		
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		
		System.out.println("Validating valid computer instance: ");
		Set<ConstraintViolation<Computer>> constraintViolations = validator.validate(validComputer); 
		
		if(constraintViolations.size() == 0)
			System.out.println("No errors");
		else {
			Iterator<ConstraintViolation<Computer>> iter = constraintViolations.iterator();
			
			while(iter.hasNext()) {
				System.out.println(iter.next().getMessage());
			}
		}
		
		
		creation.set(2012, 10, 10);
		lastUpdate.set(2012, 12, 12);
		
		Computer invalidComputer = new Computer("foo", 2, "AUTVIE1111", 
											creation.getTime(), lastUpdate.getTime());
		
		System.out.println("\nValidating invalid computer instance: ");
		constraintViolations = validator.validate(invalidComputer); 
		
		if(constraintViolations.size() == 0)
			System.out.println("No errors");
		else {
			Iterator<ConstraintViolation<Computer>> iter = constraintViolations.iterator();
			
			while(iter.hasNext()) {
				ConstraintViolation<Computer> cv = iter.next();
				System.out.println(cv.getPropertyPath()+": "+cv.getMessage());
			}
		}
		
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
