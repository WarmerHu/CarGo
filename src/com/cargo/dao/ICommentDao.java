package com.cargo.dao;

import com.cargo.model.Comment;


public interface ICommentDao extends IBaseDao<Comment>{

	public void deleteByCarId(long id);
}
