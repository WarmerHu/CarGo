package com.cargo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cargo.model.Account;
import com.cargo.model.Order;


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
		if(account.getName() == null)account.setName(old.getName());
		if(account.getEmail() == null) account.setEmail(old.getEmail());
		if(account.getTelephone() == null) account.setTelephone(old.getTelephone());
		if(account.getAuth_token() == null) account.setAuth_token(old.getAuth_token());
		if(account.getGender() == null) account.setGender(old.getGender());
		if(account.getPassword() == null) account.setPassword(old.getPassword());
		if(account.getAddress() == null) account.setAddress(old.getAddress());
		if(account.getCity() == null) account.setCity(old.getCity());
		if(account.getType() == null) account.setType(old.getType());
		super.update(account);
	}

	@Override
	public List<Order> getOrders(Long id) {
		@SuppressWarnings("unchecked")
		List<Order> list = getCurrentSession().createQuery("from Order as o where o.buyer = ?")
						.setEntity(0, find(id))
						.list();
		return list;
	}
	
}
