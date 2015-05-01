package com.cargo.dao;

import com.cargo.model.Account;

public interface IAccountDao extends IBaseDao<Account>{

	Account findByName(String string);
	
}
