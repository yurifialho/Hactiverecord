package br.com.secoti.activerecord;

import java.io.Serializable;
import java.util.List;

import br.com.secoti.activerecord.exception.ActiveRecordException;

/**
 * Interface que define o contrato com os principais métodos do activerecord
 * 
 * @author yurifialho
 * @since 02/11/2013
 */
public interface IActiveRecord<T> extends Serializable {
	
	public List<T> find(T obj) throws ActiveRecordException;
	
	public T save(T obj) throws ActiveRecordException;
	
	public T update(T obj) throws ActiveRecordException;
	
}
