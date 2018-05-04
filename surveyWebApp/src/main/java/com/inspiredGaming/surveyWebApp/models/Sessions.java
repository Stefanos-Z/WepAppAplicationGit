/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Creates an object corresponding to a Sessions tuple in the DB.
 * @authors     DALLOS
 * @version     1
 * @since       07/03/2018
 */

@Entity
@Table(name = "sessions")
public class Sessions {
        
    private String sessionId;
    
    private int userId;

    private Date expiryTime;
    /**
     * constructor for class
     * @param userId 
     */
    public Sessions(int userId) {
        sessionId = UUID.randomUUID().toString();
        this.userId = userId;
        
        expiryTime = new Date();
        expiryTime.setHours(expiryTime.getHours()+1);
        //expiryTime.setMinutes(expiryTime.getMinutes()+1);//makes the page valid for use only for 1 min
        
        
    }
    

    public Sessions()
    {
        //needed for hibernate 
    }
        
    /**
     * getter for sessionId
     * @return 
     */
    @Id
    @NotNull
    @Column(name="sessionId", unique = true)
    public String getSessionId()
    {
        return sessionId;
    }
    
    /**
     * getter for userId
     * @return 
     */
    @NotNull
    @Column(name="userId")
    public int getUserId()
    {
        return userId;
    }
    
    /**
     * getter for exiryTime
     * @return 
     */
    @NotNull
    @Column(name="expiryDate")
    public Date getExpiryTime()
    {
        return expiryTime;
    }

    /**
     * setter for sessionId
     * @param sessionId 
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    /**
     * setter for userId
     * @param userId 
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    /**
     * setter for expiryTime
     * @param expiryTime 
     */
    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    
    
}