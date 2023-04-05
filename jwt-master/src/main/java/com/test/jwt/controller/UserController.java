package com.test.jwt.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.test.jwt.entity.ChangePasswordRequest;
import com.test.jwt.entity.TransferRequest;
import com.test.jwt.entity.User;
import com.test.jwt.service.UserService;


@RestController
public class UserController {
	
	
	 @Autowired
	    private UserService userService;

	    @PostConstruct
	    public void initRoleAndUser() {
	        userService.initRoleAndUser();
	    }

	    @PostMapping({"/registerNewUser"})
	    @CrossOrigin(origins = "http://localhost:4200")
	    public User registerNewUser(@RequestBody User user) {
	        return userService.registerNewUser(user);
	    }

	    @GetMapping({"/forAdmin"})
	    @PreAuthorize("hasRole('Admin')")
	    public String forAdmin(){
	        return "This URL is only accessible to the admin";
	    }
	    
	    
	    @GetMapping({"/forUser"})
	    @PreAuthorize("hasRole('User')")
	    public String forUser(){
	        return "This URL is only accessible to the User";
	    }
	    

	    @GetMapping({"/customer"})
	    @PreAuthorize("hasRole('Admin')")
	    
	    	public ResponseEntity<List<User>> getCustomers1() {
				List<User> list = userService.getAllCustomer();
				if (list.size() <= 0) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				}
				return ResponseEntity.of(Optional.of(list));
	    }
	    
	    
	    @DeleteMapping("/customer/{userAccountId}")
	    @PreAuthorize("hasRole('Admin')")
		public ResponseEntity<User> deleteCustomer(@PathVariable("userAccountId") String userAccountId) {
			try {
				this.userService.deleteCustomer(userAccountId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

			}

		}
	    
		@PutMapping("/customer/{userAccountId}")
		@PreAuthorize("hasRole('Admin')")
		public ResponseEntity<User> updateCustomer(@RequestBody User user,
				@PathVariable("userAccountId") String userAccountId) {
			try {
				this.userService.updateCustomer(user, userAccountId);

				return ResponseEntity.ok().body(user);

			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

			}

		}
		
		
		@PostMapping("/deposit")
		@PreAuthorize("hasRole('User')")
		

		public ResponseEntity<Double> deposit(@RequestBody User user) {

			userService.deposit(user);
			return ResponseEntity.ok().build();
		}
		
		@PostMapping("/withdraw")
		@CrossOrigin(origins = "http://localhost:4200")
		public ResponseEntity<Double> withdraw(@RequestBody User user) {
			userService.withdraw(user);
			return ResponseEntity.ok().build();
		}
		
		
		 @PostMapping("/transfer")
		 @PreAuthorize("hasRole('User')")
		 @CrossOrigin(origins = "http://localhost:4200")
		 public ResponseEntity<String> transferMoney(@RequestBody TransferRequest transferRequest) {
		       
		    	
		    	
		    	

		        if ( transferRequest.getAmount() == null) {
		            return ResponseEntity.badRequest().body("Invalid transfer request");
		        }
		    
		        
		       
		       boolean success = userService.transferMoney(transferRequest.getFromAccountId(), 
		                transferRequest.getToAccountId(), transferRequest.getAmount());
		        if (success) {
		            return ResponseEntity.ok("Money transferred successfully");
		        } else {
		            return ResponseEntity.badRequest().body("Error transferring money");
		        }
		    }
		 
		  @PostMapping("/users/change-password")
		  @PreAuthorize("hasRole('User')")
		    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {


			   if (!userService.validateCurrentPassword(changePasswordRequest.getUserAccountId(), changePasswordRequest.getCurrentPassword())) {
		            return new ResponseEntity<>("Current password is incorrect.", HttpStatus.BAD_REQUEST);
		        }

		        // validate new password and confirm new password
		        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())) {
		            return new ResponseEntity<>("New password and confirm new password do not match.", HttpStatus.BAD_REQUEST);
		        }
		        if (!userService.validateNewPassword(changePasswordRequest.getNewPassword())) {
		            return new ResponseEntity<>("New password does not meet complexity requirements.", HttpStatus.BAD_REQUEST);
		        }
			  
			  
			  
		        // update user's stored password
		        userService.updateUserPassword(changePasswordRequest.getUserAccountId(), changePasswordRequest.getNewPassword());

		        return new ResponseEntity<>("Password successfully changed.", HttpStatus.OK);
		    }
		}
	    
	    
	    
	    
	    
	
	    
	    
	  
	    
	  
	    
	    
	   
	    
		

	    
	    
	   
		
	   

	

