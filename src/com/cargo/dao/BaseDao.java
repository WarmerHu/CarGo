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

	@Override
	@SuppressWarnings("unchecked")
	public final T find(final long id) {
		return (T) getCurrentSession().get(clazz, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return getCurrentSession().createQuery("from " + clazz.getName()).list();
	}

	@Override
	public T create(T entity) {
		Preconditions.checkNotNull(entity);
        return this.find((Long) getCurrentSession().save(entity));
	}

	@Override
	public void update(T entity) {
		Preconditions.checkNotNull(entity);
        getCurrentSession().merge(entity);
	}

	@Override
	public void delete(T entity) {
		Preconditions.checkNotNull(entity);
        getCurrentSession().delete(entity);
	}

	@Override
	public void deleteById(long id) {
		final T entity = find(id);
        Preconditions.checkState(entity != null);
        delete(entity);
	}

	@Override
	public void deleteAll() {
		getCurrentSession().createQuery("DELETE from " + clazz.getName()).executeUpdate();
	}

	@Override
	public T first() {
		return findAll().get(0);
	}

}
