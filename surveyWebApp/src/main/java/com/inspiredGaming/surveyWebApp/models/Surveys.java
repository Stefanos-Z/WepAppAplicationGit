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
     * Main Constructor For survey 
     * @param surveyName the name of the survey
     * @param users the user of the survey (creator)
     */
    public Surveys(String surveyName, Users users)
    {
        this.surveyName = surveyName;
        this.users = users;
        this.creationDate = new Date();
    }
    
    /**
     * default constructor for hibernate to work
     */
    public Surveys()
    {
        //needs to be empty for hibernate to work
    }
    
    /**
     * Gets a Surveys id
     * @return the id of the survey 
     */    
    @Id
    @GeneratedValue 
    @NotNull
    @Column(name="surveyId", unique = true)
    public int getSurveyId()
    {
        return surveyId;
    }
    
    /**
     * Gets the name of a survey
     * @return the surveys name
     */
    @NotNull
    @Column(name="surveyName")
    public String getSurveyName()
    {
        return surveyName;
    }
    
    /**
     * Gets the user of which created this survey
     * @return an instance of a Users
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "userId", nullable = false)
    public Users getUsers()
    {
        return users;
    }
    
    /**
     * Gets the creation date of a survey
     * @return the creation date 
     */
    @Column(name="creationDate")
    public Date getCreationDate()
    {
        return creationDate;
    }

    /**
     * Sets the surveysID to
     * @param surveyId the new surveys ID
     */
    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * Sets the survey name of the survey
     * @param surveyName the new name of the survey
     */
    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    /**
     * Sets the Users(-creator) of this survey 
     * @param users the new Users
     */
    public void setUsers(Users users) {
        this.users = users;
    }

    /**
     * sets the creation date of this survey 
     * @param creationDate the new creation date of this survey
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }    
    

}
