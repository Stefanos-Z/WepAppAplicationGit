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
 * Creates an object corresponding to an RespondentAnswers tuple in the DB.
 * @authors     DALLOS
 * @version     1
 * @since       07/03/2018
 */

@Entity
@Table(name = "respondent_answers")
public class RespondentAnswers {

    private int respondentAnswerId;
    //private int answerId;
    private int respondentId;
    private String answerText;
    
    private Answers answers;
    /**
     * constructor for the class
     * @param answers
     * @param respondentId
     * @param answerText 
     */
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
    
    /**
    *  getter for respondentAnswerId
    * @return 
    */  
    @Id
    @GeneratedValue //means hibernate will generate the value
    @NotNull
    @Column(name="respondentAnswerId", unique = true)
    public int getRespondentAnswerId()
    {
        return respondentAnswerId;
    }
    
    /**
     * getter for answers
     * @return 
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "answerId", nullable = false)
    public Answers getAnswers()
    {
        return answers;
    }
    
    /**
     * getter for respondentId
     * @return 
     */
    @NotNull
    @Column(name="respondentId")
    public int getRespondentId()
    {
        return respondentId;
    }
    
    /**
     * getter for answerText
     * @return 
     */
    @Column(name="answerText")
    public String getAnswerText()
    {
        return answerText;
    }
    
    /**
     * setter for resondentAnswerId
     * @param respondentAnswerId 
     */
    public void setRespondentAnswerId(int respondentAnswerId)
    {
        this.respondentAnswerId=respondentAnswerId;
    } 
    
    /**
     * setter for answers
     * @param answers 
     */
    public void setAnswers(Answers answers)
    {
        this.answers = answers;
    }
    
    /**
     * setter for respondentId
     * @param respondentId 
     */
    public void setRespondentId(int respondentId)
    {
        this.respondentId=respondentId;
    }
    
    /**
     * setter for answerText
     * @param answerText 
     */
    public void setAnswerText(String answerText)
    {
        this.answerText=answerText;
    }  

}
