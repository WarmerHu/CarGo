package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.Comment;



@Repository
public class CommentDao extends BaseDao<Comment> implements ICommentDao {

	public CommentDao(){
		super();
		setClazz(Comment.class);
	}
	
}
