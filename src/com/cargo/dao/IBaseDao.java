package com.cargo.dao;

import java.util.List;

import org.hibernate.Session;

//test
public interface IBaseDao<T> {
	public Session getCurrentSession();
	public T find(final long id);
	
//	public List<T> find(T t);

    public List<T> findAll();

    public T create(final T entity);

    public void update(final T entity);

    public void delete(final T entity);

    public void deleteById(final long id);
    
//    public void deleteById(final String id);
    
    public void deleteAll();
    
    public T first();
}
