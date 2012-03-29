package dst1.model;

import javax.persistence.*;

public class PersistenceUtil {

	private static EntityManagerFactory entityManagerFactory;

	
	/**
	 * Get the only instance of EntityManagerFactory
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		// an EntityManagerFactory is created once for an application
		// the name must match the one specified for persistence-unit in persistence.xml
		if(entityManagerFactory == null)
			entityManagerFactory = Persistence.createEntityManagerFactory( "grid" );
		return entityManagerFactory;
	}
	
	/**
	 * close the EntityManagerFactory instance
	 */
	public static void freeResources() {
		if(entityManagerFactory != null) {
			entityManagerFactory.close();
			entityManagerFactory = null;
		}
	}
}
