package com.cargo.dao;

import org.springframework.stereotype.Repository;
import com.cargo.dao.BaseDao;
import com.cargo.model.Account;
import com.cargo.model.Car;


@Repository
public class AccountDao extends BaseDao<Account> implements IAccountDao {

	public AccountDao(){
		super();
		setClazz(Account.class);
	}
	
	public Account findByName(String name){
		return (Account) getCurrentSession().createQuery("from Account as a where a.name = ?").setString(0, name).uniqueResult();
	}
	
	@Override
	public void update(Account account) {
		Account old = find(account.getId());
		if(account.getEmail() == null) account.setEmail(old.getEmail());
		if(account.getTelephone() == null) account.setTelephone(old.getTelephone());
		if(account.getAuth_token() == null) account.setAuth_token(old.getAuth_token());
		if(account.getGender() == null) account.setGender(old.getGender());
		if(account.getName() == null) account.setName(old.getName());
		if(account.getPassword() == null) account.setPassword(old.getPassword());
		
		if(account.getType() == null) account.setType(old.getType());
		super.update(account);
	}
	
}
