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

public class Questions {

    private int questionId;
    private String question;
    private int questionTypeId;
    private int surveyId;
    
    public Questions(int questionId, String question, int questionTypeId, int surveyId)
    {
        this.questionId = questionId;
        this.question = question;
        this.questionTypeId = questionTypeId;
        this.surveyId = surveyId;
    }
    
    public Questions()
    {
        //needs to be empty for hibernate to work?
    }
    
        
    @Id
    @GeneratedValue //means hibernate will generate the value
    @NotNull
    @Column(name="questionId", unique = true)
    public int getQuestionId()
    {
        return questionId;
    }
    
    
    @NotNull
    @Column(name="question")
    public String getQuestion()
    {
        return question;
    }
    
    @NotNull
    @Column(name="questionTypeId")
    public int getQuestionTypeId()
    {
        return questionTypeId;
    }
    
    
    @Column(name="surveyId")
    public int getSurveyId()
    {
        return surveyId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }
    
    

}
