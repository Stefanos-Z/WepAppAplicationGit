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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Creates an object corresponding to an StaffEmails tuple in the DB.
 * @authors     DALLOS
 * @version     1
 * @since       07/03/2018
 */

@Entity
@Table(name = "staff_emails")

public class StaffEmails {
    private String email;
    private int staffId;
    private int groupID;

    /**
     * constructor for class
     * @param email
     * @param groupID 
     */
    public StaffEmails(String email, int groupID) {
        this.email = email;
        this.groupID = groupID;
    }
    

    
    public StaffEmails()
    {
        //needs to be empty for hibernate to work
    }
    
    /**
     * getter for email
     * @return 
     */
    @Column(name="email")
    public String getEmail()
    {
        return email;
    }
    
    /**
     * getter for staffId
     * @return 
     */
    @Id
    @GeneratedValue //means hibernate will generate the value
    @NotNull
    @Column(name="staffId", unique = true)
    public int getStaffId()
    {
        return staffId;
    }
    
    /**
     * getter for groupId
     * @return 
     */
    @NotNull
    @Column(name="groupID")
    public int getGroupID()
    {
        return groupID;
    }

    /**
     * setter for email
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * setter for staffid
     * @param staffId 
     */
    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    /**
     * setter for groupId
     * @param groupID 
     */
    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
    
    
}
