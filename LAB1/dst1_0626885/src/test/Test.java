package test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import dst1.model.*;

public class Test {

	public static void createEntities() {
		PersistenceUtil.getEntityManagerFactory();
	}
	
	
	
	public static void deleteEntities() {
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		UserDAO 		userDAO 		= new UserDAO		(entityManager);
		GridDAO 		gridDAO 		= new GridDAO		(entityManager);
		AdminDAO 		adminDAO 		= new AdminDAO		(entityManager);
		ClusterDAO 		clusterDAO 		= new ClusterDAO	(entityManager);
		ComputerDAO 	computerDAO 	= new ComputerDAO	(entityManager);
		
		entityManager.getTransaction().begin();
		
		userDAO.removeAllUsers();
		computerDAO.removeAllComputers();
		clusterDAO.removeAllClusters();
		gridDAO.removeAllGrids();
		adminDAO.removeAllAdmins();
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	/**
	 * Frees the resources and closes the connection to the persistence context
	 */
	public static void freeResources() {
		PersistenceUtil.freeResources();
	}
}
