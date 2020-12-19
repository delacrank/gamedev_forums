package com.juan.gamedevforums.persistence.model;

import java.util.Collection;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.
                                          SimpleGrantedAuthority;

import org.jboss.aerogear.security.otp.api.Base32;

@Entity
@Table(name = "user_account")
public class User {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    private String email;

    private String username;
    
    @Column(length = 90)
    private String password;

    private boolean enabled;

    private String secret;

    private String image;

    private String bio;

    @JsonIgnore 
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES",
	       joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "id"),
	       inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "id"))
    private Collection<Role> roles;

    public User() {
        super();
        this.secret = Base32.random();
        this.enabled = false;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public boolean isEnabled() {
	return enabled;
    }

    public void setEnabled(boolean enabled) {
	this.enabled = enabled;
    }

    public String getSecret() {
	return secret;
    }

    public void setSecret(String secret) {
	this.secret = secret;
    }

    public Collection<Role> getRoles() {
	return roles;
    }
    
    public void setRoles(Collection<Role> roles) {
	this.roles = roles;
    }

    public String getImage() {
	return image;
    }

    public void setImage(String image) {
	this.image = image;
    }

    public String getBio() {
	return bio;
    }

    public void setBio(String bio) {
	this.bio = bio;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User [id=")
                .append(id)
                .append(", username=").append(username)
                .append(", email=").append(email)
                .append(", enabled=").append(enabled)
                .append(", password=").append(password)
                .append(", secret=").append(secret)
	    .append(", roles=").append(roles)
	        .append(", image=").append(image)
	        .append(", bio=").append(bio)
                .append("]");
        return builder.toString();
    }

}
