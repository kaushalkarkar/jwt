package com.test.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.test.jwt.dao.RoleDao;
import com.test.jwt.dao.UserDao;
import com.test.jwt.entity.Role;
import com.test.jwt.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        adminUser.setUserAadharNo("12345678");
        adminUser.setUserAccountId("121212");
        adminUser.setUserAccountType("saving");
        adminUser.setUserEmailId("kaushal@gmail.com");
        adminUser.setUserPhoneNumber("7433035294");
        adminUser.setUserAmount(3232.0);
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

        User user = new User();
       
        user.setUserName("kaushal123");
        user.setUserPassword(getEncodedPassword("kaushal@123"));
        user.setUserFirstName("kaushal");
        user.setUserLastName("patel");
        user.setUserAadharNo("12345678");
        user.setUserAccountId("12122323");
        user.setUserAccountType("saving");
        user.setUserEmailId("kaushal@gmail.com");
        user.setUserPhoneNumber("7433035294");
        user.setUserAmount(3232.0);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userDao.save(user);
    }

    public User registerNewUser(User user)
    {
    	
    	user.setUserName(user.getUserName());
    	user.setUserPassword(getEncodedPassword(user.getUserPassword()));
    	user.setUserFirstName(user.getUserFirstName());
    	user.setUserLastName(user.getUserLastName());
    	
    	
         
     
     Role role = roleDao.findById("User").get();
     Set<Role> userRoles = new HashSet<>();
     userRoles.add(role);
     user.setRole(userRoles);
  
        
        return userDao.save(user);
    }
    
    public String getEncodedPassword(String password) 
    {
        return passwordEncoder.encode(password);
    }

	
	

	

	public List<User> getAllCustomer() 
	{
		List<User> list = (List<User>) this.userDao.findAll();
		return list;
	}

	public void deleteCustomer(String userAccountId)
	{
	
		userDao.findByuserAccountId(userAccountId);
		
	}

	public void updateCustomer(User user, String userAccountId) 
	{
		
		user.setUserAccountId(userAccountId);
		userDao.save(user);
		
		
	}

	public void deposit(User userData ) {
		
			
			User user = userDao.findByuserAccountId(userData.getUserAccountId());
			if (user == null)
			{
				throw new RuntimeException("PLEASE ENTER UNDER 20000 FUNDS");
			}
			
			user.setUserAmount(user.getUserAmount() + (userData.getUserAmount()));
			userDao.save(user);

		
		
	}
	
	
	public void withdraw(User userData) {
		try {
			User user = userDao.findByuserAccountId(userData.getUserAccountId());

			if (user.getUserAmount() < userData.getUserAmount())
			{
				throw new RuntimeException("INSUFFICIENT FUNDS");
			}	
				user.setUserAmount(user.getUserAmount() - (userData.getUserAmount()));
				userDao.save(user);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public boolean transferMoney( String fromAccountId, String toAccountId, Double amount ) {
			
			User fromUser = userDao.findByuserAccountId(fromAccountId);
		    User toUser = userDao.findByuserAccountId(toAccountId);
		    

			if (fromUser.getUserAmount() < amount)
			{
				throw new RuntimeException("Insufficient balance");
			}
		    
		    
		    
			
		    fromUser.setUserAmount(fromUser.getUserAmount() - amount);
		    userDao.save(fromUser);
		    
		    
			toUser.setUserAmount(toUser.getUserAmount() + amount);
			userDao.save(toUser);
			return true;
			}

	
	public User updatepassword(String userAccountId, String newPassword) {
		User user=userDao.findByuserAccountId(userAccountId);
		user.setUserPassword(newPassword);
		return userDao.save(user);
	}

	
	
	public boolean validateCurrentPassword(String userAccountId, String currentPassword) {
        User user = userDao.findByuserAccountId(userAccountId);
        if (user == null) {
        	throw new RuntimeException("");
        }
        return new BCryptPasswordEncoder().matches(currentPassword, user.getUserPassword());
    }

    public boolean validateNewPassword(String newPassword) {
        // Add any password complexity requirements here
        return true;
    }

    
    public void updateUserPassword(String userAccountId, String newPassword) {
        User user = userDao.findByuserAccountId(userAccountId);
        if (user == null) {
        	throw new RuntimeException("user not found");
        }
        user.setUserPassword(new BCryptPasswordEncoder().encode(newPassword));
        userDao.save(user);
    }
	

			    
}		    
		   
		
			       
			          
			        
			
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	



	
	
	



		

	

	
	
