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
 * 
 * @author Levi
 */

@Entity
@Table(name = "survey_keys")

public class SurveyKeys {
    private String keyId;
    private boolean expired;
    private int surveyId;
    
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
    
    
    @Column(name="expired")
    public boolean getExpired()
    {
        return expired;
    }
    
    @Id
    @NotNull
    @Column(name="keyId", unique = true)
    public String getKeyId()
    {
        return keyId;
    }
    
    @NotNull
    @Column(name="surveyId", unique = true)
    public int getSurveyId()
    {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    
    
}
