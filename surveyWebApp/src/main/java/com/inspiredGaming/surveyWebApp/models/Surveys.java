/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Creates an object corresponding to an Surveys tuple in the DB.
 * @authors     DALLOS
 * @version     1
 * @since       07/03/2018
 */

@Entity
@Table(name = "surveys")
public class Surveys {

    private int surveyId;
    private String surveyName;
    private Users users;
    private Date creationDate;
    
    /**
     * 
     * @param surveyName
     * @param users 
     */
    public Surveys(String surveyName, Users users)
    {
        this.surveyName = surveyName;
        this.users = users;
        this.creationDate = new Date();
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "userId", nullable = false)
    public Users getUsers()
    {
        return users;
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

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }    
    

}
