package br.com.secoti.activerecord;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceConfigUtil {

	private static PersistenceConfigUtil instance;
	public static final String DEFAULT_PERSISTENCE_UNIT_NAME = "default";
	
	private String persistenceUnitName;
	
	
	private PersistenceConfigUtil(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}
	
	public String getPersistenceUnit() throws IOException {
		if(this.persistenceUnitName == null) {
			this.persistenceUnitName = PersistenceConfigUtil.DEFAULT_PERSISTENCE_UNIT_NAME;
		}
		
		return this.persistenceUnitName;
	}
	
	public EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(this.persistenceUnitName);
		EntityManager manager = factory.createEntityManager();
		return manager;
	}
	
	public static PersistenceConfigUtil getInstance() {
		if(PersistenceConfigUtil.instance == null)
			return getInstance(PersistenceConfigUtil.DEFAULT_PERSISTENCE_UNIT_NAME);
		
		return PersistenceConfigUtil.instance;
	}
	
	public static PersistenceConfigUtil getInstance(String persistenceUnitName) {
		if(persistenceUnitName == null)
			persistenceUnitName = PersistenceConfigUtil.DEFAULT_PERSISTENCE_UNIT_NAME;
		if(PersistenceConfigUtil.instance == null) {
			return new PersistenceConfigUtil(persistenceUnitName);
		} else {
			return PersistenceConfigUtil.instance;
		}
	}
}
