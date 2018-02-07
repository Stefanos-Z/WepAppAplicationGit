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
@Table(name = "respondent_answers")

public class RespondentAnswers {

    private int respondentAnswerId;
    private int answerId;
    private int respondentId;
    private String answerText;
    
    public RespondentAnswers(int answerId, int respondentId, String answerText)
    {
        this.answerId = answerId;
        this.respondentId = respondentId;
        this.answerText = answerText;
    }
    
    
    public RespondentAnswers()
    {
        //needs to be empty for hibernate to work?
    }
    
        
    @Id
    @GeneratedValue //means hibernate will generate the value
    @NotNull
    @Column(name="respondentAnswerId", unique = true)
    public int getRespondentAnswerId()
    {
        return respondentAnswerId;
    }
    
    
    @NotNull
    @Column(name="answerId")
    public int getAnswerId()
    {
        return answerId;
    }
    
    @NotNull
    @Column(name="respondentId")
    public int getRespondentId()
    {
        return respondentId;
    }
    
    
    @Column(name="answerText")
    public String getAnswerText()
    {
        return answerText;
    }
    
    public void setRespondentAnswerId(int respondentAnswerId)
    {
        this.respondentAnswerId=respondentAnswerId;
    } 
    
    public void setAnswerId(int answerId)
    {
        this.answerId = answerId;
    }
    
    public void setRespondentId(int respondentId)
    {
        this.respondentId=respondentId;
    }
    
    public void setAnswerText(String answerText)
    {
        this.answerText=answerText;
    }  

}
