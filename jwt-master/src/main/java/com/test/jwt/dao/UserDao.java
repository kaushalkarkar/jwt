package com.test.jwt.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.test.jwt.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, String> 
{

	
	
	public User findByuserAccountId(String userAccountId);
	

	
	
	
	
}
