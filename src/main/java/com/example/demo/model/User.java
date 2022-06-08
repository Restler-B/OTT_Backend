package com.example.demo.model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", schema = "user_details",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })

public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotBlank
  @Size(max = 50)
  private String username;
  
  @NotBlank
  @Size(max = 80)
  @Email
  private String email;
  
  @NotBlank
  @Size(max = 120)
  private String password;
  
  private static final long OTP_VALID_DURATION = 5 * 60* 1000;   // 5 minutes
  
  @Column(name = "otp")
  private String oneTimePassword;
   
  @Column(name = "otp_requested_time")
  private Date otpRequestedTime;
   
  
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", schema = "user_details",
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id"))
  
  private Set<Role> roles = new HashSet<>();
  
  public User() {
  }
  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  
  public Set<Role> getRoles() {
    return roles;
  }
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
  public String getOneTimePassword() {
		return oneTimePassword;
  }
  public void setOneTimePassword(String oneTimePassword) {
	this.oneTimePassword = oneTimePassword;
  }
  public Date getOtpRequestedTime() {
		return otpRequestedTime;
  }
  public void setOtpRequestedTime(Date otpRequestedTime) {
	this.otpRequestedTime = otpRequestedTime;
  }
	
  public boolean isOTPExpired() {
    if (this.getOneTimePassword() == null) {
	        return false;
    }
	 
    long currentTimeInMillis = System.currentTimeMillis();
    long otpRequestedTimeInMillis = this.otpRequestedTime.getTime();
    
    System.out.println( (otpRequestedTimeInMillis+OTP_VALID_DURATION)+"   " +currentTimeInMillis);
	 
    if (otpRequestedTimeInMillis + OTP_VALID_DURATION  < currentTimeInMillis) {
	    // OTP expires
    	return false;
    }
	 
    	return true;
	}
}