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
@Table(name = "respondents")

public class Respondents {

    private Date surveyDate;
    private int respondentId;
    
    public Respondents()
    {
        //this.name = name;
        surveyDate = new Date();
    }
    
    /*
    public Respondents()
    {
        //needs to be empty for hibernate to work?
    }*/
    
        
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
    
       
    public void setSurveyDate(Date surveyDate)
    {
        this.surveyDate = surveyDate;
    }
    
    public void setRespondentId(int respondentId)
    {
        this.respondentId=respondentId;
    }

}