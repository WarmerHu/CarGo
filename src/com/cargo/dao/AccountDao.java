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
	
	public Account findByName(String name){
		return (Account) getCurrentSession().createQuery("from Account as a where a.name = ?").setString(0, name).uniqueResult();
	}
	
}
