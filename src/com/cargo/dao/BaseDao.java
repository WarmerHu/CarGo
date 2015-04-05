package com.cargo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;

public class BaseDao<T> implements IBaseDao<T> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	private Class<T> clazz;
	
	protected final void setClazz(final Class<T> clazzToSet) {
        this.clazz = Preconditions.checkNotNull(clazzToSet);
    }

	@SuppressWarnings("unchecked")
	public final T find(final long id) {
		return (T) getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return getCurrentSession().createQuery("from " + clazz.getName()).list();
	}

	public T create(T entity) {
		Preconditions.checkNotNull(entity);
        return this.find((Long) getCurrentSession().save(entity));
	}

	public T update(T entity) {
		Preconditions.checkNotNull(entity);
        getCurrentSession().update(entity);
        return entity;
	}

	public void delete(T entity) {
		Preconditions.checkNotNull(entity);
        getCurrentSession().delete(entity);
	}

	public void deleteById(long id) {
		final T entity = find(id);
        Preconditions.checkState(entity != null);
        delete(entity);
	}

}
