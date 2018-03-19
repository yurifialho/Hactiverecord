package br.com.secoti.activerecord;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;

import br.com.secoti.activerecord.exception.ActiveRecordException;

/**
 * Implementacao java do padrao ActiveRecord para JPA
 * 
 * @author Yuri Fialho
 * @since 08/11/2013
 * @version 0.1
 *
 * @param <T> Classe que extendera o ActiveRecord 
 */
@SuppressWarnings("unchecked")
public abstract class ActiveRecord<T> implements IActiveRecord<T>{

	private static final long serialVersionUID = 1L;
	
	private EntityManager eManager;
	
	public ActiveRecord() {
		this.eManager = PersistenceConfigUtil.getInstance().getEntityManager();
	}
	
	public ActiveRecord(String persistentUnity) {
		this.eManager = PersistenceConfigUtil.getInstance(persistentUnity).getEntityManager();
	}
	
	public static <T extends ActiveRecord<T>> T findById(Class<T> clazz,
			Serializable id, String persistentUnity) throws ActiveRecordException {
		PersistenceConfigUtil.getInstance(persistentUnity);
		return findById(clazz, id);
	}
	public static <T extends ActiveRecord<T>> T findById(Class<T> clazz,
			Serializable id) throws ActiveRecordException {
		
		if(clazz == null || id == null ) {
			throw new ActiveRecordException("Todos os parametros sao obrigatorios");
		}
		
		EntityManager manager = PersistenceConfigUtil.getInstance().getEntityManager(); 
		
		T obj = (T) manager.find(clazz, id);
		return obj;
	}
	
	public static <T extends ActiveRecord<T>> List<T> findAll(Class<T> clazz) throws ActiveRecordException {
		EntityManager manager = PersistenceConfigUtil.getInstance().getEntityManager();
		
		StringBuilder hql = new StringBuilder();
		hql.append("Select c From ").append(clazz.getName()).append(" c");
	
		Query query = manager.createQuery(hql.toString());
		return query.getResultList();
	}
	
	public static <T extends ActiveRecord<T>> List<T> findAll(Class<T> clazz, String persistentUnity) throws ActiveRecordException {
		PersistenceConfigUtil.getInstance(persistentUnity);
		
		return findAll(clazz);
	}
	
	@Override
	public List<T> find() throws ActiveRecordException {
		StringBuilder hql = new StringBuilder();
			hql.append("Select c from ").append(this.getClass().getSimpleName()).append(" c");
		StringBuilder whereClasule = null;
		Map<String,Object> params = new HashMap<String, Object>();
		for(Field f : this.getClass().getDeclaredFields()) {
			for(Annotation a : f.getDeclaredAnnotations()) {
				if (a.annotationType().equals(Id.class)
						|| a.annotationType().equals(Column.class)) {
					try {
						f.setAccessible(true);
						Object value = f.get(this);
						if(value != null) {
							if(whereClasule == null) {
								whereClasule = new StringBuilder(" Where ");
							} else {
								whereClasule.append(" and ");
							}
							whereClasule.append("c.").append(f.getName()).append(" = :").append(f.getName());
							params.put(f.getName(), value);
						}
					} catch (Exception e) {
						throw new ActiveRecordException(e);
					} 
				}
					
			}
		}
		if(whereClasule != null) {
			hql.append(whereClasule);
		}
		Query query = this.eManager.createQuery(hql.toString());
		if(params != null && !params.isEmpty()) {
			for(String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.getResultList();
	}
	
	@Override
	public T save() throws ActiveRecordException {
		this.eManager.getTransaction().begin();
		try {
			this.eManager.persist(this.eManager.merge(this));
			this.eManager.flush();
			this.eManager.getTransaction().commit();
		} catch (Exception e) {
			this.eManager.getTransaction().rollback();
			throw new ActiveRecordException("Nao foi possivel salvar", e);
		}
		
		return (T) this;
	}
	
	@Override
	public T remove() throws ActiveRecordException {
		boolean hasId = false;
		campos : for(Field f : this.getClass().getDeclaredFields()) {
			for(Annotation a : f.getDeclaredAnnotations()) {
				if(a.annotationType().equals(Id.class)) {
					f.setAccessible(true);
					try {
						if(f.get(this) != null) {
							hasId = true;
							break campos;
						}
					} catch (Exception e) {
						throw new ActiveRecordException(e);
					} 
				}
			}
		}
		if(!hasId) {
			throw new ActiveRecordException("Id é obrigatorio para remoção do objeto");
		}
		try {
			this.eManager.getTransaction().begin();
			this.eManager.remove(this.eManager.merge(this));
			this.eManager.getTransaction().commit();
		} catch (Exception e) {
			this.eManager.getTransaction().rollback();
			throw new ActiveRecordException("Nao foi possivel remover", e);
		}
		
		return (T) this;
	}
}