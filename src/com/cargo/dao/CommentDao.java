package com.cargo.dao;

import java.math.BigInteger;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cargo.model.Comment;



@Repository
public class CommentDao extends BaseDao<Comment> implements ICommentDao {

	public CommentDao(){
		super();
		setClazz(Comment.class);
	}
	
	
	public void deleteByCarId(long id){
		Session session = null;
		try {
		session = getCurrentSession();
		org.hibernate.Query query = session.createQuery("delete from Comment s where s.car=?")
				.setBigInteger(0, new BigInteger(Long.toString(id)));
		
		query.executeUpdate();
		} catch (HibernateException e) {
		e.printStackTrace();
		}
	}
	
}
