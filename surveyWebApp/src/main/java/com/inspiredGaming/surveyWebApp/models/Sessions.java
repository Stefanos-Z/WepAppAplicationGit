/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models;

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
 *
 * @author oneZt
 */

@Entity
@Table(name = "sessions")
public class Sessions {
        
    private String sessionId;
    
    private int userId;

    public Sessions(int userId) {
        sessionId = UUID.randomUUID().toString();
        this.userId = userId;
    }
    

    public Sessions()
    {
        
    }
        
    @Id
    @NotNull
    @Column(name="sessionId", unique = true)
    public String getSessionId()
    {
        return sessionId;
    }
    
    
    @NotNull
    @Column(name="userId")
    public int getUserId()
    {
        return userId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    
    
}