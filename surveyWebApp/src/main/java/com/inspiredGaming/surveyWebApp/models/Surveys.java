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
@Table(name = "surveys")
public class Surveys {

    private int surveyId;
    private String surveyName;
    private int userId;
    private Date creationDate;
    
    public Surveys(String surveyName, int userId)
    {
        this.surveyName = surveyName;
        this.userId = userId;
        this.creationDate = new Date();;
    }
    
    public Surveys()
    {
        //needs to be empty for hibernate to work
    }
    
        
    @Id
    @GeneratedValue 
    @NotNull
    @Column(name="surveyId", unique = true)
    public int getSurveyId()
    {
        return surveyId;
    }
    
    
    @NotNull
    @Column(name="surveyName")
    public String getSurveyName()
    {
        return surveyName;
    }
    
    @NotNull
    @Column(name="userId")
    public int getUserId()
    {
        return userId;
    }
    
    
    @Column(name="creationDate")
    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }    
    

}
