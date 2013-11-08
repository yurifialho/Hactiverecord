package br.com.secoti.activerecord;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.secoti.activerecord.exception.ActiveRecordException;

/**
 * Implementação java do padrão ActiveRecord para hibernate
 * 
 * @author Yuri Fialho
 * @since 08/11/2013
 * @version 0.1
 *
 * @param <T> Classe que extenderá o ActiveRecord 
 */
@SuppressWarnings("unchecked")
public abstract class ActiveRecord<T> implements IActiveRecord<T>{

	private static final long serialVersionUID = 1L;
	private Session session;
	
	public ActiveRecord() {
		
	}
	
	public ActiveRecord(Session session) {
		this.session = session;
	}

	public static <T extends ActiveRecord<T>> T findById(Class<T> clazz,
			Serializable id, Session session) throws ActiveRecordException {
		
		if(clazz == null || id == null || session == null) {
			throw new ActiveRecordException("Todos os parametros sao obrigatórios");
		}
		
		T obj = (T) session.load(clazz, id);
		if(obj != null) {
			obj.setSession(session);
		}
		return obj;
	}

	@Override
	public List<T> find() throws ActiveRecordException {
		if(this.session == null) {
			throw new ActiveRecordException("Sessao nao pode ser nula");
		}
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
		Query query = this.session.createQuery(hql.toString());
		if(params != null && !params.isEmpty()) {
			for(String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.list();
	}
	
	@Override
	public T save() throws ActiveRecordException {
		if(this.session == null) {
			throw new ActiveRecordException("Sessao nao pode ser nula");
		}
		
		this.session.saveOrUpdate(this);
		return (T) this;
	}
	
	@Override
	public T remove() throws ActiveRecordException {
		if(this.session == null) {
			throw new ActiveRecordException("Sessao nao pode ser nula");
		}
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
		this.session.delete(this);
		return (T) this;
	}

	/* HIBERNATE */
	
	protected void setSession(Session session) {
		this.session = session;
	}
	
	protected Session getSession() {
		return this.session;
	}
}