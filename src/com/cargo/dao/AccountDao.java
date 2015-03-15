package com.cargo.dao;

import org.springframework.stereotype.Repository;
import com.cargo.dao.BaseDao;
import com.cargo.model.Account;


@Repository
public class AccountDao extends BaseDao<Account> implements IAccountDao {

	public AccountDao(){
		super();
		setClazz(Account.class);
	}
	
}
