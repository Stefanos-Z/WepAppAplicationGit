/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *  Database entity Questions
 * @author Levi
 */

@Entity
@Table(name = "users")
public class Users {

    private int userId;
    private String username;
    private String email;
    private String userPassword;
    private String phoneNumber;

    public Users(String username, String userPassword, String email, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.userPassword = userPassword;
        this.phoneNumber = phoneNumber;
    }
    
    
    public Users()
    {
        //needs to be empty for hibernate to work
    }
    
    
    
    @Id
    @GeneratedValue 
    @NotNull
    @Column(name="userId", unique = true)
    public int getUserId() {
        return userId;
    }



    @NotNull
    @Column(name="username")
    public String getUsername() {
        return username;
    }

    @NotNull
    @Column(name="email")
    public String getEmail() {
        return email;
    }

    @NotNull
    @Column(name="userPassword")
    public String getUserPassword() {
        return userPassword;
    }

    @NotNull
    @Column(name="phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    
    
    
    
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    

}