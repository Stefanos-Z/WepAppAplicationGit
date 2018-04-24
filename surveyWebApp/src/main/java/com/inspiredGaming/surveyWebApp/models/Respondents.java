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
 * Creates an object corresponding to an Respondents tuple in the DB.
 * @authors     DALLOS
 * @version     1
 * @since       07/03/2018
 */

@Entity
@Table(name = "respondents")

public class Respondents {

    private Date surveyDate;
    private int respondentId;
    private int surveyId;
    
    public Respondents(int surveyId)
    {
        //this.name = name;
        surveyDate = new Date();
        this.surveyId = surveyId;
    }
    
    
    public Respondents()
    {
        //needs to be empty for hibernate to work?
    }
    
        
    @Id
    @GeneratedValue //means hibernate will generate the value
    @NotNull
    @Column(name="respondentId", unique = true)
    public int getRespondentId()
    {
        return respondentId;
    }
    
    @NotNull
    @Column(name="surveyDate")
    public Date getSurveyDate()
    {
        return surveyDate;
    }

    @NotNull
    @Column(name="surveyId")
    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }
    
       
    public void setSurveyDate(Date surveyDate)
    {
        this.surveyDate = surveyDate;
    }
    
    public void setRespondentId(int respondentId)
    {
        this.respondentId=respondentId;
    }

}