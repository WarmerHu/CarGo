package com.cargo.dao;

import java.util.List;

public interface IBaseDao<T> {
	public T find(final long id);

    public List<T> findAll();

    public T create(final T entity);

    public T update(final T entity);

    public void delete(final T entity);

    public void deleteById(final long id);
    
    public void deleteAll();
    
    public T first();
}
