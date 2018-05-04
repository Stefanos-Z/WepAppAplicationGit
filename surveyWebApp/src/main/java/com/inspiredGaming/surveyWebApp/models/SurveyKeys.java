/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Creates an object corresponding to an SurveyKeys tuple in the DB.
 * @authors     DALLOS
 * @version     1
 * @since       07/03/2018
 */

@Entity
@Table(name = "survey_keys")

public class SurveyKeys {
    private String keyId;
    private boolean expired;
    private int surveyId;
    
    /**
     * constructor for class
     * @param surveyId 
     */
    public SurveyKeys(int surveyId)
    {
        keyId = UUID.randomUUID().toString();
        expired = false;
        this.surveyId = surveyId;
    }
    
    public SurveyKeys()
    {
        //default
    }
    
    /**
     * getter for expired
     * @return 
     */
    @Column(name="expired")
    public boolean getExpired()
    {
        return expired;
    }
    
    /**
     * getter for keyId
     * @return 
     */
    @Id
    @NotNull
    @Column(name="keyId", unique = true)
    public String getKeyId()
    {
        return keyId;
    }
    
    /**
     * getter for surveyId
     * @return 
     */
    @NotNull
    @Column(name="surveyId", unique = true)
    public int getSurveyId()
    {
        return surveyId;
    }

    /**
     * setter for surveyId
     * @param surveyId 
     */
    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * setter for keyId
     * @param keyId 
     */
    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
    
    /**
     * setter for expired
     * @param expired 
     */
    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    
    
}
