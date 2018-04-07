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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    //private int answerId;
    private int respondentId;
    private String answerText;
    
    private Answers answers;
    
    public RespondentAnswers(Answers answers, int respondentId, String answerText)
    {
        this.answers = answers;
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
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "answerId", nullable = false)
    public Answers getAnswers()
    {
        return answers;
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
    
    public void setAnswers(Answers answers)
    {
        this.answers = answers;
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
