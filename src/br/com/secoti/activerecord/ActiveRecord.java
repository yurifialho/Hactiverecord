package br.com.secoti.activerecord;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

import br.com.secoti.activerecord.exception.ActiveRecordException;

public abstract class ActiveRecord<T> implements IActiveRecord<T>{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public static <T extends ActiveRecord<T>> T findById(Class<T> clazz, Serializable id) throws ActiveRecordException {
		Session session = null;
		return (T) session.load(clazz, id);
	}

	@Override
	public List<T> find(Object obj) throws ActiveRecordException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T save(Object obj) throws ActiveRecordException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T update(Object obj) throws ActiveRecordException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* --- HIBERNATE --- */
	
	public Session getSession() {
		return null;
	}

}
