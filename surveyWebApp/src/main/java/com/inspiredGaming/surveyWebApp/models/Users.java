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
 * Creates an object corresponding to a Users tuple in the DB.
 * @authors     DALLOS
 * @version     1
 * @since       07/03/2018
 */

@Entity
@Table(name = "users")
public class Users {

    private int userId;
    private String username;
    private String email;
    private String userPassword;
    private String phoneNumber;
    private String role;

    /**
     * main constructor for User where needed to provide
     * @param username the username of the user
     * @param userPassword the password of the user
     * @param email the email of the user
     * @param phoneNumber the phone number of the user
     * @param role the type/role of the user
     */
    public Users(String username, String userPassword, String email, String phoneNumber, String role) {
        this.username = username;
        this.email = email;
        this.userPassword = userPassword;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    /**
     * Method that gets the role 
     * @return the role of the user
     */
    @NotNull
    @Column(name="role", unique = true)
    public String getRole() {
        return role;
    }

    /**
     * Sets the User's role to the new
     * @param role the new role of the user
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    /**
     * empty constructor for hibernate to work
     */
    public Users()
    {
        //needs to be empty for hibernate to work
    }    
    
    /**
     * Gets the User's id
     * @return the user's ID
     */
    @Id
    @GeneratedValue 
    @NotNull
    @Column(name="userId", unique = true)
    public int getUserId() {
        return userId;
    }



    /**
     * Gets the user's username
     * @return the username
     */
    @NotNull
    @Column(name="username")
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user's email
     * @return the email
     */
    @NotNull
    @Column(name="email")
    public String getEmail() {
        return email;
    }

    /**
     * Gets the user's password
     * @return the password
     */
    @NotNull
    @Column(name="userPassword")
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Gets the user's phone number
     * @return the phone number
     */
    @NotNull
    @Column(name="phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    
    
    
    /**
     * Sets the user's user id
     * @param userId the new ID of the user
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Sets the User's username
     * @param the new username for the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the User's email
     * @param email the new email of the User
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the password of a User
     * @param userPassword the new password for the User
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Sets the phone number of a User
     * @param phoneNumber the new phone number of the User 
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    

}
