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
 *
 * @author Levi
 */

@Entity
@Table(name = "staff_emails")

public class StaffEmails {
    private String email;
    private int staffId;
    
    public StaffEmails(String email)
    {
        this.email = email;
    }
    
    public StaffEmails()
    {
        //needs to be empty for hibernate to work?
    }
    
    @Column(name="email")
    public String getEmail()
    {
        return email;
    }
    
    @Id
    @GeneratedValue //means hibernate will generate the value
    @NotNull
    @Column(name="staffId", unique = true)
    public int getStaffId()
    {
        return staffId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
    
    
}
