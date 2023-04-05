package com.test.jwt.entity;

import javax.persistence.*;



import java.util.Set;

@Entity
public class User {

	    @Id
	    
	    private String userName;
	    private String userFirstName;
	    private String userLastName;
	    private String userPassword;
	    private String userAccountId;
	    private String userPhoneNumber;
	    private String userAadharNo;
	    private String userAccountType;
	    private String userEmailId;
	    private Double userAmount;
	    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	    @JoinTable(name = "USER_ROLE",
	            joinColumns = {
	                    @JoinColumn(name = "USER_ID")
	            },
	            inverseJoinColumns = {
	                    @JoinColumn(name = "ROLE_ID")
	            }
	    )
	    private Set<Role> role;
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getUserFirstName() {
			return userFirstName;
		}
		public void setUserFirstName(String userFirstName) {
			this.userFirstName = userFirstName;
		}
		public String getUserLastName() {
			return userLastName;
		}
		public void setUserLastName(String userLastName) {
			this.userLastName = userLastName;
		}
		public String getUserPassword() {
			return userPassword;
		}
		public void setUserPassword(String userPassword) {
			this.userPassword = userPassword;
		}
		public String getUserAccountId() {
			return userAccountId;
		}
		public void setUserAccountId(String userAccountId) {
			this.userAccountId = userAccountId;
		}
		public String getUserPhoneNumber() {
			return userPhoneNumber;
		}
		public void setUserPhoneNumber(String userPhoneNumber) {
			this.userPhoneNumber = userPhoneNumber;
		}
		public String getUserAadharNo() {
			return userAadharNo;
		}
		public void setUserAadharNo(String userAadharNo) {
			this.userAadharNo = userAadharNo;
		}
		public String getUserAccountType() {
			return userAccountType;
		}
		public void setUserAccountType(String userAccountType) {
			this.userAccountType = userAccountType;
		}
		public String getUserEmailId() {
			return userEmailId;
		}
		public void setUserEmailId(String userEmailId) {
			this.userEmailId = userEmailId;
		}
		public Double getUserAmount() {
			return userAmount;
		}
		public void setUserAmount(Double userAmount) {
			this.userAmount = userAmount;
		}
		public Set<Role> getRole() {
			return role;
		}
		public void setRole(Set<Role> role) {
			this.role = role;
		}
		

	   

	

	


	
	
    
    

   
}
